package id.co.endang.mymovie3.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import id.co.endang.mymovie3.BuildConfig;
import id.co.endang.mymovie3.FavoriteMovieWidget;
import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.db.model.MovieFavorite;
import id.co.endang.mymovie3.db.provider.MovieContentProvider;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private Cursor cursor;
    private int widgetAppId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        this.widgetAppId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        Log.d(this.getClass().getSimpleName(), "onCreate:");
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(MovieContentProvider.MOVIE_URI, null, null, null, null, null);
        Log.d("onDataSetChanged: ", "JML " + cursor.getCount());
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        MovieFavorite movieFavorite = getItem(position);
        Bitmap bitmap = null;
        String image_url = BuildConfig.IMAGE_URL_BASE_PATH + movieFavorite.getPosterPath();
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(image_url)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);
        Bundle extras = new Bundle();
        extras.putInt(FavoriteMovieWidget.EXTRA_ITEM, widgetAppId);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public MovieFavorite getItem(int position) {
        if (!cursor.moveToPosition(position))
            throw new IllegalArgumentException("Position invalid");
        return new MovieFavorite(cursor);
    }
}
