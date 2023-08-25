package com.example.TecFresh.Customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.TecFresh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class customerFragRegister extends Fragment {

    private EditText customerName, customerEmail, customerPhone, customerPassword;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public customerFragRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment customerfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static customerFragRegister newInstance(String param1, String param2) {
        customerFragRegister fragment = new customerFragRegister();
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
        return inflater.inflate(R.layout.frag_customer_register, container, false);
    }


    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Button createAccount = view.findViewById(R.id.register_customer);

        customerName = view.findViewById(R.id.set_name_customer);
        customerEmail = view.findViewById(R.id.set_email_customer);
        customerPhone = view.findViewById(R.id.set_phone_customer);
        customerPassword = view.findViewById(R.id.set_password_customer);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

    }

    private void CreateAccount() {
        String name = customerName.getText().toString();
        String email = customerEmail.getText().toString();
        String phone = customerPhone.getText().toString();
        String pass = customerPassword.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "Please write a name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(), "Please provide an email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(getActivity(), "Please enter a phone number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(getActivity(), "Please give a password", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(getActivity(), "Please hang in there for a while.", Toast.LENGTH_LONG).show();
            ValidatephoneNumber(name, email, phone, pass);
        }

    }

    private void ValidatephoneNumber(final String name, final String email, final String phone, final String pass) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference("Customers");

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("email", email);
                    userdataMap.put("password", pass);
                    userdataMap.put("name", name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getActivity(), "Your account has been created successfully !", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getActivity(), customerMain.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), "Something went wrong, please try again later.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(getActivity(), phone + " already exists.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
