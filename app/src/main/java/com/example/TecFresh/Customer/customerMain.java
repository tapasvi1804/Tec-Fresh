package com.example.TecFresh.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.TecFresh.Adapter.CategoryAdapter;
import com.example.TecFresh.Customer.categorySpecific;
import com.example.TecFresh.Model.modelCategory;
import com.example.TecFresh.R;

import java.util.ArrayList;
import java.util.List;

public class customerMain extends AppCompatActivity {

    private Toolbar toolbar;
    private CategoryAdapter adapter;
    private List<modelCategory> exampleList;
    private CategoryAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_main);

        toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        fillExampleList();
        setUpRecyclerView();
    }

    private void fillExampleList() {
        exampleList = new ArrayList<>();
        exampleList.add(new modelCategory(R.drawable.grocery, "Grocery"));
        exampleList.add(new modelCategory(R.drawable.dairy, "Dairy"));
        exampleList.add(new modelCategory(R.drawable.vegetables, "Vegetables"));
        exampleList.add(new modelCategory(R.drawable.beverages, "Beverages"));
        exampleList.add(new modelCategory(R.drawable.fruits, "Fruits"));
        exampleList.add(new modelCategory(R.drawable.personalcare, "Personal Care"));
        exampleList.add(new modelCategory(R.drawable.bakery, "Bakery"));
        exampleList.add(new modelCategory(R.drawable.healthcare, "Healthcare"));
    }

    private void setUpRecyclerView() {
        setOnClickListener();
        RecyclerView recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new CategoryAdapter(exampleList,listener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new CategoryAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent myIntent = new Intent(getApplicationContext(), categorySpecific.class);
                myIntent.putExtra("cName",exampleList.get(position).getText1());
                startActivity(myIntent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_customer, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.account_info){
            Toast.makeText(getApplicationContext(),"Account info",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.placed_orders){
            Toast.makeText(getApplicationContext(),"Orders",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.help){
            Toast.makeText(getApplicationContext(),"Contact us",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.search){
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(),"Cart",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
