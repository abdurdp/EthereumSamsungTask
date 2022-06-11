package com.abdur.ethereumsamsungtask.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdur.ethereumsamsungtask.data.home.ResultItem;
import com.abdur.ethereumsamsungtask.databinding.ItemTransactionLayoutBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context context;
    protected List<ResultItem> mDataSet;


    public TransactionAdapter(List<ResultItem> dataSet) {
        this.mDataSet = dataSet;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemTransactionLayoutBinding itemBinding =
                ItemTransactionLayoutBinding.inflate(layoutInflater, parent, false);
        return new ItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ResultItem resultItem = mDataSet.get(position);
        ItemViewHolder itemViewHolder =
                (ItemViewHolder) viewHolder;

        itemViewHolder.binding.tvTimeStamp.setText(getDate(Long.parseLong(resultItem.getTimeStamp())));
        itemViewHolder.binding.tvfrom.setText(resultItem.getFrom());
        itemViewHolder.binding.tvTo.setText(resultItem.getTo());
        itemViewHolder.binding.tvNonce.setText(resultItem.getNonce());
        itemViewHolder.binding.tvValue.setText(resultItem.getValue());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @SuppressLint("NonConstantResourceId")
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemTransactionLayoutBinding binding;

        public ItemViewHolder(ItemTransactionLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    private String getDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale.US);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
