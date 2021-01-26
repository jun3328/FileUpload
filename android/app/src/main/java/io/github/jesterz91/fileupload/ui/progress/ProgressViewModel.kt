package io.github.jesterz91.fileupload.ui.progress

import io.github.jesterz91.fileupload.api.FileService
import io.github.jesterz91.fileupload.api.ProgressRequestBody
import io.github.jesterz91.fileupload.common.BaseViewModel
import io.github.jesterz91.fileupload.util.toRequestBody
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.jetbrains.anko.wtf
import java.io.File

class ProgressViewModel: BaseViewModel(), ProgressListener {

    fun uploadWithProgress(file: File) {
        val progressBody =
            ProgressRequestBody(file, this)
        val filePart = MultipartBody.Part.createFormData("photos", "image.${file.extension}", progressBody)

        FileService.service.upload("${System.currentTimeMillis()}".toRequestBody(), listOf(filePart))
            .subscribeOn(Schedulers.io())
            .subscribe({
                info(it)
            }, {
                error(it.message)
            }).addTo(disposables)
    }

    override fun onProgress(percentage: Int) {
        wtf("$percentage %")
    }

    override fun onError(e: Exception) {
        wtf(e.message)
    }

    override fun onFinish() {
        wtf("finish")
    }
}