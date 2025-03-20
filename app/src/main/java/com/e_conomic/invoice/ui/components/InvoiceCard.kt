package com.e_conomic.invoice.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.data.utils.formatDateFromMillis
import com.e_conomic.invoice.data.utils.formatToDollar

@Composable
fun InvoiceCard(
    invoiceEntity: InvoiceEntity,
    onclick: (id: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .clickable(enabled = true) {
                onclick(invoiceEntity.invoiceId)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text(
                    text = invoiceEntity.title,
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatToDollar(invoiceEntity.totalAmount),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = rememberImagePainter(data = invoiceEntity.image),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatDateFromMillis(invoiceEntity.date.toLong()),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}