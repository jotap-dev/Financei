package com.example.financei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.financei.applications.CountApplication
import com.example.financei.databinding.ActivityMainBinding
import com.example.financei.ui.adapters.CountListAdapter
import com.example.financei.ui.viewmodels.CountViewModel
import com.example.financei.ui.viewmodels.CountViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountListAdapter
    private lateinit var binding : ActivityMainBinding

    private val countViewModel : CountViewModel by  viewModels {
        CountViewModelFactory((application as CountApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.adapter = CountListAdapter()
        this.recyclerView = binding.recyclerView
        recyclerView.adapter = this.adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()

        binding.addNewCount.setOnClickListener {
            val intent = Intent(this, AddCountActivity::class.java)
            startActivity(intent)
        }

        countViewModel.allCounts.observe(this, Observer { counts ->
            counts?.let { adapter.submitList(it) }
        })

    }
}