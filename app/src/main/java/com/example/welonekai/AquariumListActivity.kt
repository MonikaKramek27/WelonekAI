package com.example.welonekai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AquariumListActivity : AppCompatActivity() {

    private val aquariumListViewModel: AquariumListViewModel by viewModels {
        AquariumViewModelFactory((application as WelonekApplication).database.aquariumDao())
    }

    private val addAquariumActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                val name = data.getStringExtra(AddAquariumActivity.EXTRA_NAME)
                val description = data.getStringExtra(AddAquariumActivity.EXTRA_DESCRIPTION)
                if (name != null && description != null) {
                    val aquarium = Aquarium(name = name, description = description)
                    aquariumListViewModel.insert(aquarium)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aquarium_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = AquariumListAdapter { aquarium ->
            val intent = Intent(this, AquariumDetailActivity::class.java)
            intent.putExtra(AquariumDetailActivity.EXTRA_AQUARIUM_ID, aquarium.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        aquariumListViewModel.allAquariums.observe(this) { aquariums ->
            aquariums?.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@AquariumListActivity, AddAquariumActivity::class.java)
            addAquariumActivityResultLauncher.launch(intent)
        }
    }
}
