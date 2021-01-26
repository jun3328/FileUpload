package io.github.jesterz91.fileupload.api

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


interface FileApi {

    @Multipart
    @POST("upload")
    fun upload(
        @Part("folder") folderName: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Single<UploadResponse>

    @GET
    fun download(@Url fileUrl: String): Single<ResponseBody>
}