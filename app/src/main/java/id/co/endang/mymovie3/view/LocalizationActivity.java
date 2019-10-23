package id.co.endang.mymovie3.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import id.co.endang.mymovie3.MainActivity;
import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.common.BaseAppCompatActivity;

public class LocalizationActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization);
        final SharedPreferences sharedPreferences = getSharedPreferences("LANGUAGE", MODE_PRIVATE);
        RadioGroup rgLanguage = findViewById(R.id.rgLanguage);
        RadioButton rbIndonesia = findViewById(R.id.rbIndonesia);

        getSupportActionBar().setTitle(R.string.change_language);

        if (getLanguage(sharedPreferences).equals("id"))
            rbIndonesia.setChecked(true);

        rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbIndonesia) {
                    saveLanguage("id", sharedPreferences);
                    setLocale();
                } else if (i == R.id.rbEnglish) {
                    saveLanguage("en", sharedPreferences);
                    setLocale();
                }
            }
        });
    }

    public void saveLanguage(String language, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language);
        editor.apply();
    }

    private void setLocale() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
