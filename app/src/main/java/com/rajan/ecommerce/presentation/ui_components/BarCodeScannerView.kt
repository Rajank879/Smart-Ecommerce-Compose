package com.rajan.ecommerce.presentation.ui_components

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@ExperimentalGetImage
@Composable
fun BarCodeScannerView(onBarcodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    //Logic to prevent multiple scans(Cooldown of 2 second)
    var lastScannedTime by remember { mutableLongStateOf(0L) }
    val scanCooldownMillis = 2000L
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val executor = ContextCompat.getMainExecutor(ctx)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    // Preview
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    // Barcode Selector
                    val scanner = BarcodeScanning.getClient()

                    // Image Analysis
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    imageAnalysis.setAnalyzer(executor) { imageProxy ->
                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val image = InputImage.fromMediaImage(
                                mediaImage,
                                imageProxy.imageInfo.rotationDegrees
                            )
                            scanner.process(image)
                                .addOnSuccessListener { barcodes ->
                                    val currentTime = System.currentTimeMillis()
                                    for (barcode in barcodes) {
                                        val rawValue = barcode.rawValue
                                       //only trigger the scan if it's been at least 2 seconds since the last scan
                                        if(rawValue!=null && (currentTime - lastScannedTime)>scanCooldownMillis){
                                            lastScannedTime = currentTime
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            onBarcodeScanned(rawValue)
                                        }
                                    }
                                }
                                .addOnCompleteListener { imageProxy.close() }
                        }
                    }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        Log.e("CameraX", "Binding failed", e)
                    }
                }, executor)
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        // Overlay with scanning frame
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val strokeWidth = 4.dp.toPx()
            val cornerLength = 40.dp.toPx()
            val frameSize = 250.dp.toPx()
            
            val left = (width - frameSize) / 2
            val top = (height - frameSize) / 2
            val right = left + frameSize
            val bottom = top + frameSize

            // Darken outside area
            drawRect(
                color = Color.Black.copy(alpha = 0.5f)
            )
            
            // Clear the frame area
            drawRect(
                color = Color.Transparent,
                topLeft = Offset(left, top),
                size = Size(frameSize, frameSize),
                blendMode = BlendMode.Clear
            )

            // Draw corners
            val color = Color.White
            // Top Left
            drawLine(color, Offset(left, top), Offset(left + cornerLength, top), strokeWidth)
            drawLine(color, Offset(left, top), Offset(left, top + cornerLength), strokeWidth)
            
            // Top Right
            drawLine(color, Offset(right, top), Offset(right - cornerLength, top), strokeWidth)
            drawLine(color, Offset(right, top), Offset(right, top + cornerLength), strokeWidth)

            // Bottom Left
            drawLine(color, Offset(left, bottom), Offset(left + cornerLength, bottom), strokeWidth)
            drawLine(color, Offset(left, bottom), Offset(left, bottom - cornerLength), strokeWidth)

            // Bottom Right
            drawLine(color, Offset(right, bottom), Offset(right - cornerLength, bottom), strokeWidth)
            drawLine(color, Offset(right, bottom), Offset(right, bottom - cornerLength), strokeWidth)

            // Scanning line
            drawLine(
                color = Color(0xFF9192FD),
                start = Offset(left, top + frameSize / 2),
                end = Offset(right, top + frameSize / 2),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}