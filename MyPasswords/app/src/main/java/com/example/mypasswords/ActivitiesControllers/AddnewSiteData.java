package com.example.mypasswords.ActivitiesControllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mypasswords.DataBase.DB;
import com.example.mypasswords.Models.Site;
import com.example.mypasswords.R;
import com.example.mypasswords.Threads.DBThread;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.IOException;


public class AddnewSiteData extends AppCompatActivity {

    final int GET_FROM_GALLERY = 1;
    byte[] imageBytes;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                bitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                imageBytes=MainActivity.getBitmapAsByteArray(bitmap);

                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.world);
                Toast.makeText(getApplicationContext(),"Error ,Cannot Load Image",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.world);
                Toast.makeText(getApplicationContext(),"Error ,Cannot Load Image",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.currentTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_site_data);
//        Start Upload Image
        FloatingActionButton galaryBtn=(FloatingActionButton) findViewById(R.id.button);
        galaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

            }
        });
//        ====== ======= ========   =======
            final AddnewSiteData superThis=(AddnewSiteData)this;
            final Button addButon=(Button) findViewById(R.id.addButton);
            final Button remove=(Button) findViewById(R.id.deleteBtn);

            if(MainActivity.tempSite==null){
                remove.setVisibility(View.GONE);
            }else{
            superThis.setTitle(R.string.addNewEditSiteLabel);
            remove.setVisibility(View.VISIBLE);
            addButon.setText(R.string.editButtonText);
            EditText siteName=((EditText)findViewById(R.id.siteName));
            siteName.setText(MainActivity.tempSite.getSiteName());

            EditText userName=((EditText)findViewById(R.id.userName));
            userName.setText(MainActivity.tempSite.getUserName());

            EditText pass=((EditText)findViewById(R.id.password));
            pass.setText(MainActivity.tempSite.getPass());
            if( MainActivity.tempSite.getImg()!=null){
                imageBytes=MainActivity.tempSite.getImg();
                        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(BitmapFactory.decodeByteArray( MainActivity.tempSite.getImg(), 0,  MainActivity.tempSite.getImg().length));
            }
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder  builder=new AlertDialog.Builder(superThis);
                    builder.setMessage("Do you want to Delete this site ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                    DB db=new DB(superThis);
                                    long result=db.deleteSite(MainActivity.tempSite.getId());
                                    if(result>0){
                                        Toast.makeText(getApplicationContext(),"Site Deleted Successfully",Toast.LENGTH_SHORT).show();
                                        superThis.finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Uncatched Error!",Toast.LENGTH_SHORT).show();}
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();

                                }
                            });
                    AlertDialog alert=builder.create();
                    alert.setTitle("Confirmation");
                    alert.show();


                }

            });
        }
        addButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String siteName=((EditText)findViewById(R.id.siteName)).getText().toString().trim();
                String userName=((EditText)findViewById(R.id.userName)).getText().toString().trim();
                String pass=((EditText)findViewById(R.id.password)).getText().toString().trim();
//                Toast.makeText(getApplicationContext(),"Studytonight is Best",Toast.LENGTH_LONG).show();
                if(siteName.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Please Type Site Name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userName.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Please Type User Name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Please Type Password ",Toast.LENGTH_SHORT).show();
                    return;
                }
                DB db=new DB(superThis);
//                ==== imageBytes ====
                Site s=new Site(0,siteName,userName,pass,imageBytes);
                String action=addButon.getText().toString();
                DBThread mm=new DBThread(s,action,superThis);
                mm.start();
                long result=0;
                while (result==0){
                    result=DBThread.result;
                }
                if(action.equalsIgnoreCase("add")){
                    if(result>0){
                        Toast.makeText(getApplicationContext(),"Site Added Successfully",Toast.LENGTH_SHORT).show();
                        superThis.finish();
                    }
                    else if(result==-100){
                        Toast.makeText(getApplicationContext(),"This Site Name is Already Exist !",Toast.LENGTH_SHORT).show();}

                    else{
                        Toast.makeText(getApplicationContext(),"Uncatched Error!",Toast.LENGTH_SHORT).show();}}
                else{
                    if(result>0){
                        Toast.makeText(getApplicationContext(),"Site Updated Successfully",Toast.LENGTH_SHORT).show();
                        superThis.finish();
                    }
                    else if(result==-100){
                        Toast.makeText(getApplicationContext(),"This Site Name is Already Exist !",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Uncatched Error!",Toast.LENGTH_SHORT).show();}
                }
            }

        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        Button addBtn= findViewById(R.id.addButton);
        addBtn.requestFocus();

    }



}
