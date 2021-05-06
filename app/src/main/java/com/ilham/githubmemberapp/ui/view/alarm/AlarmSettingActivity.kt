package com.ilham.githubmemberapp.ui.view.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.ilham.githubmemberapp.AlarmReceiver
import com.ilham.githubmemberapp.R
import com.ilham.githubmemberapp.databinding.ActivityAlarmSettingBinding
import com.ilham.githubmemberapp.utils.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*


class AlarmSettingActivity : AppCompatActivity(), View.OnClickListener,
    TimePickerFragment.DialogTimeListener {

    private var binding: ActivityAlarmSettingBinding? = null
    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmSettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.apply {
            title = getString(R.string.github_repeat_alarm)
            setBackgroundDrawable(getDrawable(R.drawable.base_background))
        }

        binding?.tvRepeatingTime?.setOnClickListener(this)
        binding?.setRepeatingAlarm?.setOnClickListener(this)
        binding?.setCancelAlarm?.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return false
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        // Siapkan time formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_REPEAT_TAG -> binding?.tvRepeatingTime?.text =
                dateFormat.format(calendar.time)
            else -> {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_repeating_time -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }
            R.id.set_repeating_alarm -> {
                val repeatTime = binding?.tvRepeatingTime?.text.toString()
                val repeatMessage = binding?.edtRepeatingMessage?.text.toString()
                binding?.edtRepeatingMessage?.setText("")
                alarmReceiver.setRepeatAlarm(
                    this,
                    AlarmReceiver.REPEATING_ALARM, repeatTime, repeatMessage
                )
            }
            R.id.set_cancel_alarm -> {
                alarmReceiver.cancelAlarm(context = this)
            }

        }
    }


}