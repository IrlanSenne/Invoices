package com.e_conomic.invoice.data.localdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.e_conomic.invoice.data.entities.InvoiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoicesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInvoice(note: InvoiceEntity)

    @Update
    suspend fun updateNote(note: InvoiceEntity)

    @Delete
    suspend fun deleteNote(note: InvoiceEntity)

    @Query("SELECT * FROM invoices_list")
    fun getAllNotes(): Flow<List<InvoiceEntity>>

    @Query("DELETE FROM invoices_list")
    suspend fun deleteAllNotes()
}