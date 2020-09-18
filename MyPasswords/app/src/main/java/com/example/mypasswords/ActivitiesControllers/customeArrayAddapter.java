package com.example.mypasswords.ActivitiesControllers;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypasswords.Models.Site;
import com.example.mypasswords.R;

import java.util.ArrayList;

public class customeArrayAddapter<S> extends ArrayAdapter<Site> {


//        private static final String LOG_TAG = AndroidFlavorAdapter.class.getSimpleName();

        /**
         * This is our own custom constructor (it doesn't mirror a superclass constructor).
         * The context is used to inflate the layout file, and the list is the data we want
         * to populate into the lists.
         *
         * @param context        The current context. Used to inflate the layout file.
         * @param arr A List of AndroidFlavor objects to display in a list
         */
        public customeArrayAddapter(Activity context,int id, ArrayList<Site> arr) {
            // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
            // the second argument is used when the ArrayAdapter is populating a single TextView.
            // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
            // going to use this second argument, so it can be any value. Here, we used 0.
            super(context, id, arr);
        }

        /**
         * Provides a view for an AdapterView (ListView, GridView, etc.)
         *
         * @param position The position in the list of data that should be displayed in the
         *                 list item view.
         * @param convertView The recycled view to populate.
         * @param parent The parent ViewGroup that is used for inflation.
         * @return The View for the position in the AdapterView.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if(listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false);
            }

            // Get the {@link AndroidFlavor} object located at this position in the list
            Site currentSite = getItem(position);

            // Find the TextView in the list_item.xml layout with the ID version_name
            TextView siteName =  listItemView.findViewById(R.id.siteName);
            // Get the version name from the current AndroidFlavor object and
            // set this text on the name TextView
            siteName.setText(currentSite.getSiteName());

            TextView username =listItemView.findViewById(R.id.userName);
            // Get the version number from the current AndroidFlavor object and
            // set this text on the number TextView
            username.setText(currentSite.getUserName());


            // Find the TextView in the list_item.xml layout with the ID version_numbe

            TextView pass =listItemView.findViewById(R.id.pass);
            // Get the version number from the current AndroidFlavor object and
            // set this text on the number TextView
            pass.setText(currentSite.getPass());

            // Find the ImageView in the list_item.xml layout with the ID list_item_icon
            ImageView iconView = listItemView.findViewById(R.id.list_item_icon);



            if(currentSite.getImg()!=null){
                iconView.setImageBitmap(BitmapFactory.decodeByteArray( currentSite.getImg(), 0,  currentSite.getImg().length));
            }
            else{
                iconView.setImageResource(R.drawable.world);
            }

            return listItemView;
        }

    }

