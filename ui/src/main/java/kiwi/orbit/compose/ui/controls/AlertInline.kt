package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.LocalAlertScope
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInteractiveTheme
import kiwi.orbit.compose.ui.foundation.asSuccessTheme
import kiwi.orbit.compose.ui.foundation.asWarningTheme

@Composable
public fun AlertInlineInfo(
    title: @Composable () -> Unit,
    action: @Composable RowScope.() -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.InformationCircle,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asInteractiveTheme(suppressed),
    ) {
        AlertInline(
            icon = icon,
            title = title,
            action = action,
            onActionClick = onActionClick,
            modifier = modifier,
        )
    }
}

@Composable
public fun AlertInlineSuccess(
    title: @Composable () -> Unit,
    action: @Composable RowScope.() -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.CheckCircle,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asSuccessTheme(suppressed),
    ) {
        AlertInline(
            icon = icon,
            title = title,
            action = action,
            onActionClick = onActionClick,
            modifier = modifier,
        )
    }
}

@Composable
public fun AlertInlineWarning(
    title: @Composable () -> Unit,
    action: @Composable RowScope.() -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.AlertCircle,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asWarningTheme(suppressed),
    ) {
        AlertInline(
            icon = icon,
            title = title,
            action = action,
            onActionClick = onActionClick,
            modifier = modifier,
        )
    }
}

@Composable
public fun AlertInlineCritical(
    title: @Composable () -> Unit,
    action: @Composable RowScope.() -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.AlertOctagon,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asCriticalTheme(suppressed),
    ) {
        AlertInline(
            icon = icon,
            title = title,
            action = action,
            onActionClick = onActionClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun AlertInline(
    icon: Painter?,
    title: @Composable () -> Unit,
    action: @Composable RowScope.() -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bgColor = OrbitTheme.colors.surface.background
    val borderColor = OrbitTheme.colors.content.subtle.copy(0.08f)
    val accentColor = OrbitTheme.colors.primary.main
    val shape = OrbitTheme.shapes.normal
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .drawBehind {
                drawRect(bgColor)
                drawLine(
                    color = accentColor,
                    start = Offset.Zero,
                    end = Offset(size.width, 0f),
                    strokeWidth = 6.dp.toPx(), // doubled width to account for path offset
                )
            }
            .border(1.dp, borderColor, shape)
            .padding(
                top = 8.dp + 3.dp, // stroke width
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp,
            ),
        propagateMinConstraints = true,
    ) {
        AlertInlineContent {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp),
                    tint = accentColor,
                )
            } else {
                Spacer(Modifier.width(4.dp))
            }
            ProvideMergedTextStyle(
                OrbitTheme.typography.bodyNormal.copy(fontWeight = FontWeight.Bold)
            ) {
                title()
            }
            CompositionLocalProvider(
                LocalAlertScope provides true
            ) {
                ButtonPrimary(
                    onClick = onActionClick,
                    content = action,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun AlertInlineContent(
    content: @Composable () -> Unit,
) {
    Layout(content = content) { measurables, constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val iconPlaceable = measurables[0].measure(looseConstraints)
        val actionPlaceable = measurables[2].measure(looseConstraints)

        val textConstraints = constraints.offset(
            horizontal = -(iconPlaceable.width + actionPlaceable.width),
        )
        val textPlaceable = measurables[1].measure(textConstraints)

        val width = iconPlaceable.width + textPlaceable.width + actionPlaceable.width
        val height = maxOf(iconPlaceable.height, textPlaceable.height, actionPlaceable.height)

        layout(width = width, height) {
            if (textPlaceable.height < height) {
                iconPlaceable.placeRelative(
                    x = 0,
                    y = (height - iconPlaceable.height) / 2,
                )
                textPlaceable.placeRelative(
                    x = iconPlaceable.width,
                    y = (height - textPlaceable.height) / 2,
                )
                actionPlaceable.placeRelative(
                    x = iconPlaceable.width + textPlaceable.width,
                    y = (height - actionPlaceable.height) / 2,
                )
            } else {
                iconPlaceable.placeRelative(0, 0)
                textPlaceable.placeRelative(iconPlaceable.width, 0)
                actionPlaceable.placeRelative(iconPlaceable.width + textPlaceable.width, 0)
            }
        }
    }
}