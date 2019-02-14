package com.example.test_hencoder_plus_07.test_hencoder_plus_07;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewTest01 extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview_01);

        rv = findViewById(R.id.rv);
    }
}
