package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var noteEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var noteListView: ListView
    private lateinit var notesDir: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        noteEditText = findViewById(R.id.noteEditText)
        saveButton = findViewById(R.id.saveButton)
        noteListView = findViewById(R.id.noteListView)
        // 메모를 저장할 디렉토리 설정(앱의 내부 저장소)
        notesDir = File(filesDir, "notes")
        if (!notesDir.exists()) {
            notesDir.mkdir()
        }
        // 저장된 메모 목록 불러오기
        loadNotes()
        saveButton.setOnClickListener {
            saveNote()
        }
        // 메모 선택 시 해당 메모를 불러오기
        noteListView.setOnItemClickListener { _, _, position, _ ->
            val selectedNoteFile = noteListView.getItemAtPosition(position)
                    as String
            loadNoteContent(selectedNoteFile)
        }
    }

    private fun saveNote() {
        val noteContent = noteEditText.text.toString()
        if (noteContent.isNotEmpty()) {
            val fileName = "note_${System.currentTimeMillis()}.txt"
            val noteFile = File(notesDir, fileName)
            noteFile.writeText(noteContent)
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            noteEditText.text.clear()
            // 저장 후 메모 목록 갱신
            loadNotes()
        } else {
            Toast.makeText(this, "Please write a note", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadNotes() {
        val noteFiles = notesDir.listFiles()
        val noteFileNames = noteFiles?.map { it.name } ?: listOf()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, noteFileNames
        )
        noteListView.adapter = adapter
    }

    private fun loadNoteContent(fileName: String) {
        val noteFile = File(notesDir, fileName)
        if (noteFile.exists()) {
            val noteContent = noteFile.readText()
            noteEditText.setText(noteContent)
            Toast.makeText(this, "Loaded: $fileName", Toast.LENGTH_SHORT).show()
        }
    }
}
