package com.example.welonekai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddAquariumActivity : AppCompatActivity() {

    private lateinit var editAquariumNameView: EditText
    private lateinit var editAquariumDescriptionView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_aquarium)
        editAquariumNameView = findViewById(R.id.edit_aquarium_name)
        editAquariumDescriptionView = findViewById(R.id.edit_aquarium_description)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editAquariumNameView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editAquariumNameView.text.toString()
                val description = editAquariumDescriptionView.text.toString()
                replyIntent.putExtra(EXTRA_NAME, name)
                replyIntent.putExtra(EXTRA_DESCRIPTION, description)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_NAME = "com.example.welonekai.NAME"
        const val EXTRA_DESCRIPTION = "com.example.welonekai.DESCRIPTION"
    }
}
