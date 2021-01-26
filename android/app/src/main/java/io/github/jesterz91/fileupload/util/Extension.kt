package io.github.jesterz91.fileupload.util

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

// 문자를 RequestBody 로 변환
fun String.toRequestBody(): RequestBody {
    return RequestBody.create(MultipartBody.FORM, this)
}

// 파일을 MultipartBody.Part 로 변환
fun File.toPart(contentType: MediaType?, formName: String, fileName: String) : MultipartBody.Part {
    val requestBody = RequestBody.create(contentType, this)
    return MultipartBody.Part.createFormData(formName, "$fileName.${this.extension}", requestBody)
}