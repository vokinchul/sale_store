package com.vokinchul.salestore.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.model.Rating
import com.vokinchul.salestore.ui.navigation.Screens

@Preview(showBackground = true)
@Composable
fun MainScreenContentPreview() {
    MainScreen(
        navController = null,
        products = listOf(
            Product(
                id = 1,
                title = "Sample Product 1",
                price = 29.99,
                description = "This is a sample product description.",
                category = "electronics",
                image = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg",
                rating = Rating(rate = 4.5, count = 120)
            ),
            Product(
                id = 2,
                title = "Sample Product 2",
                price = 59.99,
                description = "This is another sample product description.",
                category = "jewelery",
                image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
                rating = Rating(rate = 4.0, count = 80)
            )
        ),
        modifier = Modifier,
        isLoading = false,
        error = null,
        onRetry = {},
        onProductClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController?,
    products: List<Product>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Загрузка товаров...")
            }
        }

        error != null -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                onRetry?.let {
                    Button(onClick = it) {
                        Text("Повторить")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { navController?.navigate(Screens.AuthorizationScreen.name) }
                ) {
                    Text("Авторизация")
                }
            }
        }

        products.isEmpty() -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Товары не найдены")
                onRetry?.let {
                    Button(onClick = it) {
                        Text("Повторить")
                    }
                }
            }
        }

        else -> {
            Column(modifier = modifier.fillMaxSize()) {
                // TopAppBar с кнопкой корзины
                TopAppBar(
                    title = { Text("Магазин") },
                    actions = {
                        IconButton(
                            onClick = { navController?.navigate(
                                Screens.ShoppingCartScreen.name)
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart,
                                contentDescription = "Корзина")
                        }
                        IconButton(
                            onClick = { navController?.navigate(
                                Screens.AuthorizationScreen.name)
                            }
                        ) {
                            Icon(Icons.Default.AccountCircle,
                                contentDescription = "Авторизация")
                        }
                    }
                )

                // Сетка товаров
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(products) { product ->
                        ProductCard(
                            product = product,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.8f),
                            onProductClick = onProductClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onProductClick: ((Product) -> Unit)? = {}
) {
    Card(
        modifier = modifier
            .clickable { onProductClick?.let { it(product) } },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Изображение товара
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )

            // Информация о товаре
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Название товара (максимум 2 строки)
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Цена
                Text(
                    text = "$${"%.2f".format(product.price)}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Рейтинг
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = "%.1f".format(product.rating.rate),
                        style = MaterialTheme.typography.labelSmall
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "(${product.rating.count})",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}