package com.example.cmsc413project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyView> {
    Context context;
    ArrayList<LoginCredentials> loginCredentialsArrayList;

    public Adapter(Context context, ArrayList<LoginCredentials> loginCredentialsArrayList) {
        this.context = context;
        this.loginCredentialsArrayList = loginCredentialsArrayList;
    }

    @NonNull
    @Override
    public Adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.password_list, parent, false);
        return new MyView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyView holder, int position) {
        LoginCredentials loginCredentials = loginCredentialsArrayList.get(position);
        holder.appHeading.setText(loginCredentials.appName);
        holder.emailHeading.setText(loginCredentials.email);
        holder.pwdHeading.setText(loginCredentials.password);
    }

    @Override
    public int getItemCount() {
        return loginCredentialsArrayList.size();
    }

    public static class MyView extends RecyclerView.ViewHolder {
        TextView appHeading;
        TextView emailHeading;
        TextView pwdHeading;

        public MyView(@NonNull View itemView) {
            super(itemView);
            appHeading = itemView.findViewById(R.id.appHeading);
            emailHeading = itemView.findViewById(R.id.emailHeading);
            pwdHeading = itemView.findViewById(R.id.pwdHeading);
        }
    }
}
