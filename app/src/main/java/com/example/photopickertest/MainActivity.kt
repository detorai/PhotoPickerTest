package com.example.photopickertest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter

    private val images = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.imageRecyclerView)
        imageAdapter = ImageAdapter(this, images)
        recyclerView.adapter = imageAdapter

        // Изначально список пуст, поэтому кнопка "Добавить" будет первым элементом
    }

    // Обработка результата выбора изображения
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            val filePath: String? = getRealPathFromURI(uri) // Получите путь к файлу из URI

            if (filePath != null) {
                imageAdapter.addImage(filePath)
            }
        }
    }

    // Функция для получения пути к файлу из URI
    private fun getRealPathFromURI(contentUri: Uri?): String? {
        if (contentUri == null) return null

        val cursor = contentResolver.query(contentUri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return null
    }
}