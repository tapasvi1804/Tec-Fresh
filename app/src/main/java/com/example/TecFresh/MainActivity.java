package com.example.TecFresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.example.TecFresh.Others.userTabLogin;
import com.example.TecFresh.Others.userTabRegister;

public class MainActivity extends AppCompatActivity {

    private Button joinNowBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            joinNowBtn = (Button) findViewById(R.id.join_now);
            loginBtn = (Button) findViewById(R.id.login_main);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent = new Intent(MainActivity.this, userTabLogin.class);

                startActivity(loginIntent);
            }
        });

        joinNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(MainActivity.this, userTabRegister.class);

                startActivity(registerIntent);
            }
        });



    }
}
