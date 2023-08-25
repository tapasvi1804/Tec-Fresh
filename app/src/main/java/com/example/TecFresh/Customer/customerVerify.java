package com.example.TecFresh.Customer;

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


import com.example.TecFresh.Model.usersCustomers;
import com.example.TecFresh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class customerVerify extends AppCompatActivity {

    private EditText phoneCustomer;
    DatabaseReference Rootref;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_verify);

        phoneCustomer = (EditText)findViewById(R.id.reg_phone_customer);
        final Button verifyPhone = (Button)findViewById(R.id.generate_OTP_customer);

        verifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyInput();
            }

            private void VerifyInput() {
                String phone = phoneCustomer.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(customerVerify.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(customerVerify.this,"Please wait ",Toast.LENGTH_SHORT).show();
                    VerifyPhone(phone);
                }
            }


            private void VerifyPhone(final String phone) {
                Rootref = FirebaseDatabase.getInstance().getReference("Customers");

                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(parentDbName).child(phone).exists()){
                            usersCustomers usersData = snapshot.child(parentDbName).child(phone).getValue(usersCustomers.class);

                            assert usersData != null;
                            if (usersData.getPhone().equals(phone)) {
                                Intent intent = new Intent(getApplicationContext(), customerResetPassword.class);
                                intent.putExtra("EnteredNo",phone);

                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(customerVerify.this,"Sorry! No account with " + phone + " exists.",Toast.LENGTH_SHORT).show();
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
