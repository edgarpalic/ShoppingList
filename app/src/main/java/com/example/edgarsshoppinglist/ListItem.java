package com.example.edgarsshoppinglist;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {
    final String item;
    public Boolean isChecked;

    //Our item has a name and a UI selection boolean so that you can check items on the list instead of removing them with the left swipe.
    ListItem(String item, Boolean isChecked){
        this.item = item;
        this.isChecked = isChecked;
    }

    private ListItem(Parcel in) {
        item = in.readString();
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item);
    }
}
