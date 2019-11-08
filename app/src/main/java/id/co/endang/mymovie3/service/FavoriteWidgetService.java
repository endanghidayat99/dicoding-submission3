package id.co.endang.mymovie3.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoriteWidgetService extends RemoteViewsService {
    public FavoriteWidgetService() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }

}
