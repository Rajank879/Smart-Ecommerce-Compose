package com.rajan.ecommerce.presentation.feature.address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rajan.ecommerce.MainActivity
import com.rajan.ecommerce.presentation.feature.home.ui.HomeScreen
import com.rajan.ecommerce.presentation.navigation.Routes
import com.rajan.ecommerce.presentation.ui_components.AddressRadioItem
import com.rajan.ecommerce.presentation.ui_components.MyTopAppBar
import com.rajan.ecommerce.presentation.ui_components.StepProgressBar
import com.rajan.ecommerce.presentation.ui_components.util.StepItems
import kotlinx.coroutines.delay

@Composable
fun AddAddress(navController: NavController,
               viewModel: UserAddressViewModel = hiltViewModel(),
               pinCode: String,
               city: String,
               states: String,
               address: String) {
    val steps = listOf(
        StepItems("Bag", true, false),
        StepItems("Address", false, true),
        StepItems("Payment", false, false)
    )

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current


    LaunchedEffect(pinCode, city, state, address) {
        viewModel.onEvent(AddressUIEvent.OnPinCodeChange(pinCode))
        viewModel.onEvent(AddressUIEvent.OnCityChange(city))
        viewModel.onEvent(AddressUIEvent.OnStateChange(states))
        viewModel.onEvent(AddressUIEvent.OnAddressChange(address))

    }
    LaunchedEffect(Unit) {
        viewModel.navigationState.collect {
            when (it) {
                is AddressNavigation.Back -> {
                    navController.popBackStack()

                }

                is AddressNavigation.NavigateToPayment -> {
                    (context as MainActivity).startPayment()
                    delay(500)
                    navController.navigate(Routes.HomeScreen(showPaymentSuccess = false)){
                        popUpTo<Routes.HomeScreen>{
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        }

    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Add New Address",
                onBackClick = {
                   viewModel.onEvent(AddressUIEvent.OnCancelClick)
                }
            )
        },
        bottomBar = {
            AddressBottomBar(viewModel::onEvent)
        }

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .imePadding(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(color = MaterialTheme.colorScheme.surface)
                ) {
                    StepProgressBar(steps)
                }
            }

            item { ContactDetails(state, viewModel::onEvent) }

            item { AddressDetails(state, viewModel::onEvent) }

            item { AddressType(state, viewModel::onEvent) }


        }
    }

}

@Composable
fun AddressType(state: AddressUiState, onEvent: (AddressUIEvent) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Address Type",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AddressRadioItem(title = "Home", selected = state.addressType == "Home", onClick = {onEvent(AddressUIEvent.IsAddressTypeChange("Home"))})
                Spacer(modifier = Modifier.width(16.dp))
                AddressRadioItem(title = "Work", selected = state.addressType == "Work", onClick = {onEvent(AddressUIEvent.IsAddressTypeChange("Work"))})
            }
            Spacer(modifier = Modifier.height(12.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.isDefault,
                    onCheckedChange = {
                        onEvent(AddressUIEvent.OnDefaultChange(it))

                        // Handle checkbox state change
                    },
                    modifier = Modifier.size(20.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.onSurface,
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Make this as Default Address",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
fun AddressDetails(state: AddressUiState, onEvent: (AddressUIEvent) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Address",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutLineTextFiledAddress(
                label = "PinCode*",
                keyboardType = KeyboardType.Number,
                state = state.pinCode,
                onValueChange = {onEvent(AddressUIEvent.OnPinCodeChange(it))},
                isEnable = false
            )

            Spacer(modifier = Modifier.height(12.dp))
            OutLineTextFiledAddress(label = "Address*", state = state.address,
                onValueChange = {onEvent(AddressUIEvent.OnAddressChange(it))} )
            Spacer(modifier = Modifier.height(12.dp))
            OutLineTextFiledAddress(
                label = "Locality / Town*",
                state = state.locality,
                onValueChange = {onEvent(AddressUIEvent.OnLocalityChange(it))}
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutLineTextFiledAddress(
                    label = "City / District*",
                    modifier = Modifier.weight(1f), state = state.city,
                    onValueChange = {onEvent(AddressUIEvent.OnCityChange(it))},
                    isEnable = false
                )
                OutLineTextFiledAddress(
                    label = "State*",
                    modifier = Modifier.weight(1f), state = state.state,
                    onValueChange = {onEvent(AddressUIEvent.OnStateChange(it))},
                    imeAction = ImeAction.Done,
                    isEnable = false
                )
            }

        }
    }
}

@Composable
fun ContactDetails(state: AddressUiState, onEvent: (AddressUIEvent) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Contact Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))
            OutLineTextFiledAddress(label = "Name*", state = state.name, onValueChange = {onEvent(AddressUIEvent.OnNameChange(it))} )
            Spacer(modifier = Modifier.height(12.dp))
            OutLineTextFiledAddress(label = "Mobile No*",keyboardType = KeyboardType.Number, state = state.mobile,onValueChange = {onEvent(AddressUIEvent.OnMobileChange(it))} )
        }
    }
}

@Composable
fun AddressBottomBar(onEvent: (AddressUIEvent) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            onClick = {
                onEvent(AddressUIEvent.OnCancelClick)
            },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(48.dp),
            shape = MaterialTheme.shapes.small

        ) {
            Text(
                text = "Cancel",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }

        Button(
            onClick = {
                onEvent(AddressUIEvent.OnSaveAddressClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.small

        ) {
            Text(
                text = "Save",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }


    }
}

@Composable
fun OutLineTextFiledAddress(
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    state: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    isEnable: Boolean = true
) {
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
    )

    OutlinedTextField(
        value = state,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light,
            )
        },
        shape = MaterialTheme.shapes.small,
        colors = textFieldColors,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction= imeAction),
        enabled = isEnable

    )
}
