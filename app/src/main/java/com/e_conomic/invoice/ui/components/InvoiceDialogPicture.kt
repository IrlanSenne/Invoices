package com.e_conomic.invoice.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.e_conomic.invoice.R

@ExperimentalMaterial3Api
@Composable
fun InvoiceDialogPicture(
    onDismissRequest: () -> Unit,
    onTakePictureClick: () -> Unit,
    onChooseFromGalleryClick: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() },
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.choose_image_resource), style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onDismissRequest()
                        onTakePictureClick()
                    }
                ) {
                    Text(stringResource(R.string.take_picture))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onDismissRequest()
                        onChooseFromGalleryClick()
                    }
                ) {
                    Text(stringResource(R.string.choose_from_gallery))
                }
            }
        }
    )
}
