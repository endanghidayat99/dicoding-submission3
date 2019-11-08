package id.co.endang.mymovie3;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import id.co.endang.mymovie3.common.BaseAppCompatActivity;
import id.co.endang.mymovie3.view.FavoriteActivity;
import id.co.endang.mymovie3.view.MainFragment;
import id.co.endang.mymovie3.view.SearchActivity;
import id.co.endang.mymovie3.view.SettingActivity;

public class MainActivity extends BaseAppCompatActivity {

    private Fragment contentPage = new MainFragment();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Movie XIX");
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containter, contentPage).commit();
        } else {
            contentPage = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            getSupportFragmentManager().beginTransaction().replace(R.id.containter, contentPage).commit();
        }
        setLanguage();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (searchView != null) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
        }
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, contentPage);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    MainFragment mainFragment = (MainFragment) contentPage;
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra(SearchActivity.SEARCH_TEXT, query);
                    intent.putExtra(SearchActivity.SEARCH_TYPE, mainFragment.getViewPager().getCurrentItem());
                    startActivity(intent);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_setting)
            showSetting();
        else if (item.getItemId() == R.id.action_fav_activity)
            showFavorite();
        else if (item.getItemId() == R.id.search) {
            System.out.println("ADADDDDDDDDD");
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFavorite() {
        Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
        startActivity(intent);
    }

    private void showSetting() {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void setLanguage() {
        Locale locale = new Locale(getLanguage());
        Configuration configuration = this.getResources().getConfiguration();
        configuration.locale = locale;
        this.getResources().updateConfiguration(configuration,
                this.getResources().getDisplayMetrics());
    }
}