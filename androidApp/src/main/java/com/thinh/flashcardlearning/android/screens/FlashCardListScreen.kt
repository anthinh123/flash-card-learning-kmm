package com.thinh.flashcardlearning.android.screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thinh.flashcardlearning.android.R
import com.thinh.flashcardlearning.flashcard.domain.FlashCardPo
import com.thinh.flashcardlearning.flashcard.flashcardlist.impl.FlashCardListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.absoluteValue
import androidx.compose.ui.util.lerp
import com.thinh.flashcardlearning.flashcard.flashcardlist.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardListScreen(
    viewModel: FlashCardListViewModel = koinViewModel(),
    navigateToAddingFlashCardScreen: () -> Unit
) {

    val uiState = viewModel.flashCardListState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.flash_card_list_title), maxLines = 1
                    )
                },
                actions = {
                    IconButton(onClick = navigateToAddingFlashCardScreen) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "create new phrase"
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (uiState.value.error.isNotEmpty()){
                ErrorContent()
            }

            if (uiState.value.flashCards.isNotEmpty()) {
                FlashCardListContent(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    flashCardPos = uiState.value.flashCards,
                    onClickDone = { id -> viewModel.event(Event.OnDoneIconSelected(id)) },
                    onClickSpeak = { id -> viewModel.event(Event.OnSpeakIconSelected(id)) },
                    onClickTranslate = { id -> viewModel.event(Event.OnTranslateIconSelected(id)) }
                )
            }

            if (uiState.value.isLoading) {
                LoadingContent()
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FlashCardListContent(
    modifier: Modifier,
    flashCardPos: List<FlashCardPo>,
    onClickDone: (Long) -> Unit,
    onClickSpeak: (Long) -> Unit,
    onClickTranslate: (Long) -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 32.dp, top = 20.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        val pagerState = rememberPagerState(pageCount = {
            flashCardPos.size
        })
        Box(
            modifier = Modifier
                .padding(bottom = 48.dp, top = 24.dp)
                .fillMaxSize()
                .weight(1f)
                .fillMaxWidth(),
        ) {
            FlashCardsPager(pagerState, flashCardPos)
        }
        UsefulButtons(onClickDone = { onClickDone(flashCardPos[pagerState.currentPage].id) },
            onClickSpeak = { onClickSpeak(flashCardPos[pagerState.currentPage].id) },
            onClickTranslate = { onClickTranslate(flashCardPos[pagerState.currentPage].id) })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FlashCardsPager(
    pagerState: PagerState, flashCardList: List<FlashCardPo>
) {
    val scope = rememberCoroutineScope()

    val scrollToNextPage = {
        val currentPage = pagerState.currentPage
        if (currentPage == pagerState.pageCount) {
//            ToastMessage(message = "This is a last flashCard")
        } else {
            scope.launch {
                pagerState.animateScrollToPage(page = currentPage + 1, pageOffsetFraction = 0f)
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        pageSpacing = 24.dp,
        modifier = Modifier.fillMaxSize(),
    ) { pageIndex ->
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction).absoluteValue

                    alpha = lerp(
                        start = 0.2f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .clickable { scrollToNextPage() },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val flashCard = flashCardList[pageIndex]
                Text(
                    text = if (!flashCard.isDisplayMeaning) flashCard.original else flashCard.meaning,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun ToastMessage(message: String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun UsefulButtons(
    onClickDone: () -> Unit, onClickSpeak: () -> Unit, onClickTranslate: () -> Unit
) {
    val sizeOfButton = 48.dp
    val modifierOfButton = Modifier.size(sizeOfButton)
    FlowRow(
        horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = Icons.Rounded.Done,
            contentDescription = "Done",
            modifier = modifierOfButton.clickable { onClickDone() })

        Icon(painter = painterResource(id = R.drawable.ic_volume_up_24),
            contentDescription = "Done",
            modifier = modifierOfButton.clickable { onClickSpeak() })

        Icon(painter = painterResource(id = R.drawable.ic_translate_24),
            contentDescription = "Done",
            modifier = modifierOfButton.clickable { onClickTranslate() })
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
    }
}

@Composable
private fun ErrorContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.errorContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.does_not_has_flash_card),
            color = MaterialTheme.colorScheme.onErrorContainer,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Preview
@Composable
fun MainContentScreenPreview() {
//    MainScreen(Modifier.padding(12.dp))
}

@Preview
@Composable
fun ErrorScreenPreview() {
//    ErrorContent()
}