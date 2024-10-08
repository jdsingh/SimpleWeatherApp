package me.jagdeep.playground.screens.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.jagdeep.playground.R
import me.jagdeep.playground.presenation.AppUiState
import me.jagdeep.playground.presenation.AppViewModel

@Composable
fun SearchView(
    uiState: AppUiState,
    viewModel: AppViewModel,
) {
    var query by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = { query = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search_icon)
                )
            },
            label = { Text(text = stringResource(id = R.string.search_weather_for_city)) },
            isError = uiState is AppUiState.InvalidCityError,
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = {
                viewModel.onSearchWithCity(query = query)
            }
        ) {
            Text(text = stringResource(id = R.string.btn_search))
        }
    }
}
