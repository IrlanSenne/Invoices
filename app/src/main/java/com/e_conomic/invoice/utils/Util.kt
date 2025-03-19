package com.e_conomic.invoice.utils

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


