package com.bogadevelopment.heirloomrecipes.core.ui.components.cards

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomText

@Composable
fun ExpandableGeneralCard(
    tittle: String,
    text: String,
    maxHeightFraction: Float,
    modifier: Modifier,
    isExpanded: Boolean,
    onExpandedChanged: () -> Unit,
    onTextChanged: (String) -> Unit
) {

    Card(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val maxCardHeight = maxHeight * maxHeightFraction

            Column(
                Modifier.background(MaterialTheme.colorScheme.secondary)
                    .clickable { onExpandedChanged() }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomText(
                        tittle,
                        MaterialTheme.typography.displayMedium,
                        MaterialTheme.colorScheme.onBackground,
                        Modifier.padding(top = 3.dp, bottom = 3.dp, start = 5.dp)
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collar" else "Expand"
                    )
                }

                if (isExpanded) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
                    OutlinedTextField(
                        value = text,
                        onValueChange = { onTextChanged(it) },
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = maxCardHeight)
                            .padding(3.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                            focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        )
                    )
                }
            }
        }
    }
}