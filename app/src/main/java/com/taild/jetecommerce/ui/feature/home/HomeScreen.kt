package com.taild.jetecommerce.ui.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.taild.domain.model.Product

@Composable
fun HomeScreen(
    navController: NavController? = null,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var loading by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }
    var features by rememberSaveable { mutableStateOf<List<Product>>(emptyList()) }
    var populars by rememberSaveable { mutableStateOf<List<Product>>(emptyList()) }
    var categories by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState) {
                is HomeScreenUIState.Loading -> {
                    loading = true
                    error = null
                }

                is HomeScreenUIState.Success -> {
                    val data = (uiState as HomeScreenUIState.Success)
                    features = data.feature
                    populars = data.popular
                    categories = data.categories
                    loading = false
                    error = null
                }

                is HomeScreenUIState.Error -> {
                    val errorMessage = (uiState as HomeScreenUIState.Error).message
                    loading = false
                    error = errorMessage
                }
            }
            HomeContent(
                features = features,
                populars = populars,
                categories = categories,
                isLoading = loading,
                errorMessage = error)
        }
    }
}

@Composable
fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            CircularIcon(
                modifier = Modifier.size(38.dp),
                imageVector = Icons.Filled.Person)

            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Hello",
                    style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "Duc Tai",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold)
            }
        }
        CircularIcon(
            imageVector = Icons.Filled.Notifications,
            modifier = Modifier
                .size(38.dp)
                .align(Alignment.CenterEnd))
    }
}

@Composable
fun CircularIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = Color.LightGray.copy(alpha = 0.4f),
                shape = CircleShape
            )
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = imageVector,
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = onSearch,
                    expanded = false,
                    onExpandedChange = { },
                    placeholder = { Text("Hinted search text") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.Mic, contentDescription = null) },
                )
            },
            expanded = false,
            onExpandedChange = { },
        ) {
            Text(text = "Sample result")
        }
    }
}

@Composable
fun HomeContent(
    features: List<Product>,
    populars: List<Product>,
    categories: List<String>,
    isLoading: Boolean = false,
    errorMessage: String? = null
    ) {
    var text by rememberSaveable { mutableStateOf("") }
    LazyColumn {
        item {
            ProfileHeader()
            Spacer(modifier = Modifier.size(16.dp))
            HomeSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                query = text,
                onQueryChange = {text = it},
                onSearch = {}
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        item {
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            errorMessage?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (categories.isNotEmpty()) {
                Spacer(modifier = Modifier.size(16.dp))
                LazyRow {
                    items(categories, key = {it}) { category ->
                        var isVisible by remember { mutableStateOf(false) }
                        LaunchedEffect(true) {
                            isVisible = true
                        }
                        AnimatedVisibility(
                            visible = isVisible,
                            enter = fadeIn() + expandVertically()
                        ) {
                            Text(
                                text = category.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(8.dp),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
            }
            if (features.isNotEmpty()) {
                HomeProductRow(features, "Feature")
                Spacer(modifier = Modifier.size(16.dp))
            }
            if (populars.isNotEmpty()) {
                HomeProductRow(populars, "Popular")
            }
        }
    }
}

@Composable
fun HomeProductRow(products: List<Product>, title: String) {
    Column {
        Box(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold)
            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = "View all",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(items = products, key = { it.id }) { product ->
                var isVisible by remember { mutableStateOf(false) }
                LaunchedEffect(true) {
                    isVisible = true
                }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn() + expandVertically()
                ) {
                    ProductItem(product = product)
                }
            }
        }
    }

}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(
                width = 126.dp,
                height = 144.dp
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(alpha = 3.0f))
        ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                model = product.image,
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    ProfileHeader()
}

@Preview
@Composable
fun SearchBarPreview() {
    HomeSearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = "Sample",
        onQueryChange = {},
        onSearch = {}
    )
}

