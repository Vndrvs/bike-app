package gde.bike.mobileapp

import android.os.Bundle
import gde.bike.mobileapp.databinding.DetailScreenBinding

class DetailActivity: androidx.appcompat.app.AppCompatActivity() {

    lateinit var binding: DetailScreenBinding
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.buildDb(this)

        val bikeId = intent.getLongExtra(EXTRA_ID, -1L)

        val bike = db.bikeDao().getBikeById(bikeId)

        binding.brand.text = bike?.brand
        binding.model.text = bike?.model
        binding.price.text = bike?.price.toString()
        binding.category.text = bike?.category
        binding.description.text = bike?.description
        binding.isAvailable.text = bike?.availability

    }
    companion object {
        const val EXTRA_TEXT = "text"
        const val EXTRA_ID = "id"
    }
}
