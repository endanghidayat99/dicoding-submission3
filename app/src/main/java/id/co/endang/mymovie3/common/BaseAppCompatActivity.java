package id.co.endang.mymovie3.common;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;

public class BaseAppCompatActivity extends AppCompatActivity {
    public static final String KEY_FRAGMENT = "fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(this);
    }

    public String getLanguage(SharedPreferences sharedPreferences) {
        return sharedPreferences.getString("language", "en");
    }
}
