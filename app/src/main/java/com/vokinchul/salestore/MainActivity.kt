package com.vokinchul.salestore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vokinchul.MyApp
import com.vokinchul.salestore.data.api.FakeStoreApi
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.ui.navigation.Screens
import com.vokinchul.salestore.ui.screens.MainScreen
import com.vokinchul.salestore.ui.theme.SaleStoreTheme
import jakarta.inject.Inject
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var apiService: FakeStoreApi

    private var productsState by mutableStateOf<List<Product>>(emptyList())
    private var isLoading by mutableStateOf(true)
    private var error by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        (application as MyApp).appComponent.inject(this)

        lifecycleScope.launch {
            val value = apiService.getProducts()
            Log.d("API", "Продукты: $value")
        }

        setContent {
            SaleStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    LaunchedEffect(Unit) {
                        loadProducts()
                    }

                    NavHost(
                        navController = navController,
                        startDestination = Screens.MainScreen.name
                    ) {
                        composable(Screens.MainScreen.name) {
                            MainScreen(
                                navController = navController,
                                products = productsState,
                                isLoading = isLoading,
                                error = error,
                                onRetry = { loadProducts() },
                                onProductClick = { product ->
                                    navController.navigate("product_detail/${product.id}")
                                }
                            )
                        }

                        /*
                        composable(Screens.AuthorizationScreen.name) {
                            AuthorizationScreen(
                                navController = navController,
                                onLoginSuccess = {
                                    // После успешной авторизации возвращаемся назад
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(Screens.ShoppingCartScreen.name) {
                            ShoppingCartScreen(
                                navController = navController,
                                products = productsState.filter { *//* логика корзины *//* }
                            )
                        }

                        // Детали товара с аргументом
                        composable(
                            route = "product_detail/{productId}",
                            arguments = listOf(
                                navArgument("productId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getInt("productId")
                            val product = productsState.find { it.id == productId }

                            if (product != null) {
                                ProductDetailScreen(
                                    navController = navController,
                                    product = product
                                )
                            } else {
                                // Обработка случая, когда товар не найден
                            }
                        }
                        */
                    }
                }
            }
        }
    }

    private fun loadProducts() {
        lifecycleScope.launch {
            isLoading = true
            error = null
            try {
                val products = apiService.getProducts(limit = 30)
                productsState = products
                Log.d("API", "Загружено продуктов: ${products.size}")
            } catch (e: Exception) {
                error = "Ошибка загрузки: ${e.message}"
                Log.e("API", "Ошибка загрузки", e)
            } finally {
                isLoading = false
            }
        }
    }
}