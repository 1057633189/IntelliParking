package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Search extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = (EditText) findViewById(R.id.searchedittext);

        Button button = (Button) findViewById(R.id.searchbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, ParkingLot.class);
                intent.putExtra("place", editText.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

}