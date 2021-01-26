package io.github.jesterz91.fileupload.ui.download

import androidx.activity.viewModels
import com.jakewharton.rxbinding3.view.clicks
import io.github.jesterz91.fileupload.R
import io.github.jesterz91.fileupload.common.BaseActivity
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_download.*
import java.io.File

class DownloadActivity : BaseActivity() {

    override val layoutResId: Int
        get() = R.layout.activity_download

    private val downloadViewModel by viewModels<DownloadViewModel>()

    override fun bind() {
        download.clicks()
            .subscribe {
                val url = "https://i.picsum.photos/id/668/200/200.jpg"
                val targetFile = File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
                downloadViewModel.downloadFile(url, targetFile)
            }.addTo(viewDisposables)
    }
}