package com.rajan.ecommerce.presentation.feature.home.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    onSearchQueryChange: (String) -> Unit,
    categories: List<String>
) {
    val colorScheme = MaterialTheme.colorScheme
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) { ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            //Material3 SearchBar is still in experimental stage, so using TopSearchBar for now, will replace it with SearchBar when it becomes stable

            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = {
                    Text(text = "Search for coffee...")
                },
                leadingIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            null
                        )
                    }
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                query = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear Search"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { traversalIndex = 0f },
                colors = SearchBarDefaults.colors(
                    containerColor = colorScheme.surfaceVariant
                )
            ) {


                //Suggeestions for search results can be added here, for now just showing categories as suggestions
                categories.filter { it.contains(query, true) }
                    .forEach { category ->
                        ListItem(
                            headlineContent = { Text(text = category) },
                            leadingContent = { Icon(Icons.Default.Search, null) },
                            modifier = Modifier.clickable {
                                query = category
                                active = false
                                onSearchQueryChange(query)
                                navController.navigateUp()
                            },

                            )
                    }

                // Content below search bar

                if (query.isNotEmpty()) {
                    RecentSearchesSection()
                }
            }
        }
    }

}