package com.taild.jetecommerce.ui.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.taild.domain.model.Product

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val viewModel : HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState) {
                is HomeScreenUIState.Loading -> {
                    CircularProgressIndicator()
                }

                is HomeScreenUIState.Success -> {
                    val data = (uiState as HomeScreenUIState.Success)
                    HomeContent(feature = data.feature, popular = data.popular
                    )
                }

                is HomeScreenUIState.Error -> {
                    Text(text = (uiState as HomeScreenUIState.Error).message)
                }
            }
        }
    }
}

@Composable
fun HomeContent(feature: List<Product>, popular: List<Product>) {
    LazyColumn {
        item {
            if (feature.isNotEmpty()) {
                HomeProductRow(feature, "Feature")
                Spacer(modifier = Modifier.size(16.dp))
            }
            if (popular.isNotEmpty()) {
                HomeProductRow(popular, "Popular")
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
                style = MaterialTheme.typography.titleMedium)
            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = "View all",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(products) { product ->
                ProductItem(product = product)
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
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}