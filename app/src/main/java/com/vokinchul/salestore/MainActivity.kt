package com.vokinchul.salestore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vokinchul.MyApp
import com.vokinchul.salestore.data.api.FakeStoreApi
import com.vokinchul.salestore.ui.navigation.Screens
import com.vokinchul.salestore.ui.theme.SaleStoreTheme
import jakarta.inject.Inject
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var apiService: FakeStoreApi

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
                    NavHost(
                        navController = navController,
                        startDestination = Screens.MainScreen.name
                    ){

                    }
                    Text(text = "Проверка API в Logcat...")
                }
            }
        }
    }
}