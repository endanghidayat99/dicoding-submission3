package id.co.endang.mymovie3.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import id.co.endang.mymovie3.MainActivity;
import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.common.BaseAppCompatActivity;
import id.co.endang.mymovie3.service.ReminderReceiver;

public class SettingActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private static final String SWITCH_ON = "ON";
    private static final String SWITCH_OFF = "OFF";
    private Switch switchDaily;
    private Switch switchRelease;
    private RadioButton rbIndonesia;
    private ReminderReceiver reminderReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        RadioGroup rgLanguage = findViewById(R.id.rgLanguage);
        rbIndonesia = findViewById(R.id.rbIndonesia);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.setting);

        switchDaily = findViewById(R.id.switch_daily);
        switchRelease = findViewById(R.id.switch_release);
        switchDaily.setOnClickListener(this);
        switchRelease.setOnClickListener(this);

        reminderReceiver = new ReminderReceiver();
        initValueSetting();
        rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbIndonesia) {
                    savePrefences(PREF_LANGUAGE, ID_LANGUAGE, getSharedPreferences());
                    setLocale();
                } else if (i == R.id.rbEnglish) {
                    savePrefences(PREF_LANGUAGE, EN_LANGUAGE, getSharedPreferences());
                    setLocale();
                }
            }
        });
    }

    private void initValueSetting() {
        if (getLanguage().equals(ID_LANGUAGE))
            rbIndonesia.setChecked(true);

        switchDaily.setChecked(getReminderPref(ReminderReceiver.DAILY_REMINDER).equals(SWITCH_ON));
        switchRelease.setChecked(getReminderPref(ReminderReceiver.RELEASE_REMINDER).equals(SWITCH_ON));
    }

    public String getReminderPref(String type) {
        return getSharedPreferences().getString(type, SWITCH_OFF);
    }

    public void savePrefences(String prefName, String prefValue, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }

    private void setLocale() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_daily:
                if (switchDaily.isChecked()) {
                    reminderReceiver.setDailyReminder(this);
                    savePrefences(ReminderReceiver.DAILY_REMINDER, SWITCH_ON, getSharedPreferences());
                } else {
                    reminderReceiver.cancelAlarm(this, ReminderReceiver.DAILY_REMINDER);
                    savePrefences(ReminderReceiver.DAILY_REMINDER, SWITCH_OFF, getSharedPreferences());
                }
                break;
            case R.id.switch_release:
                if (switchRelease.isChecked()) {
                    reminderReceiver.setReleaseReminder(this);
                    savePrefences(ReminderReceiver.RELEASE_REMINDER, SWITCH_ON, getSharedPreferences());
                } else {
                    reminderReceiver.cancelAlarm(this, ReminderReceiver.RELEASE_REMINDER);
                    savePrefences(ReminderReceiver.RELEASE_REMINDER, SWITCH_OFF, getSharedPreferences());
                }
                break;
        }
    }
}
