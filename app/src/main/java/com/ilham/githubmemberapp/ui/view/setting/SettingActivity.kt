package com.ilham.githubmemberapp.ui.view.setting

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import com.ilham.githubmemberapp.R
import com.ilham.githubmemberapp.databinding.ActivitySettingBinding
import com.ilham.githubmemberapp.ui.view.alarm.AlarmSettingActivity

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.setting)
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(getDrawable(R.drawable.base_action_bar_background))
        }
        binding.languageSetting.setOnClickListener(clickListener)
        binding.alarmSetting.setOnClickListener(clickListener)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                super.onBackPressed()
                return true
            }
        }
        return false
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.language_setting -> {
                val setting = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(setting)
            }
            R.id.alarm_setting->{
                val alarm = Intent(this,AlarmSettingActivity::class.java)
                startActivity(alarm)
            }
        }
    }
}