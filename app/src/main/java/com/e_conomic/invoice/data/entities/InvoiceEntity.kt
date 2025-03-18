package com.e_conomic.invoice.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoices_list")
data class InvoiceEntity(
    @PrimaryKey
    var invoiceId: String = "",
    var title: String = "",
    var content: String = "",
    val image: String = ""
)
