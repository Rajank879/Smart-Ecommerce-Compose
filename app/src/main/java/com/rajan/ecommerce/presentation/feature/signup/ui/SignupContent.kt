package com.rajan.ecommerce.presentation.feature.signup.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rajan.ecommerce.R
import com.rajan.ecommerce.presentation.feature.signup.SignUpEvent
import com.rajan.ecommerce.presentation.feature.signup.SignUpUiState
import com.rajan.ecommerce.presentation.ui_components.RoundedTextField

@Composable
fun SignupContent(uiState: SignUpUiState, onEvent: (SignUpEvent) -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.create_account),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.header_text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))
        RoundedTextField(
            value = uiState.firstName,
            onValueChange = { onEvent(SignUpEvent.OnFirstnameChanged(it)) },
            label = "First Name",
            isError = uiState.firstNameError != null,
            errorText = uiState.firstNameError ?: "Invalid First Name",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(12.dp))
        RoundedTextField(
            value = uiState.lastName,
            onValueChange = { onEvent(SignUpEvent.OnLastnameChanged(it)) },
            label = "Last Name",
            isError = uiState.lastNameError != null,
            errorText = uiState.lastNameError ?: "Invalid First Name",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(12.dp))
        RoundedTextField(
            value = uiState.email,
            onValueChange = { onEvent(SignUpEvent.OnEmailChanged(it)) },
            label = "Email",
            isError = uiState.emailError != null,
            errorText = uiState.emailError ?: "Invalid Email",
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(12.dp))
        RoundedTextField(
            value = uiState.password,
            onValueChange = { onEvent(SignUpEvent.OnPasswordChanged(it)) },
            label = "Password",
            isError = uiState.passwordError != null,
            errorText = uiState.passwordError ?: "Invalid Password",
            keyboardType = KeyboardType.Password,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        RoundedTextField(
            value = uiState.confirmPassword,
            onValueChange = { onEvent(SignUpEvent.OnConfirmPasswordChanged(it)) },
            label = "Confirm Password",
            isError = uiState.confirmPasswordError != null,
            errorText = uiState.confirmPasswordError ?: "Invalid Password",
            keyboardType = KeyboardType.Password,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onEvent(SignUpEvent.OnSignUpClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = !uiState.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = "Signup",
                style = MaterialTheme.typography.labelLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        LoginSection(onLoginClick ={ onEvent(SignUpEvent.OnLoginClicked)})
    }
}