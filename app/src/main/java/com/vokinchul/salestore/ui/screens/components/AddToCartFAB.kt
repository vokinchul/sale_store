package com.vokinchul.salestore.ui.screens.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vokinchul.salestore.domain.model.Product

@Composable
fun AddToCartFAB(
    product: Product,
    onCartAction: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        onClick = {
            onCartAction(!product.isInCart)
        },
        modifier = modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = if (product.isInCart) Icons.Default.Delete else Icons.Default.Add,
            contentDescription = if (product.isInCart) "Убрать из корзины" else "Добавить в корзину"
        )
        Text(
            text = if (product.isInCart) "Убрать из корзины" else "Добавить в корзину"
        )
    }
}