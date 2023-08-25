package com.example.TecFresh.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.TecFresh.Adapter.GridViewAdapter;
import com.example.TecFresh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class categorySpecific extends AppCompatActivity {

    private GridView grid;
    private TextView cText;
    private ArrayList<String> productName= new ArrayList<>();
    private ArrayList<String> productPrice= new ArrayList<>();
    private ArrayList<String> productImgURL= new ArrayList<>();
    private ArrayList<String> shopId= new ArrayList<>();
    private ArrayList<String> productId = new ArrayList<>();
    private GridViewAdapter adapter;
    private String category;
    private DatabaseReference Rootref;
    HashMap<String,String> map = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_gridview);

        grid = (GridView)findViewById(R.id.gridview);
        category = getIntent().getStringExtra("cName");
        cText = findViewById(R.id.category_text);
        cText.setText(category);

        Rootref = FirebaseDatabase.getInstance().getReference(category);
        LoadDataFromFirebase();
        adapter = new GridViewAdapter(productName,productPrice,productImgURL,getApplicationContext());
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),productCard.class);
                intent.putExtra("catg",category);
                intent.putExtra("pName",productName.get(i).toString());
                intent.putExtra("pPrice",productPrice.get(i).toString());
                intent.putExtra("pimg",productImgURL.get(i).toString());
                intent.putExtra("Id",map);
                startActivity(intent);
            }
        });
    }

    private void LoadDataFromFirebase() {
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot product : snapshot.getChildren()) {
                    map.put("shopId",product.getKey());
                    for(DataSnapshot pd : product.getChildren()) {
                        productName.add(pd.child("Product Name").getValue().toString());
                        productPrice.add(pd.child("Product Price").getValue().toString());
                        productImgURL.add(pd.child("IMG").getValue().toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}