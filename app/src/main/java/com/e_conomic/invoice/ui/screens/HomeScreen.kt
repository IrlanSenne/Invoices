package com.e_conomic.invoice.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.e_conomic.invoice.R
import com.e_conomic.invoice.core.MainViewModel
import com.e_conomic.invoice.core.Routes
import com.e_conomic.invoice.data.OnEvent
import com.e_conomic.invoice.data.Resource
import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.ui.components.InvoiceCard
import com.e_conomic.invoice.ui.components.InvoiceFloatingButton
import com.e_conomic.invoice.ui.components.InvoiceTopBar
import com.e_conomic.invoice.ui.components.InvoiceWarningEmptyBox
import com.e_conomic.invoice.ui.theme.EconomicInvoiceTheme

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel?,
    navController: NavController?
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val invoicesFlow = mainViewModel?.getInvoiceFlow?.collectAsState()?.value

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            InvoiceTopBar(
                title = stringResource(R.string.invoices)
            )
        },
        floatingActionButton = {
            InvoiceFloatingButton {
                navController?.navigate(Routes.addWithInvoiceDetails(Routes.NEW_INVOICE, "", "", ""))
            }
        }
    ) { paddingValues ->
        when (invoicesFlow) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            is Resource.Success -> {
                val invoices = invoicesFlow.result
                Box(modifier = Modifier.fillMaxSize()) {
                    if (invoices.isEmpty()) {
                        InvoiceWarningEmptyBox()
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 106.dp),
                        ) {
                            itemsIndexed(invoices) { index, invoice ->
                                InvoiceCard(invoice) {
                                    navController?.navigate(
                                        Routes.addWithInvoiceDetails(
                                            invoice.invoiceId,
                                            invoice.title,
                                            invoice.content,
                                            invoice.image
                                        )
                                    )
                                }
                            }

                            item {
                                Spacer(modifier = Modifier.height(96.dp))
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}

@PreviewDynamicColors
@Composable
fun NoteHomeScreenPreview() {
    EconomicInvoiceTheme {
        HomeScreen(null, null)
    }
}