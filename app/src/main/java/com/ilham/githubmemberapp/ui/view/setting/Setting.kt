package com.ilham.githubmemberapp.ui.view.setting

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.ilham.githubmemberapp.R
import com.ilham.githubmemberapp.databinding.ActivitySettingBinding

class Setting : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.base_action_bar_background))
        binding.languageSetting.setOnClickListener(clickListener)
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.language_setting -> {
                val setting = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(setting)
            }
        }
    }
}