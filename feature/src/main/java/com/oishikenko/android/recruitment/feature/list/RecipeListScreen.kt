package com.oishikenko.android.recruitment.feature.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oishikenko.android.recruitment.feature.R

@OptIn(ExperimentalLayoutApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel()
) {
    val cookingRecords by viewModel.cookingRecords.collectAsStateWithLifecycle()
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                modifier = Modifier,
//                backgroundColor = Color.Transparent,
//                elevation = 0.dp
//            ) {
//                Text(text = stringResource(id = R.string.cooking_records_title))
//            }
//        }
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(innerPadding)
//                .consumedWindowInsets(innerPadding)
//        ) {
//            item {
//                Row(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.cooking_records_title),
//                        modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp),
//                        color = colorResource(R.color.cooking_records_title_color),
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.W700,
//                        lineHeight = 29.sp,
//                    )
//                    Image(
//                        modifier = Modifier
//                            .height(64.dp)
//                            .width(66.72.dp),
//                        painter = painterResource(id = R.drawable.cooking_records_image),
//                        contentDescription = ""
//                    )
//                }
//            }
//            items(cookingRecords) {
//                RecipeListItem(it)
//            }
//        }
//    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.cooking_records_title),
                    modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp),
                    color = colorResource(R.color.cooking_records_title_color),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 29.sp,
                )
                Image(
                    modifier = Modifier
                        .height(64.dp)
                        .width(66.72.dp),
                    painter = painterResource(id = R.drawable.cooking_records_image),
                    contentDescription = ""
                )
            }
        }
        items(cookingRecords) {
            RecipeListItem(it)
        }
    }
}

@Preview
@Composable
fun PreviewRecipeListScreen(){
    MaterialTheme {
        RecipeListScreen()
    }
}
