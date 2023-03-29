package com.app.sushi.tei.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sushi.tei.R;
import com.app.sushi.tei.adapter.MyAccount.AccoundAdapter;

public class sampleActivity extends AppCompatActivity {

    RecyclerView recyclerView_address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_account_new);
        recyclerView_address = findViewById(R.id.recyclerView_address);
        AccoundAdapter accoundAdapter = new AccoundAdapter(this);
        recyclerView_address.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_address.setAdapter(accoundAdapter);


    }

}
