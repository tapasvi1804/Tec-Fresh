package com.example.TecFresh.Shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.TecFresh.Model.usersShopkeepers;
import com.example.TecFresh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class shopkeeperVerify extends AppCompatActivity {

    private EditText phoneShopkeeper,shopIdShopkeeper;
    DatabaseReference Rootref;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopkeeper_verify);

        shopIdShopkeeper = (EditText)findViewById(R.id.reg_shopNo_shopkeeper);
        phoneShopkeeper = (EditText)findViewById(R.id.reg_phone_shopkeeper);
        final Button verify = (Button)findViewById(R.id.verify_shopkeeper);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyInput();
            }

            private void VerifyInput() {
                String shopNo = shopIdShopkeeper.getText().toString();
                String phone = phoneShopkeeper.getText().toString();

                if(TextUtils.isEmpty(shopNo)){
                    Toast.makeText(shopkeeperVerify.this,"Please enter 5-digit registered shop no",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(phone)){
                    Toast.makeText(shopkeeperVerify.this,"Please enter registered mobile number",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(shopkeeperVerify.this,"Please wait",Toast.LENGTH_SHORT).show();
                    VerifyData(shopNo,phone);
                }
            }

            private void VerifyData(final String shopNo, final String phone) {

                Rootref = FirebaseDatabase.getInstance().getReference("Shopkeepers");

                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(parentDbName).child(shopNo).exists()){
                            usersShopkeepers usersdata = snapshot.child(parentDbName).child(shopNo).getValue(usersShopkeepers.class);

                            assert usersdata != null;
                            if(usersdata.getShopID().equals(shopNo)){
                                if(usersdata.getPhone().equals(phone)){
                                    Intent intent = new Intent(getApplicationContext(),shopkeeperResetPassword.class);
                                    intent.putExtra("EnteredShopNo",shopNo);

                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(shopkeeperVerify.this,"Sorry! Phone No and ShopId don't match",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        else {
                            Toast.makeText(shopkeeperVerify.this,"Shop with Id " + shopNo + " doesn't exist.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}
