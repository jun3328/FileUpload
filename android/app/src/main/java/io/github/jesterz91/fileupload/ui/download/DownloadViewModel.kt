package io.github.jesterz91.fileupload.ui.download

import io.github.jesterz91.fileupload.api.FileService
import io.github.jesterz91.fileupload.common.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DownloadViewModel : BaseViewModel() {

    fun downloadFile(url: String, targetFile: File) {
        FileService.service.download(url)
            .subscribeOn(Schedulers.io())
            .subscribe({
                download(it, targetFile)
            }, {
                error(it.message)
            }).addTo(disposables)
    }

    private fun download(body: ResponseBody, downFile: File) {
        var downloaded: Long = 0
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileSize = body.contentLength()

        try {
            body.byteStream().use { inputStream ->
                FileOutputStream(downFile).use { outputStream ->
                    while (true) {
                        val read: Int = inputStream.read(buffer)
                        if (read == -1) {
                            break
                        }
                        outputStream.write(buffer, 0, read)
                        downloaded += read.toLong()
                        info("file download: $downloaded of $fileSize")
                    }
                    outputStream.flush()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}