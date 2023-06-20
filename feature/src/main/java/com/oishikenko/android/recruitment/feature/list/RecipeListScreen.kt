package com.oishikenko.android.recruitment.feature.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel()
) {
//    val cookingRecords by viewModel.cookingRecords.collectAsStateWithLifecycle()

    val lazyColumnListState = rememberLazyListState()
    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    val cookingRecordList = viewModel.cookingRecordList

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.listState == ListState.IDLE)
            viewModel.getCookingRecord()
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        state = lazyColumnListState
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 8.dp, 0.dp, 16.dp),
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
        items(
            items = cookingRecordList
        ) {
//            items(cookingRecords) {
            if (it != null) {
                RecipeListItem(it)
            }
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
