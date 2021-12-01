package com.example.cmsc413project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyView> {
    Context context;
    ArrayList<Passcodes> passcodesArrayList;

    public Adapter(Context context, ArrayList<Passcodes> passcodesArrayList) {
        this.context = context;
        this.passcodesArrayList = passcodesArrayList;
    }

    @NonNull
    @Override
    public Adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.password_list, parent, false);
        return new MyView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyView holder, int position) {

        Passcodes passcodes = passcodesArrayList.get(position);
        holder.appHeading.setText(passcodes.appName);
        holder.appImage.setImageResource(passcodes.appImage);
        holder.pwdHeading.setText(passcodes.password);
    }

    @Override
    public int getItemCount() {
        return passcodesArrayList.size();
    }

    public static class MyView extends RecyclerView.ViewHolder{
        TextView appHeading;
        ShapeableImageView appImage;
        TextView pwdHeading;


        public MyView(@NonNull View itemView) {
            super(itemView);
            appHeading = itemView.findViewById(R.id.appHeading);
            appImage = itemView.findViewById(R.id.title_image);
            pwdHeading = itemView.findViewById(R.id.pwdHeading);
        }
    }
}
