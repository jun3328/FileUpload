package io.github.jesterz91.fileupload.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding3.view.clicks
import io.github.jesterz91.fileupload.R
import io.github.jesterz91.fileupload.common.BaseActivity
import io.github.jesterz91.fileupload.ui.download.DownloadActivity
import io.github.jesterz91.fileupload.ui.progress.ProgressActivity
import io.github.jesterz91.fileupload.ui.upload.UploadActivity
import io.github.jesterz91.fileupload.util.REQUEST_CODE_PERMISSION
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    override val layoutResId: Int
        get() = R.layout.activity_main

    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
    }

    override fun bind() {
        val buttons = listOf(
            moveUpload.clicks().map { Intent(this, UploadActivity::class.java) },
            moveDownload.clicks().map { Intent(this, DownloadActivity::class.java) },
            moveProgress.clicks().map { Intent(this, ProgressActivity::class.java) }
        )
        Observable.merge(buttons)
            .filter { check() }
            .subscribe { startActivity(it) }
            .addTo(viewDisposables)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                if (check()) toast("권한허용") else toast("권한거부")
            }
        }
    }

    private fun requestPermissions() {
        if (!check()) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION)
            return
        }
    }

    private fun check(): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}