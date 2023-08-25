package com.example.TecFresh.Shopkeeper;

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

public class shopkeeperResetPassword extends AppCompatActivity {

    private EditText shopkeeperPass1,shopkeeperPass2;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopkeeper_reset);

        shopkeeperPass1 = findViewById(R.id.input_new_pass);
        shopkeeperPass2 = findViewById(R.id.confirm_pass);
        final Button reset = (Button)findViewById(R.id.reset_btn_shopkeeper);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyInput();
            }

            private void verifyInput() {
                String pass1 = shopkeeperPass1.getText().toString();
                String pass2 = shopkeeperPass2.getText().toString();

                if(TextUtils.isEmpty(pass1)){
                    Toast.makeText(shopkeeperResetPassword.this,"Enter new password",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pass2)){
                    Toast.makeText(shopkeeperResetPassword.this,"Confirm password",Toast.LENGTH_SHORT).show();
                }
                else{
                    ChangePassword(pass1,pass2);
                }
            }

            private void ChangePassword(String pass1, String pass2) {
                final String shopNo = getIntent().getStringExtra("EnteredShopNo");
                final DatabaseReference Rootref;
                Rootref = FirebaseDatabase.getInstance().getReference("Shopkeepers");

                if(!pass1.equals(pass2)){
                    Toast.makeText(shopkeeperResetPassword.this,"Password entries don't match",Toast.LENGTH_SHORT).show();
                }
                else{
                    Rootref.child(parentDbName).child(shopNo).child("password").setValue(pass1);
                    Toast.makeText(shopkeeperResetPassword.this,"Password changed successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),shopkeeperMain.class));
                }

            }
        });

    }
}
