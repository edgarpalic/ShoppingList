package com.example.shoppinglist;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {
    private ArrayList<ListItem> arrayList;
    private LayoutInflater mInflater;

    public ListAdapter(Context context, ArrayList addListItem){
        mInflater = LayoutInflater.from(context);
        this.arrayList = addListItem;
    }

    @Override
    public ListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
        View mItemView = mInflater.inflate(R.layout.view_item, parent, false);
        return new ItemViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ItemViewHolder holder, int position){
        String mCurrent = arrayList.get(position).item;
        holder.listItem.setText(mCurrent);
    }

    @Override
    public int getItemCount(){
        return arrayList.size();
    }

    public void removeItem(int position){
        arrayList.remove(position);
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView listItem;
        public boolean isChecked;

        final ListAdapter mAdapter;

        public ItemViewHolder(View itemView, ListAdapter adapter){
            super(itemView);
            listItem = itemView.findViewById(R.id.viewItem);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (!isChecked) {
                listItem.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                isChecked = true;
            } else {
                listItem.setPaintFlags(0);
                isChecked = false;
            }
        }
    }
}
