package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddedListItem extends AppCompatActivity {
    EditText item;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);
        item = findViewById(R.id.editTextItem);
    }

    public void newItem(View view){
        String itemAdd = item.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("Item", itemAdd);
        setResult(RESULT_OK, intent);
        finish();
    }
}
