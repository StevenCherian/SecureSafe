package com.example.cmsc413project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyView> implements Filterable {
    Context context;
    ArrayList<LoginCredentials> loginCredentialsArrayList;
    ArrayList<LoginCredentials> backupCredentialsArraylist;
    UserPreferencesManager manager;

    public Adapter(Context context, ArrayList<LoginCredentials> loginCredentialsArrayList) {
        this.context = context;
        this.loginCredentialsArrayList = loginCredentialsArrayList;
        this.backupCredentialsArraylist = loginCredentialsArrayList;
        manager = new UserPreferencesManager(context);
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

        Animation buttonPressAnimation = AnimationUtils.loadAnimation(this.context, R.anim.button_press);

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonPressAnimation);
                buttonPressAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        deleteItemAt(holder.getAdapterPosition());
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonPressAnimation);
                buttonPressAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        editItemAt(holder.getAdapterPosition());
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return loginCredentialsArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    loginCredentialsArrayList = backupCredentialsArraylist;
                } else {
                    ArrayList<LoginCredentials> filteredList = new ArrayList<>();
                    for (LoginCredentials row : backupCredentialsArraylist) {

                        // search for account titles that match search query
                        if (row.appName.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    loginCredentialsArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = loginCredentialsArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
               loginCredentialsArrayList = (ArrayList<LoginCredentials>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }


    public static class MyView extends RecyclerView.ViewHolder {
        TextView appHeading;
        TextView emailHeading;
        TextView pwdHeading;
        Button removeButton;
        Button editButton;

        public MyView(@NonNull View itemView) {
            super(itemView);
            appHeading = itemView.findViewById(R.id.appHeading);
            emailHeading = itemView.findViewById(R.id.emailHeading);
            pwdHeading = itemView.findViewById(R.id.pwdHeading);
            removeButton = itemView.findViewById(R.id.removeButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }


    public void editItemAt(int adapterPos) {
        int credentialsID = loginCredentialsArrayList.get(adapterPos).id;
        Intent edit = new Intent(context, EditCredentialsActivity.class);
        edit.putExtra("credentialsID", credentialsID);
        context.startActivity(edit);
    }


    public void deleteItemAt(int adapterPos) {
        int credentialsID = loginCredentialsArrayList.get(adapterPos).id;
        manager.removeLoginCredentialsByID(credentialsID);
        Adapter.this.notifyItemRemoved(adapterPos);
        loginCredentialsArrayList = manager.getLoginCredentials();
        backupCredentialsArraylist = loginCredentialsArrayList;
    }
}
