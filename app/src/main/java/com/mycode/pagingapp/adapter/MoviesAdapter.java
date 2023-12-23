package com.mycode.pagingapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.mycode.pagingapp.databinding.SingleMovieItemBinding;
import com.mycode.pagingapp.model.Movie;
import com.mycode.pagingapp.viewmodel.MovieViewModel;

import org.jetbrains.annotations.Nullable;

import kotlin.coroutines.CoroutineContext;

public class MoviesAdapter extends PagingDataAdapter<Movie,MoviesAdapter.MovieViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int MOVIE_ITEM = 1;
    RequestManager glide;

    public MoviesAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback, RequestManager glide) {
        super(diffCallback);
        this.glide = glide;
    }

    @NonNull
    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(SingleMovieItemBinding.inflate
                (LayoutInflater.from(parent.getContext())
                        ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MovieViewHolder holder, int position) {
        Movie currentMovie = getItem(position);
        if (currentMovie !=null){
            glide.load("https://image.tmdb.org/t/p/w500"+currentMovie.getPosterPath())
                    .into(holder.movieItemBinding.imageView);
            holder.movieItemBinding.textViewRating.setText(String.valueOf(currentMovie.getVoteAverage()));
           // holder.movieItemBinding.textViewOverView.setText(String.valueOf(currentMovie.getOverView()));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount()? MOVIE_ITEM:LOADING_ITEM;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        SingleMovieItemBinding movieItemBinding;
        public MovieViewHolder(@Nullable SingleMovieItemBinding movieItemBinding ){
            super(movieItemBinding.getRoot());
            this.movieItemBinding=movieItemBinding;
        }

    }

}
