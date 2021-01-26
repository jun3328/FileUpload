package io.github.jesterz91.fileupload.ui.progress

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.jakewharton.rxbinding3.view.clicks
import io.github.jesterz91.fileupload.R
import io.github.jesterz91.fileupload.common.BaseActivity
import io.github.jesterz91.fileupload.util.FileUtil
import io.github.jesterz91.fileupload.util.REQUEST_CODE_PROGRESS
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_progress.*
import java.io.File

class ProgressActivity : BaseActivity() {
    override val layoutResId: Int
        get() = R.layout.activity_progress

    private val progressViewModel by viewModels<ProgressViewModel>()

    override fun bind() {
        progressUpload.clicks()
            .map { Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" } }
            .subscribe {
                startActivityForResult(Intent.createChooser(it, "파일선택"), REQUEST_CODE_PROGRESS)
            }.addTo(viewDisposables)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_CODE_PROGRESS -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let {  uri ->
                        FileUtil.getRealPathFromURI(this, uri).let {
                            progressViewModel.uploadWithProgress(File(it))
                        }
                    }
                }
            }
        }
    }
}