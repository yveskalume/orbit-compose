package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kiwi.orbit.compose.catalog.semantics.TopAppBarScreenSemantics
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBarLarge
import kiwi.orbit.compose.ui.controls.TopAppBarScrollBehavior
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun TopAppBarLargeScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(TopAppBarScreenSemantics.LargeSimpleScreenTag),
        topBar = {
            TopAppBarLarge(
                title = { Text("Simple") },
                onNavigateUp = onNavigateUp,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = Icons.Notification, contentDescription = null)
                    }
                },
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarLargeScrollableScreen(
    onNavigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarScrollBehavior.exitUntilCollapsed()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = { Text("Scrollable") },
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                largeElevated = false,
            )
        },
        backgroundColor = OrbitTheme.colors.surface.subtle,
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarLargeScrollableElevatedScreen(
    onNavigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarScrollBehavior.exitUntilCollapsed()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = { Text("Scrollable Elevated") },
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                largeElevated = true,
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarLargeCustomContentScreen(
    onNavigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarScrollBehavior.exitUntilCollapsed()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text("Jonathan Archer")
                            Text(
                                text = "j.archer@nx01.starfleet",
                                style = OrbitTheme.typography.bodySmall,
                                emphasis = ContentEmphasis.Minor,
                            )
                        }
                        AsyncImage(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(ArcherPhoto)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                        )
                    }
                },
                largeTitle = {
                    Column {
                        Row {
                            Text("Jonathan Archer", Modifier.weight(1f))
                            AsyncImage(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(ArcherPhoto)
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = "j.archer@nx01.starfleet",
                            style = OrbitTheme.typography.bodyNormal,
                            emphasis = ContentEmphasis.Minor,
                        )
                    }
                },
                onNavigateUp = onNavigateUp,
                largeElevated = false,
                scrollBehavior = scrollBehavior,
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun TopAppBarLargePullRefreshScreen(
    onNavigateUp: () -> Unit,
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by rememberSaveable { mutableStateOf(false) }
    var i by rememberSaveable { mutableIntStateOf(0) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        i += 1
        refreshing = false
    }

    val scrollBehavior = TopAppBarScrollBehavior.exitUntilCollapsed()
    val pullRefreshState = rememberPullRefreshState(refreshing, ::refresh)

    Scaffold(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = { Text("Pull Refresh") },
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                largeElevated = true,
            )
        },
        content = {
            Box {
                CustomContentPlaceholder(it, "Custom content $i")
                PullRefreshIndicator(
                    refreshing = refreshing,
                    state = pullRefreshState,
                    contentColor = OrbitTheme.colors.primary.normal,
                    modifier = Modifier
                        .padding(it)
                        .align(Alignment.TopCenter),
                )
            }
        },
    )
}

private const val ArcherPhoto = "https://upload.wikimedia.org/wikipedia/commons/2/23/J_Archer.jpg"
