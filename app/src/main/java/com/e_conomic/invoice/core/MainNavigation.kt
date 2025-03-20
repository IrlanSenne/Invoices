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
            route = "${Routes.INVOICE_DETAIL}?invoiceId={invoiceId}&title={title}&totalAmount={totalAmount}&date={date}&image={image}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = Routes.NEW_INVOICE
                },
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("image") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("totalAmount") {
                    type = NavType.FloatType
                    defaultValue = 0f
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
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val totalAmount = backStackEntry.arguments?.getFloat("totalAmount") ?: 0f

            InvoiceAddUpdateScreen(
                viewModel = mainViewModel,
                navController = navController,
                invoiceId = noteId,
                invoiceTitle = title,
                invoiceDate = date,
                totalAmount = totalAmount,
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

        fun addWithInvoiceDetails(invoiceId: String?, title: String?, date: String?, totalAmount: Float, image: String?) =
            "$INVOICE_DETAIL?invoiceId=$invoiceId&title=$title&date=${date}&totalAmount=${totalAmount}&image=${image}"
    }
}
