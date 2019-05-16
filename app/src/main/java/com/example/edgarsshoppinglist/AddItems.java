package com.example.edgarsshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddItems extends AppCompatActivity {
    private EditText item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        item = findViewById(R.id.editTextNewItem);
    }

    public void newItem(View view){
        String itemToAdd = item.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("Item", itemToAdd);
        setResult(RESULT_OK, intent);
        finish();
    }
}
