package com.example.TecFresh.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.TecFresh.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class customerResetPassword extends AppCompatActivity {

    private String parentDbName = "Users";
    private EditText customerPass1,customerPass2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reset);

        customerPass1 = findViewById(R.id.input_new_pass);
        customerPass2 = findViewById(R.id.confirm_pass);
        final Button reset = (Button)findViewById(R.id.reset_btn_customer);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyInput();
            }

            private void VerifyInput() {
                String pass1 = customerPass1.getText().toString();
                String pass2 = customerPass2.getText().toString();

                if(TextUtils.isEmpty(pass1)){
                    Toast.makeText(customerResetPassword.this,"Enter new password",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pass2)){
                    Toast.makeText(customerResetPassword.this,"Confirm password",Toast.LENGTH_SHORT).show();
                }
                else{
                    ChangePassword(pass1,pass2);
                }
            }

            private void ChangePassword(final String pass1,final String pass2) {
                final String phoneNo = getIntent().getStringExtra("EnteredNo");
                final DatabaseReference Rootref;
                Rootref = FirebaseDatabase.getInstance().getReference("Customers");

                if(!pass1.equals(pass2)){
                    Toast.makeText(customerResetPassword.this,"Password entries don't match!",Toast.LENGTH_SHORT).show();
                }
                else{
                    assert phoneNo != null;
                    Rootref.child(parentDbName).child(phoneNo).child("password").setValue(pass1);
                    Toast.makeText(customerResetPassword.this,"Password changed successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), customerMain.class));
                }
            }
        });

    }



}
