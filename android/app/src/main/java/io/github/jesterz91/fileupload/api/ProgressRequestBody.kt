package io.github.jesterz91.fileupload.api

import io.github.jesterz91.fileupload.ui.progress.ProgressListener
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ProgressRequestBody(
    private val file: File,
    private val progressListener: ProgressListener
) : RequestBody() {

    override fun contentType(): MediaType? = "image/*".toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        var uploaded: Long = 0
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileSize = file.length()

        try {
            FileInputStream(file).use { inputStream ->
                while (true) {
                    val read: Int = inputStream.read(buffer)
                    if (read == -1) {
                        break
                    }
                    sink.write(buffer, 0, read)
                    uploaded += read.toLong()
                    progressListener.onProgress(((uploaded * 100) / fileSize).toInt())
                }
            }
            progressListener.onFinish()
        } catch (e: IOException) {
            progressListener.onError(e)
        }
    }
}