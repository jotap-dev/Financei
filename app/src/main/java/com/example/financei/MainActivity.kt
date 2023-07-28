package com.example.financei

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financei.applications.CountApplication
import com.example.financei.database.models.Count
import com.example.financei.databinding.ActivityMainBinding
import com.example.financei.ui.adapters.CountListAdapter
import com.example.financei.ui.viewmodels.CountViewModel
import com.example.financei.ui.viewmodels.CountViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if (result.resultCode == RESULT_OK){
                val data: Intent? = result.data
                val valor = data?.getStringExtra("valor")?.toDouble()
                val categoria = data?.getStringExtra("categoria")

                if (valor != null && categoria != null){
                    countViewModel.insert(Count(0, valor, categoria, setDateNow()))
                }

            }
        }

        binding.addNewCount.setOnClickListener {
            resultLauncher.launch(Intent(this, AddCountActivity::class.java))
        }

        countViewModel.allCounts.observe(this) { counts ->
            counts?.let { adapter.submitList(it) }
        }

    }

    override fun onResume() {
        super.onResume()

        fun toastWarningOnScreen(section: String){
            Toast.makeText(
                this@MainActivity,
                "$section on working!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.control.setOnClickListener { toastWarningOnScreen("Dashboards") }
        binding.boxes.setOnClickListener { toastWarningOnScreen("Wallet") }
        binding.metas.setOnClickListener { toastWarningOnScreen("Metas(to-do)") }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDateNow(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyy"))
    }
}