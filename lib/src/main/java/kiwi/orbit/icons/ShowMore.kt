package kiwi.orbit.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import kiwi.orbit.R

@get:Composable
public val Icons.ShowMore: ImageVector
	get() {
		if (icon != null) return icon!!
		icon = vectorResource(id = R.drawable.ic_show_more)
		return icon!!
	}

private var icon: ImageVector? = null
