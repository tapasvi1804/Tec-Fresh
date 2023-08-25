package com.example.TecFresh.Shopkeeper;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.TecFresh.Model.imageUploader;
import com.example.TecFresh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class addProduct extends AppCompatActivity {

    private Spinner spinner;
    private ImageView uploadIMG;
    private TextView productName;
    private TextView productId;
    private TextView productPrice;
    private TextView productDiscount;
    private Button addBtn;
    private Uri imageUri;
    private String text;
    private int sFlag = 0;
    private int iFlag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        spinner = findViewById(R.id.spinner_categories);
        uploadIMG = findViewById(R.id.upload_pic);
        addBtn = findViewById(R.id.add_product_btn);
        productName = findViewById(R.id.set_product_name);
        productId = findViewById(R.id.set_product_id);
        productPrice = findViewById(R.id.set_product_price);
        productDiscount = findViewById(R.id.set_product_discount);

        List<String> categories = new ArrayList<>();
        categories.add(0,"Choose category");
        categories.add("Grocery");
        categories.add("Dairy");
        categories.add("Vegetables");
        categories.add("Beverages");
        categories.add("Fruits");
        categories.add("Personal Care");
        categories.add("Bakery");
        categories.add("Healthcare");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text = adapterView.getItemAtPosition(i).toString();
                if(text.equals("Choose category")){
                    sFlag = 1;
                }
                else {
                    sFlag = 0;
                    Toast.makeText(getApplicationContext(),"Selected : "+text,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final StorageReference reference = FirebaseStorage.getInstance().getReference("Images");

        uploadIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyInput();
            }

            private void VerifyInput() {

                String pName = productName.getText().toString();
                String pId = productId.getText().toString();
                String pPrice = productPrice.getText().toString();
                String pDisc =  productDiscount.getText().toString();

                if(sFlag == 1){
                    Toast.makeText(getApplicationContext(),"Please choose a category first",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pName)){
                    Toast.makeText(getApplicationContext(),"Product name can't be empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pId)){
                    Toast.makeText(getApplicationContext(),"Product Id can't be empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pPrice)){
                    Toast.makeText(getApplicationContext(),"Price can't be empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pDisc)){
                    Toast.makeText(getApplicationContext(),"Please set a discount value(Type 0 if discount not applicable)",Toast.LENGTH_SHORT).show();
                }
                else if(iFlag == 0){
                    Toast.makeText(getApplicationContext(),"Please upload an image of the product",Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadImage(imageUri,pName,pId,pPrice,pDisc);
                    Toast.makeText(getApplicationContext(),"Please hold on while we do something.",Toast.LENGTH_SHORT).show();
                }
            }

            private void uploadImage(Uri imageUri, final String productName, final String productId, final String productPrice, final String productDisc) {

                final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {
                                imageUploader model = new imageUploader(uri.toString());
                                uploadData(model,productName,productId,productPrice,productDisc);
                                Toast.makeText(getApplicationContext(),"Image uploaded successfully",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Uploading failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void uploadData(final imageUploader model, final String productName, final String productId,final String productPrice, final String productDisc) {
                final DatabaseReference Rootref = FirebaseDatabase.getInstance().getReference(text);
                final String shopId = getIntent().getStringExtra("sID");
                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        assert shopId != null;
                        if(!(snapshot.child(shopId).child(productId).exists())){
                            HashMap<String, Object> userdataMap = new HashMap<>();
                            userdataMap.put("Product Id",productId);
                            userdataMap.put("Product Name",productName);
                            userdataMap.put("Product Price",productPrice);
                            userdataMap.put("Product Discount",productDisc);
                            userdataMap.put("IMG",model.getImageUrl());
                            userdataMap.put("Shop Name",getIntent().getStringExtra("sName"));

                            Rootref.child(shopId).child(productId).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Product added successfully",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Product wasn't added",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Product with ID "+productId+" already exists.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private String getFileExtension(Uri mUri) {

                ContentResolver cr = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                return mime.getExtensionFromMimeType(cr.getType(mUri));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            iFlag = 1;
            imageUri = data.getData();
            uploadIMG.setImageURI(imageUri);

        }
    }
}