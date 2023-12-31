package com.mycode.pagingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mycode.pagingapp.R;
import com.mycode.pagingapp.databinding.LoadStateItemBinding;

public class MovieLoadStateAdapter extends LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder> {

    private View.OnClickListener retryCallBack;
    public MovieLoadStateAdapter(View.OnClickListener retryCallBack){
        this.retryCallBack = retryCallBack;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(parent,retryCallBack);
    }


    public static class LoadStateViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        private TextView errorMsg;
        private Button retry;

        public LoadStateViewHolder(@NonNull ViewGroup parent ,@NonNull View.OnClickListener retryCallBack) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_state_item,parent,false));

            LoadStateItemBinding binding =  LoadStateItemBinding.bind(itemView);
            progressBar = binding.progressBar;
            errorMsg = binding.errorMsg;
            retry = binding.retryButton;
            retry.setOnClickListener(retryCallBack);

        }
        public void bind(LoadState loadState){
            if (loadState instanceof LoadState.Error){
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                errorMsg.setText(loadStateError.getError().getLocalizedMessage());
            }
            progressBar.setVisibility(
            loadState instanceof  LoadState.Loading ? View.VISIBLE:View.GONE);

            retry.setVisibility(
                    loadState instanceof  LoadState.Error ? View.VISIBLE:View.GONE);
            errorMsg.setVisibility(
                    loadState instanceof  LoadState.Error? View.VISIBLE:View.GONE);
        }
    }





}