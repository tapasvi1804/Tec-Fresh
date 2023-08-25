package com.example.TecFresh.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.TecFresh.R;

import java.util.ArrayList;


public class GridViewAdapter extends BaseAdapter {

    private ArrayList<String> productName;
    private ArrayList<String> productPrice;
    private ArrayList<String> productImgURL;
    private Context context;
    private LayoutInflater layoutInflater;

    public GridViewAdapter(ArrayList<String> productName, ArrayList<String> productPrice, ArrayList<String> productImgURL, Context context) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImgURL = productImgURL;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return this.productImgURL.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = layoutInflater.inflate(R.layout.example_grid_item,viewGroup,false);
            ImageView imageProd = (ImageView) view.findViewById(R.id.product_image);
            TextView nameProd = (TextView) view.findViewById(R.id.product_name);
            TextView priceProd = (TextView) view.findViewById(R.id.product_price);

            nameProd.setText(productName.get(position));
            priceProd.setText(productPrice.get(position));
            Glide.with(context).load(productImgURL.get(position)).into(imageProd);
        }

        return view;
    }
}