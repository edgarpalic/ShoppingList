package com.example.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

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
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        recycleSetup();
        swipeToDelete();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddedListItem.class);
                startActivityForResult(intent, 1);
            }
        });

        if(savedInstanceState != null){
            arrayList = savedInstanceState.getParcelableArrayList("Array");
        }else{
            arrayList = new ArrayList<>();
            loadData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Array", arrayList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,  resultCode, data);
        String item = data.getStringExtra("Item");
        arrayList.add(new ListItem(item));
        recycleSetup();
        mRecyclerView.getAdapter().notifyItemInserted(arrayList.size());
        saveData();
    }

    public void saveData(){
        String filename = "SaveData.json";
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            Writer writer = new OutputStreamWriter(outputStream);
            Gson gson = new Gson();
            gson.toJson(arrayList, writer);
            writer.close();
        }catch (Exception e){
            Log.e("Failure to save!", "", e);
        }
    }

    public void loadData(){
        String filename = "SaveData.json";
        FileInputStream inputStream;
        try{
            inputStream = openFileInput(filename);
            Reader reader = new BufferedReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<AddedListItem>>(){}.getType();
            arrayList = gson.fromJson(reader, collectionType);
            reader.close();
        }catch (Exception e){
            Log.d("Loading failed!", "", e);
        }
    }

    private void swipeToDelete(){
        SwipeDelete swipeDelete = new SwipeDelete(this){
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
                final int position = viewHolder.getAdapterPosition();
                mAdapter.removeItem(position);
                saveData();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeDelete);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void recycleSetup(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new ListAdapter(this, arrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
