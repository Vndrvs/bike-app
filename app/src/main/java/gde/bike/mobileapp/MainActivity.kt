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

    // ViewBinding az activity layout elemeinek eléréséhez findViewById használata nélkül
    lateinit var binding: ActivityMainBinding

    // Retrofit API szolgáltatás, csak első használatkor jön létre
    val api: ApiService by lazy {
        Network().apiService
    }

    // ez lesz az adatbázisunk példányba
    // mivel később hozzuk létre a contextet, ezért lateinit variable-t hozunk létre
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // "top bar" alatti ablak megjelenítéshez

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // room db inicializálás
        db = AppDatabase.buildDb(this)

        // RecyclerView beállítás, függőleges listával
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // biciklik lekérése API-ból
        api.loadBicycles().enqueue(object: Callback<List<Bike>> {
            override fun onResponse(
                call: Call<List<Bike>?>, response: Response<List<Bike>?>
            ) {
                if (response.isSuccessful) { // sikeres API request eset
                    val list = response.body() ?: emptyList()
                    // adatok mentése a local adatbázisunkba
                    db.bikeDao().insert(list)

                    // Recyclerview feltöltése itemekkel
                    binding.recyclerView.adapter = TextAdapter(
                        items = list,

                        // Itemre klikk esetén következő ablakra ugrás, részletek kijelzéséhez
                        onItemClick = { // ez egy 'lambda funkció', kell egyenlőségjel elé!
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra(DetailActivity.EXTRA_ID, it.id)
                            startActivity(intent)
                        }
                    )
                } else { // sikertelen API request eset (nem megfelelő HTTP válasz)
                    println("communication error") // hibaüzenet
                }
            }

            override fun onFailure(call: Call<List<Bike>?>, t: Throwable) {
                // network hiba esetén felugró ablak
                AlertDialog.Builder(this@MainActivity)
                    .setMessage("no network")
                    .show()
            }
        })

        // insets kezelés, hogy ne takarja el a top bar az alkalmazást
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}