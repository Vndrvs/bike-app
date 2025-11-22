package gde.bike.mobileapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import gde.bike.mobileapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val api: ApiService by lazy {
        Network().apiService
    }

    // mivel később hozzuk létre a contextet, ezért lateinit variable-t hozunk létre
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.buildDb(this)

        binding.button.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("text", this.getString(R.string.app_name))
            startActivity(intent)
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        api.loadBicycles().enqueue(object: Callback<List<Bike>> {
            override fun onResponse(
                call: Call<List<Bike>?>, response: Response<List<Bike>?>
            ) {
                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    db.bikeDao().insert(list)

                    binding.recyclerView.adapter = TextAdapter(
                        items = list,
                        // ez egy 'lambda funkció', kell egyenlőségjel elé
                        onItemClick = {
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra(DetailActivity.EXTRA_ID, it.id)
                            startActivity(intent)
                        }
                    )
                } else {
                    println("communication error")
                }
            }

            override fun onFailure(call: Call<List<Bike>?>, t: Throwable) {
                AlertDialog.Builder(this@MainActivity)
                    .setMessage("no network")
                    .show()
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
