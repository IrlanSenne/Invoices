package com.e_conomic.invoice.core.di

import android.content.Context
import androidx.room.Room
import com.e_conomic.invoice.data.localdatabase.InvoicesDao
import com.e_conomic.invoice.data.localdatabase.InvoicesDataBase
import com.e_conomic.invoice.data.repository.InvoiceRepository
import com.e_conomic.invoice.data.repository.InvoiceRepositoryImpl
import com.e_conomic.invoice.data.usecases.DeleteInvoiceUseCase
import com.e_conomic.invoice.data.usecases.GetInvoicesUseCase
import com.e_conomic.invoice.data.usecases.SaveInvoiceUseCase
import com.e_conomic.invoice.data.usecases.UpdateInvoiceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context): InvoicesDataBase {
        return Room.databaseBuilder(
            context = context,
            InvoicesDataBase::class.java,
            "invoices_database"
        ).build()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object DatabaseModule {
        @Provides
        @Singleton
        fun provideNoteDao(database: InvoicesDataBase): InvoicesDao {
            return database.invoiceDao()
        }
    }

    @Provides
    @Singleton
    fun provideInvoicesRepository(impl: InvoiceRepositoryImpl): InvoiceRepository = impl


    @Provides
    @Singleton
    fun provideGetInvoicesUseCase(repository: InvoiceRepository): GetInvoicesUseCase {
        return GetInvoicesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveInvoicesUseCase(repository: InvoiceRepository): SaveInvoiceUseCase {
        return SaveInvoiceUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateInvoicesUseCase(repository: InvoiceRepository): UpdateInvoiceUseCase {
        return UpdateInvoiceUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteInvoicesUseCase(repository: InvoiceRepository): DeleteInvoiceUseCase {
        return DeleteInvoiceUseCase(repository)
    }
}