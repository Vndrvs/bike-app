package gde.bike.mobileapp

import android.os.Bundle
import gde.bike.mobileapp.databinding.DetailScreenBinding

class DetailActivity: androidx.appcompat.app.AppCompatActivity() {

    // viewbinding a detail layouthoz
    lateinit var binding: DetailScreenBinding
    // adatbázis instance, itt is szükséges a kontextus miatt
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Room database létrehozása
        db = AppDatabase.buildDb(this)

        // előző képernyőről kapott bicikli azonosító (kell default érték)
        val bikeId = intent.getLongExtra(EXTRA_ID, -1L)
        // adott bicikli lekérése az adatbázisból
        val bike = db.bikeDao().getBikeById(bikeId)

        // sikeres lekérés esetén kitöltjük a UI-t a releváns adatokkal
        binding.brand.text = bike?.brand
        binding.model.text = bike?.model
        binding.price.text = bike?.price.toString()
        binding.category.text = bike?.category
        binding.description.text = bike?.description
        binding.isAvailable.text = bike?.availability
    }
    // companion object magyarázat: https://www.baeldung.com/kotlin/companion-object
    companion object {
        // intent kulcs az az ID átadáshoz
        const val EXTRA_ID = "id"
    }
}
