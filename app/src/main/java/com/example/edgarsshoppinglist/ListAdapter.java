package com.example.edgarsshoppinglist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.itemViewHolder> {
    private final ArrayList<ListItem> arrayList;
    private final LayoutInflater mInflater;

    ListAdapter(Context context, ArrayList<ListItem> shoppingItemList){
        mInflater = LayoutInflater.from(context);
        this.arrayList = shoppingItemList;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.shoppinlist_item, parent, false);
        return new itemViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        String mCurrent = arrayList.get(position).item;
        Boolean mIsChecked = arrayList.get(position).isChecked;
        holder.shoppingItem.setText(mCurrent);
        if(mIsChecked){
            holder.shoppingItem.setBackgroundColor(Color.parseColor("#B83E09"));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    void removeItem(int position){
        arrayList.remove(position);
        notifyDataSetChanged();
    }

    class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView shoppingItem;
        private boolean isChecked;

        private itemViewHolder(View itemView) {
            super(itemView);
            shoppingItem = itemView.findViewById(R.id.textViewItem);
            itemView.setOnClickListener(this);
        }

        //Clicking on an array item activates our isChecked boolean.
        @Override
        public void onClick(View v) {

            int mPosition = getLayoutPosition();
            if(!isChecked){
                shoppingItem.setBackgroundColor(Color.parseColor("#B83E09"));
                arrayList.get(mPosition).isChecked = true;
                notifyDataSetChanged();
                isChecked = true;
            }else{
                shoppingItem.setBackgroundColor(Color.parseColor("#8BC34A"));
                arrayList.get(mPosition).isChecked = false;
                notifyDataSetChanged();
                isChecked = false;
            }
        }
    }
}
