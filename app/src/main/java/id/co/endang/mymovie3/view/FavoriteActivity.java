package id.co.endang.mymovie3.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.common.BaseAppCompatActivity;

public class FavoriteActivity extends BaseAppCompatActivity {

    private Fragment contentPage = new FavoriteFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        final Toolbar toolbar = findViewById(R.id.toolbar_fav);
        toolbar.setTitle(R.string.favorite);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containter, contentPage).commit();
        } else {
            contentPage = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            getSupportFragmentManager().beginTransaction().replace(R.id.containter, contentPage).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, contentPage);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
