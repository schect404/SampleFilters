package com.example.samplearchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.samplearchitecture.presentation.filters.FiltersFragmentDialog
import com.example.samplearchitecture.presentation.main.MatchesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MatchesFragment.newInstance())
                .commit()
        }
    }
}
