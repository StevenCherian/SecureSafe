package com.example.cmsc413project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyView> implements Filterable {
    Context context;
    ArrayList<LoginCredentials> loginCredentialsArrayList;
    ArrayList<LoginCredentials> backupCredentialsArraylist;
    UserPreferencesManager manager;
    boolean isPressed = false;

    /*
     * Constructor for the adapter. Gives adapter login credentials and context. Backup credentials arraylist is used for search
     */
    public Adapter(Context context, ArrayList<LoginCredentials> loginCredentialsArrayList) {
        this.context = context;
        this.loginCredentialsArrayList = loginCredentialsArrayList;
        this.backupCredentialsArraylist = loginCredentialsArrayList;
        manager = new UserPreferencesManager(context);
    }

    //Inflates recycler view with our own created list xml. Gives the layout of how each credential will look.
    @NonNull
    @Override
    public Adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.password_list, parent, false);

        return new MyView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyView holder, int position) {
        //Fills each list item with login credentials
        LoginCredentials loginCredentials = loginCredentialsArrayList.get(position);
        holder.appHeading.setText(loginCredentials.appName);
        holder.emailHeading.setText(loginCredentials.email);
        holder.pwdHeading.setText(loginCredentials.password);

        Animation buttonPressAnimation = AnimationUtils.loadAnimation(this.context, R.anim.button_press);

        holder.viewPasswordButton.setOnClickListener(view -> {
            view.startAnimation(buttonPressAnimation);
            buttonPressAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(!isPressed) {
                        holder.viewPasswordButton.setBackgroundResource(R.drawable.ic_filled_24);
                        isPressed = true;
                        viewPasswordAt(holder.getAdapterPosition());

                    } else {
                        holder.viewPasswordButton.setBackgroundResource(R.drawable.ic_outline_24);
                        isPressed = false;
                        hidePasswordAt(holder.getAdapterPosition());
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        });

        holder.moreButton.setOnClickListener(view -> {
            view.startAnimation(buttonPressAnimation);
            PopupMenu popupMenu = new PopupMenu(this.context, view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_dropdown, popupMenu.getMenu());

            buttonPressAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.item_edit:
                                    editItemAt(holder.getAdapterPosition());
                                    return true;
                                case R.id.item_delete:
                                    deleteItemAt(holder.getAdapterPosition());
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        });

        //On click listener for the edit button. Calls the edit item method.
        holder.editButton.setOnClickListener(view -> {
            view.startAnimation(buttonPressAnimation);
            buttonPressAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                //Once animation ends, the method is called to edit selected credentials.
                @Override
                public void onAnimationEnd(Animation animation) {
                    editItemAt(holder.getAdapterPosition());
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        });
    }

    //Gets total size of login credentials arraylist
    @Override
    public int getItemCount() {
        return loginCredentialsArrayList.size();
    }

    /*
     * This is the method used for the search functionality.
     * This is from a website we used. Link is given in documentation.
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                /*
                 * If no characters are entered in search input, it sets the credentials arraylist
                 * to the backup credentials arraylist which is used to show the searched credentials
                 */
                if (charString.isEmpty()) {
                    loginCredentialsArrayList = backupCredentialsArraylist;
                } else {
                    ArrayList<LoginCredentials> filteredList = new ArrayList<>();
                    for (LoginCredentials row : backupCredentialsArraylist) {

                        // search for account titles that match search query
                        if (row.appName.toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row);
                    }

                    loginCredentialsArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = loginCredentialsArrayList;
                return filterResults;
            }

            //Updates the search results shown once a character is entered in search box
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
               loginCredentialsArrayList = (ArrayList<LoginCredentials>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    //Gets each item from the passcode list xml in order for it to hold credentials data
    public static class MyView extends RecyclerView.ViewHolder {
        TextView appHeading;
        TextView emailHeading;
        TextView pwdHeading;
        Button editButton;
        Button viewPasswordButton;
        Button moreButton;

        //Constructor for the MyView object.
        public MyView(@NonNull View itemView) {
            super(itemView);
            appHeading = itemView.findViewById(R.id.appHeading);
            emailHeading = itemView.findViewById(R.id.emailHeading);
            pwdHeading = itemView.findViewById(R.id.pwdHeading);
            editButton = itemView.findViewById(R.id.editButton);
            viewPasswordButton = itemView.findViewById(R.id.viewPasswordButton);
            moreButton = itemView.findViewById(R.id.moreButton);
        }
    }

    public void viewPasswordAt(int adapterPos) {
        int credentialsID = loginCredentialsArrayList.get(adapterPos).id;

    }

    public void hidePasswordAt(int adapterPos) {
        int credentialsID = loginCredentialsArrayList.get(adapterPos).id;

    }

    //Gives functionality to the edit button. Passes it the ID of the credential that will be edited.
    public void editItemAt(int adapterPos) {
        //Gets ID of credentials from credentials arraylist
        int credentialsID = loginCredentialsArrayList.get(adapterPos).id;
        //Creates an intent to pass the ID to the edit credentials activity so data can be filled
        Intent edit = new Intent(context, EditCredentialsActivity.class);
        edit.putExtra("credentialsID", credentialsID);
        context.startActivity(edit);
    }

    //Gives functionality to the delete button. Passes it the ID of the credential that will be deleted.
    public void deleteItemAt(int adapterPos) {
        //Gets ID of credentials from credentials arraylist
        int credentialsID = loginCredentialsArrayList.get(adapterPos).id;
        //Calls the remove function in user preferences class with ID as parameter to correctly remove specified credential
        manager.removeLoginCredentialsByID(credentialsID);
        //Notifies adapter that credentials have been removed and refreshes the list shown
        Adapter.this.notifyItemRemoved(adapterPos);
        loginCredentialsArrayList = manager.getLoginCredentials();
        backupCredentialsArraylist = loginCredentialsArrayList;
    }
}