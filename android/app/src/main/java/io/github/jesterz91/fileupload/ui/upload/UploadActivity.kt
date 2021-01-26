package io.github.jesterz91.fileupload.ui.upload

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.jakewharton.rxbinding3.view.clicks
import io.github.jesterz91.fileupload.R
import io.github.jesterz91.fileupload.common.BaseActivity
import io.github.jesterz91.fileupload.util.FileUtil
import io.github.jesterz91.fileupload.util.REQUEST_CODE_FILE
import io.github.jesterz91.fileupload.util.toPart
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_upload.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File

class UploadActivity : BaseActivity() {

    override val layoutResId: Int
        get() = R.layout.activity_upload

    private val uploadViewModel by viewModels<UploadViewModel>()

    private val filePathList = mutableListOf<String>()

    override fun bind() {
        selectButton.clicks()
            .map {
                Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
            }
            .subscribe { selectFiles(it) }
            .addTo(viewDisposables)

        sendButton.clicks()
            .filter { filePathList.isNotEmpty() }
            .map { getFiles() }
            .subscribe(uploadViewModel::uploadFiles)
            .addTo(viewDisposables)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_FILE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.clipData?.let {
                        for (i in 0 until it.itemCount) {
                            val uri = it.getItemAt(i)?.uri
                            filePathList.add(FileUtil.getRealPathFromURI(this, uri))
                        }
                    } ?: run {
                        val uri = data.data
                        filePathList.add(FileUtil.getRealPathFromURI(this, uri))
                    }
                    textView.text = filePathList.toString()
                }
            }
        }
    }

    private fun getFiles(): List<MultipartBody.Part> {
        return filePathList.mapIndexed { index, path ->
            File(path).toPart("image/*".toMediaTypeOrNull(), "photos", "img_$index")
        }
    }

    private fun selectFiles(intent: Intent) =
        startActivityForResult(Intent.createChooser(intent, "파일선택"), REQUEST_CODE_FILE)
}