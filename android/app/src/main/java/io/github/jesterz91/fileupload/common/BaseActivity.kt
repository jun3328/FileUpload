package io.github.jesterz91.fileupload.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger

abstract class BaseActivity: AppCompatActivity(), AnkoLogger {

    protected val viewDisposables by lazy { CompositeDisposable() }

    abstract val layoutResId: Int

    abstract fun bind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        bind()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDisposables.clear()
    }
}