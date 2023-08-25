package com.example.TecFresh.Shopkeeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TecFresh.Model.usersShopkeepers;
import com.example.TecFresh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link shopkeeperFragLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class shopkeeperFragLogin extends Fragment {

    private EditText inputPhoneShopkeeper,inputShopIdShopkeeper,inputPasswordShopkeeper;
    private String parentDbName = "Users";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public shopkeeperFragLogin() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment shopkeeperFragLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static shopkeeperFragLogin newInstance(String param1, String param2) {
        shopkeeperFragLogin fragment = new shopkeeperFragLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_shopkeeper_login, container, false);
    }



    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {




        super.onViewCreated(view, savedInstanceState);

        TextView forgotpass;
        forgotpass = view.findViewById(R.id.forgot_pass_shopkeeper);

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), shopkeeperVerify.class));
            }
        });

        Button login = view.findViewById(R.id.login_shopkeeper);

        inputPhoneShopkeeper = (EditText) view.findViewById(R.id.phone_shopkeeper);
        inputShopIdShopkeeper = (EditText) view.findViewById(R.id.shop_id);
        inputPasswordShopkeeper = (EditText) view.findViewById(R.id.password_shopkeeper);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginShopkeeper();
            }

            private void LoginShopkeeper() {

                String phone = inputPhoneShopkeeper.getText().toString();
                String shopID = inputShopIdShopkeeper.getText().toString();
                String password = inputPasswordShopkeeper.getText().toString();

                if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(getActivity(), "Please enter your phone number.", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(shopID))
                {
                    Toast.makeText(getActivity(), "Please provide your shop registration number.", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(getActivity(), "Please write your password.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(phone.equals("9876543219") && password.equals("rvitm") && shopID.equals(("12345"))){
                        String shopName = "RVITM";
                        Toast.makeText(getActivity(), "Logged in Successfully.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), shopkeeperMain.class);
                        intent.putExtra("Id",shopID);
                        intent.putExtra("shopName",shopName);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getActivity(), "Invalid Entry", Toast.LENGTH_LONG).show();

                }


            }

            private void AllowAccessToAccount(final String shopID, final String phone, final String password) {

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference("Shopkeepers");

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {


                        if (datasnapshot.child(parentDbName).child(shopID).exists()) {

                            usersShopkeepers usersData1 = datasnapshot.child(parentDbName).child(shopID).getValue(usersShopkeepers.class);


                            if (usersData1 != null && usersData1.getPhone().equals(phone)) {
                                if (usersData1.getShopID().equals(shopID)) {
                                    if (usersData1.getPassword().equals(password)) {
                                        Toast.makeText(getActivity(), "Logged in Successfully.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getActivity(), shopkeeperMain.class);
                                        intent.putExtra("Id",shopID);
                                        intent.putExtra("shopName",usersData1.getShopname());
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getActivity(), "Password is incorrect.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Error! Shop ID and Phone number don't match.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Sorry! Phone number " + " doesn't match with Shop-ID", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getActivity(),"Account with " + shopID + " does not exist",Toast.LENGTH_SHORT).show();
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
