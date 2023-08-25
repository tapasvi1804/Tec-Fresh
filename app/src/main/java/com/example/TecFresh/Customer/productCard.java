package com.example.TecFresh.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.TecFresh.MainActivity;
import com.example.TecFresh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Objects;

public class productCard extends AppCompatActivity {
    private String imUrl,cat;
    private String[] id;
    private ImageView prodImg;
    private TextView prodPrice,prodName,shopName,prodId,shopId,quantity;
    private DatabaseReference Rootref;
    //private String id;
    private Button buy,cart,plus,minus;
    boolean buyState = false,cartState = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_card);

        cat = getIntent().getStringExtra("catg");
        imUrl = getIntent().getStringExtra("pimg");
        HashMap<String,String> hashMap = (HashMap<String, String>)getIntent().getSerializableExtra("Id");


        assert hashMap != null;
        Rootref = FirebaseDatabase.getInstance().getReference(cat).child(Objects.requireNonNull(hashMap.get("shopId")));
        prodImg = (ImageView) findViewById(R.id.prod_card_img);

        prodName = (TextView) findViewById(R.id.prod_card_name);
        prodPrice = (TextView) findViewById(R.id.prod_card_price);
        shopName = (TextView) findViewById(R.id.prod_shop_name);
        prodId = (TextView) findViewById(R.id.prod_id);
        shopId = (TextView) findViewById(R.id.shop_id);

        buy = (Button) findViewById(R.id.prod_card_buy);
        cart = (Button) findViewById(R.id.prod_card_cart);

        SearchFirebase();

        Glide.with(getApplicationContext()).load(imUrl).into(prodImg);
        //prodName.setText(name);
        //prodPrice.setText(price);

        plus = (Button) findViewById(R.id.prod_plus);
        minus = (Button) findViewById(R.id.prod_minus);
        quantity = (TextView) findViewById(R.id.prod_card_quantity);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                if(!buyState){
                    buyState = true;
                    //Intent intent = new Intent(getApplicationContext(), cartActivity.class);
                    //startActivity(intent);
                }
                else{
                    buyState = false;
                    Toast.makeText(getApplicationContext(),"Already clicked buy",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),prodName.getText() +","+prodPrice.getText(),Toast.LENGTH_SHORT).show();
                if(!cartState){
                    cartState = true;
                    Toast.makeText(getApplicationContext(),quantity.getText()+" items Added to cart",Toast.LENGTH_SHORT).show();
                    cart.setText("Continue shopping");
                    buy.setText("Move to my bag");
                }
                else{
                    cartState = false;
                    Intent intent = new Intent(getApplicationContext(), customerMain.class);
                    startActivity(intent);
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt = Integer.parseInt(quantity.getText().toString());
                if(qt == 9){
                    Toast.makeText(getApplicationContext(),"Sorry! Operation can't be performed",Toast.LENGTH_SHORT).show();
                }
                else{
                    qt = qt+1;
                    quantity.setText(String.valueOf(qt));
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt = Integer.parseInt(quantity.getText().toString());
                if(qt == 1){
                    Toast.makeText(getApplicationContext(),"Sorry! Operation can't be performed",Toast.LENGTH_SHORT).show();
                }
                else{
                    qt = qt-1;
                    quantity.setText(String.valueOf(qt));
                }
            }
        });

    }

    private void SearchFirebase() {

        Rootref.orderByChild("IMG").equalTo(imUrl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot product : snapshot.getChildren()){
                        //price = product.child("Product Name").getValue().toString();
                        //name = product.child("Product Price").getValue().toString();
                        prodName.setText(Objects.requireNonNull(product.child("Product Name").getValue()).toString());
                        prodPrice.setText(Objects.requireNonNull(product.child("Product Price").getValue()).toString());
                        shopName.setText(Objects.requireNonNull(product.child("Shop Name").getValue()).toString());
                        prodId.setText(Objects.requireNonNull(product.child("Product Id").getValue()).toString());
                        shopId.setText(Objects.requireNonNull(product.child("Product Discount").getValue()).toString());
                        //id = product.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
