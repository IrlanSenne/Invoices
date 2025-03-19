package com.e_conomic.invoice.ui.screens

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.e_conomic.invoice.R
import com.e_conomic.invoice.core.MainViewModel
import com.e_conomic.invoice.core.Routes.Companion.NEW_INVOICE
import com.e_conomic.invoice.data.OnEvent
import com.e_conomic.invoice.data.Resource
import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.ui.components.InvoiceDialogPicture
import com.e_conomic.invoice.ui.components.InvoiceTextField
import com.e_conomic.invoice.ui.components.InvoiceTopBar
import com.e_conomic.invoice.utils.saveBitmapAndGetUri
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceAddUpdateScreen(
    viewModel: MainViewModel? = null,
    navController: NavController? = null,
    invoiceId: String? = null,
    invoiceTitle: String? = null,
    invoiceContent: String? = null,
    invoiceImage: String? = null
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val addNoteFlow = viewModel?.addInvoiceFlow?.collectAsState()?.value

    var title by remember { mutableStateOf(invoiceTitle ?: "") }
    var content by remember { mutableStateOf(invoiceContent ?: "") }
    var selectedImage by remember { mutableStateOf(invoiceImage ?: "") }

    var isErrorTitle by remember { mutableStateOf(false) }
    var showImagePickerDialog by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImage = it.toString()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveBitmapAndGetUri(context, bitmap)
            selectedImage = uri.toString()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            InvoiceTopBar(
                title = if (invoiceId != NEW_INVOICE) invoiceTitle ?: "" else stringResource(R.string.add_invoice),
                navigationIconClick = {
                    navController?.navigateUp()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            if (selectedImage.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(data = selectedImage),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(350.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Image")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    showImagePickerDialog = true
                }
            ) {
                Text(text = "Choose Image")
            }

            Spacer(modifier = Modifier.height(24.dp))

            InvoiceTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (it.isNotEmpty()) isErrorTitle = false
                },
                label = stringResource(R.string.title),
                isError = isErrorTitle,
                textStyle = TextStyle(fontSize = 24.sp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            InvoiceTextField(
                value = content,
                onValueChange = {
                    content = it
                },
                label = stringResource(R.string.content),
                isError = false,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5,
                height = 100.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.weight(1f).fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    onClick = {
                        isErrorTitle = title.isEmpty()

                        if (isErrorTitle) return@Button

                        val invoiceEntity = InvoiceEntity(
                            invoiceId = invoiceId.takeIf { it != NEW_INVOICE }
                                ?: Random.nextInt(1, Int.MAX_VALUE).toString(),
                            title = title,
                            content = content,
                            image = selectedImage
                        )

                        if (invoiceId != NEW_INVOICE) {
                            viewModel?.onEvent(OnEvent.UpdateInvoice(invoiceEntity))
                        } else {
                            viewModel?.onEvent(OnEvent.AddInvoice(invoiceEntity))
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                if (invoiceId != NEW_INVOICE) {
                    Button(
                        modifier = Modifier.weight(1f).fillMaxWidth(0.5f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        onClick = {
                            invoiceId?.let {
                                viewModel?.onEvent(
                                    OnEvent.DeleteInvoice(
                                        InvoiceEntity(
                                            invoiceId,
                                            title,
                                            content,
                                            selectedImage
                                        )
                                    )
                                )
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.delete),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(76.dp))
        }

        if (showImagePickerDialog) {
            InvoiceDialogPicture(
                onChooseFromGalleryClick = {
                    showImagePickerDialog = false
                    galleryLauncher.launch("image/*")
                },
                onTakePictureClick = {
                    showImagePickerDialog = false
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                },
                onDismissRequest = {
                    showImagePickerDialog = false
                }
            )
        }
    }

    when (addNoteFlow) {
        is Resource.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is Resource.Success -> {
            viewModel?.onEvent(OnEvent.ResetState)
            navController?.navigateUp()
        }
        else -> {}
    }
}

