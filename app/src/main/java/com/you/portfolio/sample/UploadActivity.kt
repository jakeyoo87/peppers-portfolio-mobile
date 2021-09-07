package com.you.portfolio.sample

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.you.portfolio.GlobalApplication
import com.you.portfolio.R
import kotlinx.android.synthetic.main.activity_upload.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadActivity : AppCompatActivity() {

    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        select_picture.setOnClickListener {
            getPicture()
        }
        upload_picture.setOnClickListener {
            uploadPost()
        }
    }

    private fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
            .setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            .setType("image/*")
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val uri: Uri? = data?.data
            filePath = getImageFilePath(uri)
        }
    }

    private fun getImageFilePath(uri: Uri?): String {
        var imageFilePath = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        val cursor: Cursor? = uri?.let {
            contentResolver.query(it, projection, null, null, null)
        }

        cursor?.let {
            if (it.moveToFirst()) {
                imageFilePath = it.getString(
                    it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                )
            }
        }

        return imageFilePath
    }

    private fun uploadPost() {
        val file = File(filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("image", file.name, fileRequestBody)
        val content = RequestBody.create(MediaType.parse("text/plain"), getContent())

        (application as GlobalApplication).service.uploadPost(
            part, content
        )
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        finish()
                        startActivity(Intent(this@UploadActivity, MyPostActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun getContent(): String {
        return content_input.text.toString()
    }
}