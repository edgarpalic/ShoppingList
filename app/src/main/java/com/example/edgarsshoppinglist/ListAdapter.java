package com.example.edgarsshoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ShoppingItemViewHolder> {
    private final ArrayList<ListItem> arrayList;
    private final LayoutInflater mInflater;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    ListAdapter(Context context, ArrayList<ListItem> shoppingItemList){
        mInflater = LayoutInflater.from(context);
        this.arrayList = shoppingItemList;
    }

    @NonNull
    @Override
    public ListAdapter.ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.shoppinlist_item, parent, false);
        return new ShoppingItemViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ShoppingItemViewHolder holder, int position) {
        String mCurrent = arrayList.get(position).item;
        holder.shoppingItem.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    void removeItem(int position){
        arrayList.remove(position);
        notifyDataSetChanged();
    }

    class ShoppingItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView shoppingItem;
        private boolean isChecked;

        private ShoppingItemViewHolder(View itemView) {
            super(itemView);
            shoppingItem = itemView.findViewById(R.id.textViewItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(!isChecked){
                shoppingItem.setBackgroundColor(Color.parseColor("#B83E09"));
                isChecked = true;
            }else{
                shoppingItem.setBackgroundColor(Color.parseColor("#8BC34A"));
                isChecked = false;
            }

        }
    }
}
