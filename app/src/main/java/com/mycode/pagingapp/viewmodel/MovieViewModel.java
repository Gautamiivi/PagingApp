package com.mycode.pagingapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.mycode.pagingapp.model.Movie;
import com.mycode.pagingapp.paging.MoviePagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class MovieViewModel extends ViewModel {
    public Flowable<PagingData<Movie>> moviePagingDataFlowable;

    public MovieViewModel() {
        init();
    }
    private void init() {

        //defining the page source
        MoviePagingSource moviePagingSource = new MoviePagingSource();
        Pager<Integer, Movie> pager = new Pager(
                new PagingConfig(20,
                        20,
                        false,
                        20,
                        20 * 499),
                () -> moviePagingDataFlowable);

        // flowable
        moviePagingDataFlowable = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(moviePagingDataFlowable, coroutineScope);

    }
}
