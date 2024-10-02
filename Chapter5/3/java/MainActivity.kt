package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textInputField: EditText
    private lateinit var imageSelectButton: Button
    private lateinit var locationInputField: EditText
    private lateinit var hashtagInputField: EditText
    private lateinit var uploadButton: Button
    private lateinit var selectedImageView: ImageView

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 각 UI 컴포넌트 연결
        textInputField = findViewById(R.id.text_input_field)
        imageSelectButton = findViewById(R.id.image_select_button)
        selectedImageView = findViewById(R.id.selected_image_view)
        locationInputField = findViewById(R.id.location_input_field)
        hashtagInputField = findViewById(R.id.hashtag_input_field)
        uploadButton = findViewById(R.id.upload_button)
        // 이미지 선택 버튼 클릭 리스너 설정
        imageSelectButton.setOnClickListener {
            selectImage()
        }
        // 업로드 버튼 클릭 리스너 설정
        uploadButton.setOnClickListener {
            uploadPost()
        }
    }

    private fun selectImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data:
        Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==
            Activity.RESULT_OK && data != null && data.data != null
        ) {
            val selectedImageUri: Uri? = data.data
            selectedImageView.setImageURI(selectedImageUri)
        }
    }

    private fun uploadPost() {
        // 입력된 게시물 데이터 가져오기
        val text = textInputField.text.toString()
        val location = locationInputField.text.toString()
        val hashtags = hashtagInputField.text.toString()
        // 유효성 검사
        if (text.isEmpty() || location.isEmpty() || hashtags.isEmpty()) {
            Toast.makeText(this, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        // 게시물 업로드 처리
        Toast.makeText(this, "게시물이 업로드되었습니다.", Toast.LENGTH_SHORT).show()
    }
}


