package com.rossi.customclasses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static final String TABLE_CONTENT = "MAKERTABLE";
	private static final String DATABASENAME = "PinMarkerDB";
	private static final int 	DATABASE_VERSION = 1;
	private static final String KEY_MARKER_ID = "key_marker_id";
	private static final String KEY_COORCENTER_X = "key_center_x";
	private static final String KEY_COORCENTER_Y = "key_center_y";
	private static final String KEY_MARKER_X = "key_marker_x";
	private static final String KEY_MARKER_Y = "key_marker_y";
	private static final String KEY_MAP_SCALE = "key_map_scale";
	private static final String KEY_CREATAT_DATE	= "key_create_at";
			
	public DatabaseHandler(Context context) {
		super(context, DATABASENAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTENT + "("
                + KEY_MARKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_COORCENTER_X +" REAL, " + KEY_COORCENTER_Y + " REAL, "
                + KEY_MARKER_X+ " REAL, " + KEY_MARKER_Y+" REAL, " + KEY_MAP_SCALE +" REAL, " + KEY_CREATAT_DATE + " TEXT NOT NULL" + " )";
		
		
        db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENT);
 
        // Create tables again
        onCreate(db);
	}
	
	
	 /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new Download file information.
    public void addContact(CustomPinPoint content_info) {
    	
    	String _nowDateStr = content_info.getCreateAtDate();
    	
    	if (_nowDateStr == null)
    	{
        	SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        	dateformatter.setTimeZone(TimeZone.getDefault());
        	Date _nowDate = new Date();
        	
        	_nowDateStr = dateformatter.format(_nowDate);
    	}

    	
    	
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
       
        values.put(KEY_COORCENTER_X, content_info.getInitCenterPos().x); //FILE Subscription ID.
        values.put(KEY_COORCENTER_Y, content_info.getInitCenterPos().y); //FILE Download path
        values.put(KEY_MARKER_X, content_info.getTouchX());//File Title.
        values.put(KEY_MARKER_Y, content_info.getTouchY()); // CONTENT FILELINK.
        values.put(KEY_MAP_SCALE, content_info.getZoomOut()); // CONTENT FILE Description.
        values.put(KEY_CREATAT_DATE, _nowDateStr);
        
        
        Log.d("DB insert value : ", values.getAsFloat(KEY_COORCENTER_X) + ":" + values.getAsFloat(KEY_COORCENTER_Y)+":"+values.getAsFloat(KEY_MARKER_X)+":"+values.getAsFloat(KEY_MARKER_Y)+":"+values.getAsFloat(KEY_MAP_SCALE));
        // Inserting Row
        db.insert(TABLE_CONTENT, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
   @SuppressWarnings("null")
   public CustomPinPoint getContentListItemInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTENT, new String[] {KEY_COORCENTER_X, KEY_COORCENTER_Y, KEY_MARKER_X, KEY_MARKER_Y, KEY_MAP_SCALE,  KEY_CREATAT_DATE}, KEY_MARKER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null){
        	cursor.moveToFirst();
        	return null;
        }else{
            CustomPinPoint contact = new CustomPinPoint();
            
            contact.setCustomPinState( id , Float.parseFloat(cursor.getString(2)), Float.parseFloat(cursor.getString(3)), Float.parseFloat(cursor.getString(0)), Float.parseFloat(cursor.getString(1)), Float.parseFloat(cursor.getString(4)), cursor.getString(5));

            return contact;        	
        }

    }
    
 // Getting All Contacts
    public List<CustomPinPoint> getAllContacts() {
        List<CustomPinPoint> contentList = new ArrayList<CustomPinPoint>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTENT;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomPinPoint contentListItemInfo = new CustomPinPoint();
                
                contentListItemInfo.setDBId(Integer.parseInt(cursor.getString(0)));
                Float _center_x = Float.parseFloat(cursor.getString(1));
                Float _center_y = Float.parseFloat(cursor.getString(2));
                PointF centerPos = new PointF(_center_x, _center_y);
                contentListItemInfo.setCenterPosition(centerPos);
                contentListItemInfo.setTouchPinPoint(Float.parseFloat(cursor.getString(3)), Float.parseFloat(cursor.getString(4)));
                contentListItemInfo.setZoomOut(Float.parseFloat(cursor.getString(5)));
                contentListItemInfo.setCreateAtDate(cursor.getString(6));
                
                // Adding contact to list
                contentList.add(contentListItemInfo);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contentList;
    }
    
    
    // Updating single contact
    public int updateContact(CustomPinPoint content) {
    	
    	SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    	dateformatter.setTimeZone(TimeZone.getDefault());
    	Date _nowDate = new Date();
    	
    	String _nowDateStr = dateformatter.format(_nowDate);
    	
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        
        values.put(KEY_MARKER_ID, content.getDBID()); // DOWNLOAD TABLE ID.
        values.put(KEY_COORCENTER_X, content.getInitCenterPos().x); //FILE Subscription ID.
        values.put(KEY_COORCENTER_Y, content.getInitCenterPos().y); //FILE Download path
        values.put(KEY_MARKER_X, content.getTouchX());//File Title.
        values.put(KEY_MARKER_Y, content.getTouchY()); // CONTENT FILELINK.
        values.put(KEY_MAP_SCALE, content.getZoomOut());//Content File Description.
        values.put(KEY_CREATAT_DATE, content.getCreateAtDate());
        
        // updating row
        return db.update(TABLE_CONTENT, values, KEY_MARKER_ID + " = ?",
                new String[] { String.valueOf(content.getDBID()) });
    }
 
    
    
    // Deleting single contact
    public void deleteContact(CustomPinPoint content) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTENT, KEY_MARKER_ID + " = ?",
                new String[] { String.valueOf(content.getDBID()) });
        db.close();
    }
 
    public void deleteAll()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("delete from "+ TABLE_CONTENT);
    	db.close();
    }
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        // return count
        return cursor.getCount();
    }
    
}
