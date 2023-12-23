package com.mycode.pagingapp.activity;
import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.bumptech.glide.RequestManager;
import com.mycode.pagingapp.adapter.MovieLoadStateAdapter;
import com.mycode.pagingapp.adapter.MoviesAdapter;
import com.mycode.pagingapp.databinding.ActivityMainBinding;
import com.mycode.pagingapp.model.Movie;
import com.mycode.pagingapp.util.GridSpace;
import com.mycode.pagingapp.util.MovieComparator;
import com.mycode.pagingapp.util.Utils;
import com.mycode.pagingapp.viewmodel.MovieViewModel;
import org.reactivestreams.Subscription;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    MovieViewModel mainActivityViewModel;

    ActivityMainBinding binding;
    MoviesAdapter moviesAdapter;
    @Inject
    RequestManager requestManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (Utils.API_KEY == null || Utils.API_KEY.isEmpty()){
            Toast.makeText(this, "Error in the API key", Toast.LENGTH_SHORT).show();

        }
        moviesAdapter = new MoviesAdapter(new MovieComparator(),requestManager);
        mainActivityViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        //subscribe to paging data
        mainActivityViewModel.moviePagingDataFlowable.subscribe(new FlowableSubscriber<PagingData<Movie>>() {
            @Override
            public void onSubscribe(@NonNull Subscription s) {

            }

            @Override
            public void onNext(PagingData<Movie> moviePagingData) {
                moviesAdapter.submitData(getLifecycle(),moviePagingData);

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        initRecyclerViewAndAdapter();

    }


    private  void  initRecyclerViewAndAdapter(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        binding.recyclerViewMovies.setLayoutManager(gridLayoutManager);


        binding.recyclerViewMovies.addItemDecoration(new GridSpace(2,20,true));
        binding.recyclerViewMovies.setAdapter(
                moviesAdapter.withLoadStateFooter(
                        new MovieLoadStateAdapter(view -> {
                            moviesAdapter.retry();
                        })
                )
        );
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                return moviesAdapter.getItemViewType(position)==MoviesAdapter.LOADING_ITEM?1:2;
            }
        });



    }
}