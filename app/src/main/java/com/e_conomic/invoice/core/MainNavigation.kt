package com.e_conomic.invoice.core

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.e_conomic.invoice.ui.screens.HomeScreen
import com.e_conomic.invoice.ui.screens.InvoiceAddUpdateScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainNavigation(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(
            route = Routes.HOME,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
        ) {
            HomeScreen(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }

        composable(
            route = "${Routes.INVOICE_DETAIL}?invoiceId={invoiceId}&title={title}&content={content}&image={image}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = Routes.NEW_INVOICE
                },
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("content") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("image") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("invoiceId") ?: Routes.NEW_INVOICE
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val image = backStackEntry.arguments?.getString("image") ?: ""
            val content = backStackEntry.arguments?.getString("content") ?: ""

            InvoiceAddUpdateScreen(
                viewModel = mainViewModel,
                navController = navController,
                invoiceId = noteId,
                invoiceTitle = title,
                invoiceContent = content,
                invoiceImage = image
            )
        }
    }
}

class Routes {
    companion object {
        const val HOME = "home"
        const val INVOICE_DETAIL = "invoice_detail"
        const val NEW_INVOICE = "-1"

        fun addWithInvoiceDetails(invoiceId: String?, title: String?, content: String?, image: String?) =
            "$INVOICE_DETAIL?invoiceId=$invoiceId&title=$title&content=${content}&image=${image}"
    }
}
