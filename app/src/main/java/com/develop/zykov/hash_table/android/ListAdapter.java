package com.develop.zykov.hash_table.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.develop.zykov.hash_table.R;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    public List<String> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewLarge;
        private final TextView textViewSmall;

        public ViewHolder(View v) {
            super(v);
            textViewLarge = (TextView) itemView.findViewById(R.id.textViewLarge);
            textViewSmall = (TextView) itemView.findViewById(R.id.textViewSmall);
        }

        public TextView getLargeText() {
            return textViewLarge;
        }
        public TextView getSmallText() {
            return textViewSmall;
        }
    }

    public ListAdapter(List<String> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getLargeText().setText(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}