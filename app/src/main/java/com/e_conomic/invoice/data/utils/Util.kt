package com.e_conomic.invoice.data.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun saveBitmapAndGetUri(context: Context, bitmap: Bitmap): Uri? {
    val filename = "image_${System.currentTimeMillis()}.png"
    var fos: OutputStream? = null
    var imageUri: Uri? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            imageUri = it
            fos = resolver.openOutputStream(it)
        }
    } else {
        val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
        imageUri = Uri.fromFile(image)
    }

    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }

    return imageUri
}

fun saveImageFromUri(context: Context, uri: Uri): String? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
    val outputStream = FileOutputStream(file)

    inputStream.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return file.absolutePath
}

fun formatDateFromMillis(timeInMillis: Long): String {
    val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)
    val date = Date(timeInMillis)
    return sdf.format(date)
}

fun formatToDollar(amount: Float): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    return numberFormat.format(amount)
}


