package com.example.dexlivery.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apollographql.apollo3.ApolloClient
import com.example.dexlivery.R
import com.example.dexlivery.data.model.Product
import com.example.dexlivery.domain.dummyProductList
import com.example.dexlivery.presentation.MainScreenViewmodel
import com.example.dexlivery.GetAllOrdersQuery

val apolloClient = ApolloClient.Builder()
    .serverUrl("http://10.0.2.2:8080/grahpql")
    .build()

@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: MainScreenViewmodel = hiltViewModel(),
    onNavigateToCheckout: (selectedProducts: List<Product>) -> Unit
) {
    val uistate = viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        LaunchedEffect(Unit) {
            val response = apolloClient.query(GetAllOrdersQuery()).execute()
            Log.d("GetAllOrdersQuery", "Success ${response.data}")
        }

//        LazyColumn(modifier = modifier) {
//            when (uistate.value) {
//                is MainScreenState.Loading -> {
//                    item {
//                        Text(modifier = Modifier.align(Alignment.Center), text = "Loading Products...")
//                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                    }
//                }
//                is MainScreenState.MainScreenStateUi -> {
//                    val productList = (uistate.value as MainScreenState.MainScreenStateUi).mainScreenUiState?.productList
//                    productList?.let { safeList ->
//                        items(safeList) { product ->
//                            ProductRow(product = product)
//                        }
//                    }
//                }
//                is MainScreenState.Error -> {
//
//                }
//            }
//        }
    }
}

@Composable
private fun ProductRow(product: Product) {
    Card(modifier = Modifier.padding(vertical = 8.dp),
        shape = RoundedCornerShape(20), elevation = 2.dp) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.baseline_image_24),
                contentDescription = "Product image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(50.dp).clip(RoundedCornerShape(15))
            )
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = product.name, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = product.description, fontSize = 15.sp, fontWeight = FontWeight.Normal)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text ="$${product.price}", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
private fun ProductRowPreview() {
    ProductRow(product = dummyProductList[0])
}

data class MainScreenUiState(
    val productList: List<Product>,
    val selectedProducts: List<Product>,
    val checkoutButtonEnabled: Boolean,
)