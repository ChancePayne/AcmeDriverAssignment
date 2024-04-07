package com.cmpayne.acmedriverassignment.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cmpayne.acmedriverassignment.R
import com.cmpayne.acmedriverassignment.presentation.ui.theme.AcmeDriverAssignmentTheme

object ListItem {
    @Composable
    operator fun invoke(
        title: String,
        modifier: Modifier = Modifier,
        titleStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        titleColor: Color = MaterialTheme.colorScheme.onSurface,
        subtitle: String? = null,
        subtitleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
        subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        onTap: (() -> Unit)? = null,
        horizontalPadding: Dp = 24.dp,
    ) {
        Row(
            modifier = modifier
                .clickable { onTap?.invoke() }
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1f)) {
                Text(text = title, style = titleStyle, color = titleColor)
                subtitle?.let { Text(text = it, style = subtitleStyle, color = subtitleColor) }
            }
            onTap?.let {
                Icon(
                    Icons.Rounded.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.content_icon_action)
                )
            }
        }
    }

    data class PreviewData(
        val title: String = "Knights Radiant",
        val subtitle: String? = "Life before death. Strength before weakness. Journey before destination.",
        val onTap: (() -> Unit)? = {},
    )
}

class ListItemParameterProvider : PreviewParameterProvider<ListItem.PreviewData> {
    override val values = sequenceOf(
        ListItem.PreviewData(),
        ListItem.PreviewData(subtitle = null),
        ListItem.PreviewData(onTap = null),
        ListItem.PreviewData(subtitle = null, onTap = null),
    )
}

@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ListItemPreview(@PreviewParameter(ListItemParameterProvider::class) data: ListItem.PreviewData) {
    AcmeDriverAssignmentTheme {
        ListItem(
            title = data.title,
            subtitle = data.subtitle,
            onTap = data.onTap
        )
    }
}