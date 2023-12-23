package com.mycode.pagingapp.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.mycode.pagingapp.apisection.APIClient;
import com.mycode.pagingapp.model.Movie;
import com.mycode.pagingapp.model.MovieResponse;

import java.util.List;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePagingSource extends RxPagingSource<Integer, Movie> {
    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer,Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        Single<LoadResult<Integer, Movie>> result;
        try {
            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
            result = APIClient.getApiInterface()
                    .getMoviesByPage(page)
                    .subscribeOn(Schedulers.io())
                    .map(MovieResponse::getMovies)
                    .map(movies -> toLoadResult(movies, page))
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e) {
            result = Single.just(new LoadResult.Error(e));

        }
        return result;
    }
    private LoadResult<Integer,Movie> toLoadResult(List<Movie> movies,int page){
        return new LoadResult.Page(movies,page==1?null:page-1,page+1);


    }
}
