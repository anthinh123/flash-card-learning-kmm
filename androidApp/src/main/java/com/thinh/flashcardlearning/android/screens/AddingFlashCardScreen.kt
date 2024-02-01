package com.thinh.flashcardlearning.android.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.thinh.flashcardlearning.android.R
import com.thinh.flashcardlearning.flashcard.addingflashcard.AddingFlashCardContact.Event
import com.thinh.flashcardlearning.flashcard.addingflashcard.impl.AddingFlashCardViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingFlashCardScreen(
    onUpClicked: () -> Unit,
    viewModel: AddingFlashCardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.adding_flash_card_title),
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onUpClicked) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.event(Event.OnSave) }) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "save new phrase"
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AddingFlashCardContent(
                isSavingSuccess = uiState.isSavingSuccess,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onUpClicked = onUpClicked,
                sentenceText = uiState.flashCardPo.original,
                onSentenceTextChanged = { newText ->
                    viewModel.event(Event.SetOriginalContent(newText))
                },
                meaningText = uiState.flashCardPo.meaning,
                onMeaningTextChanged = { newText ->
                    viewModel.event(Event.SetMeaningContent(newText))
                },
            )
        }
    }
}

@Composable
private fun AddingFlashCardContent(
    isSavingSuccess: Boolean,
    isLoading: Boolean,
    error: String,
    onUpClicked: () -> Unit,
    sentenceText: String,
    onSentenceTextChanged: (String) -> Unit,
    meaningText: String,
    onMeaningTextChanged: (String) -> Unit
) {
    if (isSavingSuccess) {
        onUpClicked()
    }
    if (isLoading) {
        LoadingContent()
    }

    if (error.isNotEmpty()) {
//        ToastMessage(message = error)
        println("Saving error: $error")
    }

    Box {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ) {
            val phraseSectionModifier = Modifier
                .fillMaxWidth()
                .weight(1F)

            PhraseSectionContent(
                title = stringResource(id = R.string.add_phrase_sentence_section_title),
                modifier = phraseSectionModifier.padding(bottom = 12.dp),
                content = sentenceText,
                onContentChanged = onSentenceTextChanged
            )
            PhraseSectionContent(
                title = stringResource(id = R.string.add_phrase_meaning_section_title),
                modifier = phraseSectionModifier.padding(top = 12.dp),
                content = meaningText,
                onContentChanged = onMeaningTextChanged
            )
        }
    }
}

@Composable
private fun PhraseSectionContent(
    title: String,
    modifier: Modifier,
    content: String,
    onContentChanged: (String) -> Unit
) {
    val sizeOfButton = 48.dp
    val modifierOfButton = Modifier
        .size(sizeOfButton)
        .padding(8.dp)

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Camera",
                modifier = modifierOfButton
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_voice),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Voice",
                modifier = modifierOfButton
            )
        }

        OutlinedTextField(
            placeholder = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            modifier = Modifier.fillMaxSize(),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            value = content,
            onValueChange = onContentChanged,
        )
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

//@Composable
//fun ToastMessage(message: String) {
//    val context = LocalContext.current
//    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//}
//
//@Preview
//@Composable
//private fun AddPhraseScreenPreview() {
//    AddingFlashCardContent(
//        isSavingSuccess = false,
//        onUpClicked = {},
//        sentenceText = "",
//        onSentenceTextChanged = {},
//        meaningText = "",
//        onMeaningTextChanged = {},
//    )
//}