package id.co.endang.mymovie3.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.view.MovieFragment;
import id.co.endang.mymovie3.view.TVShowFragment;

public class MovieTabAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[]{R.string.tab_movie, R.string.tab_tvshow};
    private final Context mContext;

    public MovieTabAdapter(@NonNull FragmentManager fm, Context mContext) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MovieFragment();
        }
        return new TVShowFragment();
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
