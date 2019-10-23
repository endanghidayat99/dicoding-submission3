package id.co.endang.mymovie3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import id.co.endang.mymovie3.common.BaseAppCompatActivity;
import id.co.endang.mymovie3.view.LocalizationActivity;
import id.co.endang.mymovie3.view.MainFragment;

public class MainActivity extends BaseAppCompatActivity {

    private Fragment contentPage = new MainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = this.getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Movie XIX");
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containter, contentPage).commit();
        } else {
            contentPage = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            getSupportFragmentManager().beginTransaction().replace(R.id.containter, contentPage).commit();
        }
        setLanguage(sharedPreferences);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, contentPage);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_setting)
            showSetting();
        return super.onOptionsItemSelected(item);
    }

    private void showSetting() {
        Intent intent = new Intent(MainActivity.this, LocalizationActivity.class);
        startActivity(intent);
    }

    public void setLanguage(SharedPreferences sharedPreferences) {
        Locale locale = new Locale(getLanguage(sharedPreferences));
        Configuration configuration = this.getResources().getConfiguration();
        configuration.locale = locale;
        this.getResources().updateConfiguration(configuration,
                this.getResources().getDisplayMetrics());
    }
}