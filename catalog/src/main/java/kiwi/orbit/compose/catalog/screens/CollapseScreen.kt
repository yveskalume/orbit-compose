package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.controls.Collapse
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun CollapseScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Collapse") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            CollapseScreenInner()
        }
    }
}

@Preview
@Composable
private fun CollapseScreenInner() {
    var isFirstCollapseExtended by remember { mutableStateOf(true) }
    var isSecondCollapseExtended by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(16.dp)) {
        Collapse(
            isExpended = isFirstCollapseExtended,
            onExpandChange = { isFirstCollapseExtended = it },
            header = {
                Text(text = "Title 1")
            },
            body = {
                Text(text = "Something with many many words, descriptive and longer for more lines.")
            },
        )

        Collapse(
            isExpended = isSecondCollapseExtended,
            onExpandChange = { isSecondCollapseExtended = it },
            showSeparator = false,
            header = {
                Text(text = "Title 2")
            },
            body = {
                Text(text = "Something with many many words, descriptive and longer for more lines.")
            },
        )
    }
}
