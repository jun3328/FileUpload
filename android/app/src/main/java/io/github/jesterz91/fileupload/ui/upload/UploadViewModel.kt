package io.github.jesterz91.fileupload.ui.upload

import io.github.jesterz91.fileupload.api.FileService
import io.github.jesterz91.fileupload.common.BaseViewModel
import io.github.jesterz91.fileupload.util.toRequestBody
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import org.jetbrains.anko.error
import org.jetbrains.anko.info

class UploadViewModel : BaseViewModel() {

    fun uploadFiles(files: List<MultipartBody.Part>) {
        FileService.service.upload("${System.currentTimeMillis()}".toRequestBody(), files)
            .subscribeOn(Schedulers.io())
            .subscribe({
                info(it)
            }, {
                error(it.message)
            }).addTo(disposables)
    }
}