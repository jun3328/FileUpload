package io.github.jesterz91.fileupload.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import org.jetbrains.anko.AnkoLogger
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope, AnkoLogger {

    protected val disposables by lazy { CompositeDisposable() }

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        job.cancelChildren()
    }
}