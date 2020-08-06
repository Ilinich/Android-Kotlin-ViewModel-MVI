package com.begoml.androidmvi.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.begoml.androidmvi.R
import com.begoml.androidmvi.view.news.NewsFragment

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, NewsFragment())
                .commit()
        }
    }
}