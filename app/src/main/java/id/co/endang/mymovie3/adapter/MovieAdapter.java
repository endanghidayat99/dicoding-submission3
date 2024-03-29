package id.co.endang.mymovie3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import id.co.endang.mymovie3.BuildConfig;
import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private ArrayList<Movie> movies;

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void refresh(ArrayList<Movie> movies) {
        this.movies = new ArrayList<>();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView tvName, tvDetail, tvRating;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDetail = itemView.findViewById(R.id.tv_item_detail);
            tvRating = itemView.findViewById(R.id.tv_rating);
        }

        public void bind(final Movie movie) {
            if (StringUtils.isNotBlank(movie.getPosterPath())) {
                String image_url = BuildConfig.IMAGE_URL_BASE_PATH + movie.getPosterPath();
                Glide.with(this.itemView.getContext())
                        .load(image_url)
                        .apply(new RequestOptions().override(65, 65))
                        .into(this.imgPoster);
            }
            this.tvName.setText(StringUtils.isNotBlank(movie.getTitle()) ? movie.getTitle() : movie.getName());
            this.tvDetail.setText(movie.getOverview());
            this.tvRating.setText(String.valueOf(movie.getVoteAverage()));
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallBack.onItemClicked(movie);
                }
            });

        }
    }
    public interface OnItemClickCallBack {
        void onItemClicked(Movie data);
    }

    private OnItemClickCallBack onItemClickCallBack;

    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack;
    }
}
