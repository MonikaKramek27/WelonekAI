package com.example.welonekai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.TextView

class AquariumDetailActivity : AppCompatActivity() {

    private var aquariumId: Long = -1
    private lateinit var aquariumNameTextView: TextView
    private lateinit var aquariumDescriptionTextView: TextView
    private lateinit var aquariumDetailViewModel: AquariumDetailViewModel

    private val addLogEntryResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                val ph = data.getDoubleExtra(AddLogEntryActivity.EXTRA_PH, 0.0)
                val waterChanged = data.getBooleanExtra(AddLogEntryActivity.EXTRA_WATER_CHANGED, false)
                val notes = data.getStringExtra(AddLogEntryActivity.EXTRA_NOTES)
                if (notes != null) {
                    val logEntry = LogEntry(
                        aquariumId = aquariumId,
                        ph = ph,
                        waterChanged = waterChanged,
                        notes = notes
                    )
                    aquariumDetailViewModel.insertLogEntry(logEntry)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aquarium_detail)

        aquariumNameTextView = findViewById(R.id.aquarium_name_detail)
        aquariumDescriptionTextView = findViewById(R.id.aquarium_description_detail)

        aquariumId = intent.getLongExtra(EXTRA_AQUARIUM_ID, -1)
        if (aquariumId == -1L) {
            finish()
            return
        }

        val viewModelFactory = AquariumDetailViewModelFactory((application as WelonekApplication).database.aquariumDao(), aquariumId)
        aquariumDetailViewModel = ViewModelProvider(this, viewModelFactory).get(AquariumDetailViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.log_recyclerview)
        val adapter = LogEntryListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        aquariumDetailViewModel.aquarium.observe(this) { aquarium ->
            aquarium?.let {
                aquariumNameTextView.text = it.name
                aquariumDescriptionTextView.text = it.description
                supportActionBar?.title = it.name
            }
        }

        aquariumDetailViewModel.logEntries.observe(this) { logEntries ->
            logEntries?.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab_add_log)
        fab.setOnClickListener {
            val intent = Intent(this, AddLogEntryActivity::class.java)
            addLogEntryResultLauncher.launch(intent)
        }
    }

    companion object {
        const val EXTRA_AQUARIUM_ID = "com.example.welonekai.AQUARIUM_ID"
    }
}
