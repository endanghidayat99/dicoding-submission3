package id.co.endang.moviexixfavorite.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.co.endang.moviexixfavorite.BuildConfig;
import id.co.endang.moviexixfavorite.R;
import id.co.endang.moviexixfavorite.model.MovieFavorite;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteHolder> {

    private ArrayList<MovieFavorite> listMovie = new ArrayList<>();

    public MovieFavoriteAdapter() {

    }

    public ArrayList<MovieFavorite> getListMovie() {
        return listMovie;
    }

    public void setData(ArrayList<MovieFavorite> data) {
        listMovie.clear();
        listMovie.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieFavoriteAdapter.MovieFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MovieFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavoriteHolder holder, int position) {
        MovieFavorite movie = listMovie.get(position);
        String image_url = BuildConfig.IMAGE_URL_BASE_PATH + movie.getPosterPath();
        Log.d(MovieFavorite.class.getSimpleName(), image_url);
        Glide.with(holder.itemView.getContext())
                .load(image_url)
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgPhoto);

        holder.tvName.setText(movie.getTitle());
        holder.tvDetail.setText(movie.getOverview());
        holder.tvRating.setText(String.valueOf(movie.getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MovieFavoriteHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;
        private TextView tvName, tvDetail, tvRating;

        public MovieFavoriteHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDetail = itemView.findViewById(R.id.tv_item_detail);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
        }
    }
}
