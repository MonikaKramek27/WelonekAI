package com.example.welonekai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddLogEntryActivity : AppCompatActivity() {

    private lateinit var editPhView: EditText
    private lateinit var waterChangedCheckBox: CheckBox
    private lateinit var editNotesView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_log_entry)
        editPhView = findViewById(R.id.edit_ph)
        waterChangedCheckBox = findViewById(R.id.checkbox_water_changed)
        editNotesView = findViewById(R.id.edit_notes)

        val button = findViewById<Button>(R.id.button_save_log)
        button.setOnClickListener {
            val replyIntent = Intent()
            val ph = editPhView.text.toString().toDoubleOrNull()
            if (ph == null) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val waterChanged = waterChangedCheckBox.isChecked
                val notes = editNotesView.text.toString()
                replyIntent.putExtra(EXTRA_PH, ph)
                replyIntent.putExtra(EXTRA_WATER_CHANGED, waterChanged)
                replyIntent.putExtra(EXTRA_NOTES, notes)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_PH = "com.example.welonekai.PH"
        const val EXTRA_WATER_CHANGED = "com.example.welonekai.WATER_CHANGED"
        const val EXTRA_NOTES = "com.example.welonekai.NOTES"
    }
}
