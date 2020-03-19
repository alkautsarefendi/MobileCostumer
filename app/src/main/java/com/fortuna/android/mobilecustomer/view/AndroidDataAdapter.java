package com.fortuna.android.mobilecustomer.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fortuna.android.mobilecustomer.R;

import java.util.ArrayList;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class AndroidDataAdapter extends RecyclerView.Adapter<AndroidDataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> arrayList;
    private Context mcontext;

    public AndroidDataAdapter(Context context, ArrayList<AndroidVersion> android) {
        this.arrayList = android;
        this.mcontext = context;
    }

    @Override
    public void onBindViewHolder(AndroidDataAdapter.ViewHolder holder, int i) {

        holder.imageView.setImageResource(arrayList.get(i).getrecyclerViewImage());
        holder.tv1.setText(arrayList.get(i).getrecyclerViewTitleText());
        holder.tv2.setText(arrayList.get(i).getRecyclerViewSubTitleText());

    }

    @Override
    public AndroidDataAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_item_menu, vGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1, tv2;
        private ImageView imageView;

        public ViewHolder(View v) {
            super(v);

            imageView = v.findViewById(R.id.list_avatar);
            tv1 = v.findViewById(R.id.list_title);
            tv2 = v.findViewById(R.id.list_desc);

        }
    }
}

