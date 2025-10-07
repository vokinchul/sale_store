package com.vokinchul.salestore.ui.screens

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.vokinchul.salestore.di.ViewModelFactory
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.ui.screens.components.AddToCartFAB
import com.vokinchul.salestore.ui.screens.components.ProductDetailContent
import com.vokinchul.salestore.ui.viewmodel.ProductDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModelFactory: ViewModelFactory,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    val viewModel: ProductDetailViewModel = remember {
        ViewModelProvider(
            viewModelStoreOwner,
            viewModelFactory
        )[ProductDetailViewModel::class.java]
    }

    val product by viewModel.productState
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали товара") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        bottomBar = {
            if (product != null) {
                ProductDetailBottomBar(
                    onShareClick = { shareProduct(context, product!!) },
                    onDownloadClick = { downloadImage(context, product!!) }
                )
            }
        },
        floatingActionButton = {
            product?.let {
                AddToCartFAB(
                    product = it,
                    onCartAction = { isAdding ->
                        viewModel.updateCartStatus(it.id, isAdding)
                    }
                )
            }
        }
    ) { paddingValues ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Ошибка загрузки: $error",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadProduct(productId) }) {
                            Text("Повторить")
                        }
                    }
                }
            }

            product != null -> {
                ProductDetailContent(
                    product = product!!,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Товар не найден")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadProduct(productId) }) {
                            Text("Повторить")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailBottomBar(
    onShareClick: () -> Unit,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier.height(108.dp),
        actions = {
            // Кнопка "Поделиться товаром"
            IconButton(
                onClick = onShareClick,
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Поделиться товаром",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Поделиться",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            // Кнопка "Скачать картинку"
            IconButton(
                onClick = onDownloadClick,
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Скачать картинку",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Скачать",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    )
}

// Функция для шаринга товара
private fun shareProduct(context: Context, product: Product) {
    try {
        val productLink = "https://fakestoreapi.com/products/${product.id}"

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, productLink)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(shareIntent, "Поделиться ссылкой на товар"))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// Функция для скачивания/просмотра картинки
@SuppressLint("UseKtx")
private fun downloadImage(context: Context, product: Product) {
    try {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(product.image))
            .setTitle("${product.title}.jpg")
            .setDescription("Скачивание изображения товара")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "product_${product.id}_${System.currentTimeMillis()}.jpg"
            )

        downloadManager.enqueue(request)

        // Можно показать Toast
        // Toast.makeText(context, "Загрузка начата", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
        // Альтернатива - открыть в браузере
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.image))
        context.startActivity(intent)
    }
}