package id.co.endang.mymovie3.common;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {

    private int position;
    private OnItemClickCallback onItemClickCallback;
    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback){
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view,position);
    }
}
