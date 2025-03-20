package com.e_conomic.invoice.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoices_list")
data class InvoiceEntity(
    @PrimaryKey
    var invoiceId: String = "",
    var title: String = "",
    var date: String = System.currentTimeMillis().toString(),
    var totalAmount: Float = 0f,
    val image: String = ""
)
