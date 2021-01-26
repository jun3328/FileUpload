package io.github.jesterz91.fileupload.ui.progress

interface ProgressListener {
    fun onProgress(percentage: Int)
    fun onError(e: Exception)
    fun onFinish()
}