package com.rajan.ecommerce.presentation.feature.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rajan.ecommerce.R
import com.rajan.ecommerce.presentation.feature.login.LoginEvent
import com.rajan.ecommerce.presentation.feature.login.LoginUiState
import com.rajan.ecommerce.presentation.ui_components.RoundedTextField

@Composable
fun LoginContent(uiState: LoginUiState, onEvent: (LoginEvent) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.login),
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

        Spacer(modifier = Modifier.height(32.dp))

        RoundedTextField(
            value = uiState.email,
            onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
            label = "Email",
            isError = uiState.emailError != null,
            errorText = uiState.emailError ?: "Invalid Email",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Password TextField
        RoundedTextField(
            value = uiState.password,
            onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
            label = "Password",
            isError = uiState.passwordError != null,
            errorText = uiState.passwordError,
            keyboardType = KeyboardType.Password,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Remember me section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val checkedState = remember { mutableStateOf(false) }
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurface,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
            Text(
                text = "Remember me",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            ForgotPasswordSection(
                onForgotPasswordClick = { onEvent(LoginEvent.OnForgotPasswordClick) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onEvent(LoginEvent.OnLoginClick) },
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
                text = "Login",
                style = MaterialTheme.typography.labelLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        LoginSocialButton(
            onGoogleClick = { onEvent(LoginEvent.OnGoogleLoginClick) },
            onAppleClick = { onEvent(LoginEvent.OnAppleLoginClick) }
        )

        Spacer(modifier = Modifier.height(32.dp))
        SignUpSection(onSignUpClick = { onEvent(LoginEvent.OnRegisterClick) })
    }
}

@Composable
fun ForgotPasswordSection(onForgotPasswordClick: () -> Unit) {
    Text(
        text = "Forgot Password?",
        style = MaterialTheme.typography.labelLarge.copy(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        ),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clickable { onForgotPasswordClick() },
    )
}