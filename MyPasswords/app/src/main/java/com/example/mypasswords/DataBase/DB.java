package com.example.mypasswords.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mypasswords.Models.Site;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

    public DB(@Nullable Context context) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table IF NOT EXISTS mySites (id INTEGER PRIMARY KEY,siteName VARCHAR(255) UNIQUE NOT NULL,userName  VARCHAR(255)  NOT NULL,password  VARCHAR(255)  NOT NULL,siteImage BLOB )");
        db.execSQL("Create Table IF NOT EXISTS currentTheme (cuTheme VARCHAR(255) NOT NULL)");
        db.execSQL("INSERT INTO currentTheme VALUES ('light')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mySites");
        db.execSQL("DROP TABLE IF EXISTS currentTheme");
        onCreate(db);
    }


    //Add New Site
    public long addNewSite(Site newSite) {
        try {
            long check = searchMethod(newSite.getSiteName(), 0);
            if (check == -100) {
                return -100;
            }
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues myValues = new ContentValues();
            myValues.put("siteName", newSite.getSiteName());
            myValues.put("userName", newSite.getUserName());
            myValues.put("password", newSite.getPass());
            myValues.put("siteImage", newSite.getImg());
            long res = db.insert("mySites", null, myValues);
            return res;
        } catch (Exception ex) {
            Log.e("DB -> addNewSite", "" + ex);
        }
        return 0;
    }

    //    Update Site
    public long updateSite(Site site) {
        try {
            long check = searchMethod(site.getSiteName(), 1);
            if (check == -100) {
                return -100;
            }
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues myValues = new ContentValues();
            myValues.put("siteName", site.getSiteName());
            myValues.put("userName", site.getUserName());
            myValues.put("password", site.getPass());
            myValues.put("siteImage", site.getImg());
            long res = db.update("mySites", myValues, "id=" + site.getId() + "", null);
            return res;
        } catch (Exception ex) {
            Log.e("DB -> addNewSite", "" + ex);
        }
        return 0;
    }


    public ArrayList<Site> getAllSites() {
        ArrayList<Site> mySites = new ArrayList<Site>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM mySites", null);
        while (res.moveToNext()) {
            mySites.add(new Site(res.getInt(res.getColumnIndex("id")), res.getString(res.getColumnIndex("siteName")), res.getString(res.getColumnIndex("userName")), res.getString(res.getColumnIndex("password")), res.getBlob(res.getColumnIndex("siteImage"))));
        }
        return mySites;
    }

    public long searchMethod(String siteName, int numOfExists) {
        ArrayList<Site> mySites = this.getAllSites();
        int num = 0;
        for (int i = 0; i < mySites.size(); i++) {
            if (mySites.get(i).getSiteName().equalsIgnoreCase(siteName)) {
                num++;
            }
        }
        if (num > numOfExists) {
            return -100;
        }
        return 1;
    }

    public long deleteSite(int siteId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM mySites WHERE id=" + siteId);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public String getCurrentTheme() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM currentTheme", null);
        while (res.moveToNext()) {
            return res.getString(res.getColumnIndex("cuTheme"));
        }
        return "light";

    }

    public void changeTheme(String newTheme) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues myValues = new ContentValues();
        myValues.put("cuTheme", newTheme);
        long res = db.update("currentTheme", myValues, "", null);
    }

}


