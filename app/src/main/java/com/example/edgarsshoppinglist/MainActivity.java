package com.example.edgarsshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ListItem> arrayList;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            arrayList = savedInstanceState.getParcelableArrayList("Array");
        }else{
            arrayList = new ArrayList<>();
            load();
            //arrayList.clear();
            //save();
        }
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        recycleSetup();
        remove();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = findViewById(R.id.button1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddItems.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putParcelableArrayList("Array", arrayList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            String item = data.getStringExtra("Item");
            arrayList.add(new ListItem(item, false));
            recycleSetup();
            mRecyclerView.getAdapter().notifyItemInserted(arrayList.size());
            save();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    private void save(){
        String filename = "SaveData.json";
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            Writer writer = new OutputStreamWriter(outputStream);
            Gson gson = new Gson();
            gson.toJson(arrayList, writer);
            writer.close();
        }catch (Exception e){
            Log.e("Can´t save data", "", e);
        }
    }

    private void load(){
        String filename = "SaveData.json";
        FileInputStream inputStream;
        try{
            inputStream = openFileInput(filename);
            Reader reader = new BufferedReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<ListItem>>(){}.getType();
            arrayList = gson.fromJson(reader, collectionType);
            reader.close();
            Log.d("Data laddat:", "" + arrayList);
        }catch (Exception e){
            Log.e("Can´t load data", "", e);
        }
    }

    private void remove(){
        SwipeRemoval swipeRemoval = new SwipeRemoval() {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                mAdapter.removeItem(position);
                save();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeRemoval);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void recycleSetup(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new ListAdapter(this, arrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
