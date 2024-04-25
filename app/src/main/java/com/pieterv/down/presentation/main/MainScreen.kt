package com.pieterv.down.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pieterv.down.domain.usecase.ComplexData
import com.pieterv.down.presentation.ShimmerList
import com.pieterv.down.ui.theme.DownTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    MainScreenContent(
        state = uiState,
        isFloatingActionButtonVisible = { rowCount ->
            rowCount >= 10
        }
    )

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.onEvent(MainScreenEvent.LoadData)
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    DownTheme {
        MainScreenContent(
            state = MainScreenState(isLoading = true),
            isFloatingActionButtonVisible = { rowCount ->
                rowCount >= 10
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    state: MainScreenState,
    isFloatingActionButtonVisible: (Int) -> Boolean
) {
    val staggeredGridState = remember { LazyStaggeredGridState() }
    val showFloatingActionButton by remember {
        derivedStateOf {
            isFloatingActionButtonVisible(staggeredGridState.firstVisibleItemIndex)
        }
    }

    DownTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                if (showFloatingActionButton) {
                    ScrollToTopButton(staggeredGridState)
                }
            }
        ) { contentPadding ->
            ShimmerList(
                modifier = Modifier.padding(contentPadding),
                isLoading = state.isLoading,
                contentAfterLoading = {
                    StaggeredDisplayContent(
                        modifier = Modifier.padding(contentPadding),
                        state = state,
                        staggeredGridState = staggeredGridState,
                    )
                }
            )
        }
    }
}

@Composable
fun StaggeredDisplayContent(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    staggeredGridState: LazyStaggeredGridState,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
        state = staggeredGridState
    ) {
        items(state.data) { item ->
            StaggeredBox(item)
        }
    }
}

@Composable
fun ScrollToTopButton(
    staggeredGridState: LazyStaggeredGridState
) {
    val scope = rememberCoroutineScope()

    FloatingActionButton(
        onClick = {
            scope.launch {
                staggeredGridState.scrollToItem(0)
            }
        },
        content = {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Scroll to top"
            )
        }
    )
}

@Composable
fun StaggeredBox(properties: ComplexData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = properties.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = properties.subtext,
                style = TextStyle(
                    fontSize = 16.sp
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}