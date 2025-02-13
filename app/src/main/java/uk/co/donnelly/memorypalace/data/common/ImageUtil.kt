package uk.co.donnelly.memorypalace.data.common

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.util.UUID

interface ImageUtil {
    fun storeImageAsFile(imageLocation: String, id: Long): String
}

class ImageUtilImpl(val context: Context) : ImageUtil {
    override fun storeImageAsFile(imageLocation: String,  id: Long): String {
        val input =
            context.contentResolver.openInputStream(Uri.parse(imageLocation)) ?: return imageLocation
        val uuid = UUID.randomUUID().toString()

        // Delete any existing file that contain the same palace id
        File(context.filesDir.toURI()).listFiles()?.forEach { file ->
            if (file.name.contains("palace-$id")) {
                file.delete()
            }
        }
        val outputFile = context.filesDir.resolve("palace-$id-$uuid.jpg")
        input.copyTo(outputFile.outputStream())

        val newImageUri = outputFile.toUri()
        return newImageUri.toString()
    }
}

class ImageUtilFake: ImageUtil {
    override fun storeImageAsFile(imageLocation: String, id: Long): String {
        return imageLocation
    }
}