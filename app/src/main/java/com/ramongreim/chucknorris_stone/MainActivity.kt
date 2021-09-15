package com.example.chucknorris_stone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chucknorris_stone.ui.main.MainFragment
//cria o fragmento
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}