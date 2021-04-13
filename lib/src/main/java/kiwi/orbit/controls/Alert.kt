package kiwi.orbit.controls

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.components.ThemedSurface
import kiwi.orbit.foundation.LocalColors
import kiwi.orbit.foundation.LocalElevationEnabled
import kiwi.orbit.foundation.withCritical
import kiwi.orbit.foundation.withInteractive
import kiwi.orbit.foundation.withSuccess
import kiwi.orbit.foundation.withWarning
import kiwi.orbit.icons.Icons
import kotlin.math.roundToInt

@Composable
public fun AlertInfo(
    modifier: Modifier = Modifier,
    icon: Painter? = Icons.InformationCircle,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withInteractive(),
        LocalElevationEnabled provides false,
    ) {
        Alert(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
public fun AlertSuccess(
    modifier: Modifier = Modifier,
    icon: Painter? = Icons.CheckCircle,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withSuccess(),
        LocalElevationEnabled provides false,
    ) {
        Alert(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
public fun AlertWarning(
    modifier: Modifier = Modifier,
    icon: Painter? = Icons.Visa,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withWarning(),
        LocalElevationEnabled provides false,
    ) {
        Alert(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
public fun AlertCritical(
    modifier: Modifier = Modifier,
    icon: Painter? = Icons.AlertCircle,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withCritical(),
        LocalElevationEnabled provides false,
    ) {
        Alert(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
public fun AlertButtons(
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        Modifier.padding(top = 12.dp)
    ) { measurables, incomingConstraints ->
        val buttonPadding = 12.dp.toPx().roundToInt()
        val buttonsCount = measurables.size
        val buttonSize = ((incomingConstraints.maxWidth - (buttonPadding * (buttonsCount - 1))) / buttonsCount)
        val buttonConstraint = incomingConstraints.copy(minWidth = buttonSize, maxWidth = buttonSize)

        val placeables = measurables.map {
            it.measure(buttonConstraint)
        }

        val maxHeight = placeables.maxOfOrNull { it.height } ?: 0
        layout(incomingConstraints.maxWidth, maxHeight) {
            var x = 0
            for (placeable in placeables) {
                placeable.place(x, y = 0)
                x += buttonSize + buttonPadding
            }
        }
    }
}

@Composable
private fun Alert(
    icon: Painter?,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    ThemedSurface(
        subtle = true,
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
    ) {
        ProvideTextStyle(OrbitTheme.typography.bodyNormal) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(16.dp),
                    tint = OrbitTheme.colors.primary,
                )
                Spacer(Modifier.width(8.dp))
                Column(content = content)
            } else {
                Column(content = content)
            }
        }
    }
}