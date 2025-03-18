package com.e_conomic.invoice.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.e_conomic.invoice.data.entities.InvoiceEntity

@Database(entities = [InvoiceEntity::class], version = 1, exportSchema = false)
abstract class InvoicesDataBase : RoomDatabase() {
    abstract fun invoiceDao(): InvoicesDao
}