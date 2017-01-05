package com.rossi.xmlcustomnumberpad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.PinnedPositions;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.polites.android.GestureImageViewListener;
import com.polites.android.VectorF;
import com.rossi.customclasses.CustomMapState;
import com.rossi.customclasses.CustomPinPoint;
import com.rossi.customclasses.DatabaseHandler;

public class StandardImageProgrammaticWithStartSettings extends StandardImageProgrammatic implements OnClickListener, GestureImageViewListener, AnimationListener{
	
	private float _x_touch;
	private float _y_touch;
	private ArrayList<HashMap<String, Object>> _mPinPointArray = new ArrayList<HashMap<String,Object>>();
	private Handler handler;
	private CustomMapState _customPinPoint;
	private DatabaseHandler _dbMarker;
	private Animation animMoveTop;
	private Boolean lock_drop_pin;
	private boolean _flagDropEnabled;
	private CustomPinPoint _currentPin;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	lock_drop_pin = false;
    	_flagDropEnabled = false;
    	_customPinPoint = new CustomMapState(StandardImageProgrammaticWithStartSettings.this);    	
    	
    	
    	view.setStartingScale(_customPinPoint.getZoomOut());
    	view.setStartingPosition(_customPinPoint.getImageViewX(), _customPinPoint.getImageViewY());
    	view.setOnClickListener(StandardImageProgrammaticWithStartSettings.this);
    	view.setGestureImageViewListener(this);
    	
    	btnClearPins.setOnClickListener(this);
    	
    	handler = new Handler();
    	
    	_dbMarker = new DatabaseHandler(this);
    	_currentPin = new CustomPinPoint();
    	
    	ActionBar actionBar = getSupportActionBar();
    	actionBar.setHomeButtonEnabled(true);
    	actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.

	        	_dbMarker.deleteAll();
				for (HashMap<String, Object> _pinInformation :_mPinPointArray) {
					CustomPinPoint _customPin = (CustomPinPoint) _pinInformation.get("pin_data");
					_dbMarker.addContact(_customPin);
				}
				
	        	onBackPressed();
	        	
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		
		if (_dbMarker.getContactsCount() != 0) {
			List<CustomPinPoint> _pinSaveDataList = _dbMarker.getAllContacts();
			for (CustomPinPoint _pinSaveData : _pinSaveDataList) {
				addNewPin(_pinSaveData);
			}
		}
	}
	
	public void addNewPin(final CustomPinPoint _customPoint)
	{
		 LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		 
		 final ImageView ivMarker = new ImageView(this);
	     ivMarker.setImageResource(R.drawable.ic_room_black_48dp);
	     ivMarker.setLayoutParams(layoutParams);
	     
	     _mlayout.addView(ivMarker);
	     ivMarker.setVisibility(View.INVISIBLE);
	     ivMarker.setOnClickListener(StandardImageProgrammaticWithStartSettings.this);
	     
	     
	     
	     handler.postDelayed(new Runnable(){
	 		@Override
	 			public void run(){
	 			
	 		     ivMarker.setX(_customPoint.getTouchX() - ivMarker.getWidth()/2);
	 			 ivMarker.setY(_customPoint.getTouchY() - ivMarker.getHeight());
	 			 ivMarker.setVisibility(View.VISIBLE);
	 			 
	 		     HashMap<String, Object> _hashPin = new HashMap<String, Object>();
	 		     
	 		     _hashPin.put("pin_data", _customPoint);
	 		     _hashPin.put("pin_marker_view", ivMarker);
	 		     
	 		     _mPinPointArray.add(_hashPin);
	 		     
	 		     _mTv_PlacePos.setText("Pin Position : " + "("+_customPoint.getTouchX()+" : " + _customPoint.getTouchY()+")");
	 		     _mTv_CreateAt.setText("Placed " + _customPoint.getCreateAtDate());
	 		     
	 		     refreshPinPoint(_mPinPointArray);
	 			}
	 		}, 1);
	     
	}
	
	public void moveToPin (final CustomPinPoint _customPoint, final ImageView ivMarker)
	{
		 handler.postDelayed(new Runnable(){
		 		@Override
		 			public void run(){
		 			
		 		     ivMarker.setX(_customPoint.getTouchX() - ivMarker.getWidth()/2);
		 			 ivMarker.setY(_customPoint.getTouchY() - ivMarker.getHeight());
		 			 ivMarker.setVisibility(View.VISIBLE);
		 			
		 			 ivMarker.setTag(_customPoint);
		 			 _currentPin = _customPoint;
		 		     HashMap<String, Object> _hashPin = new HashMap<String, Object>();
		 		    
		 		    
		 		     _hashPin.put("pin_data", _customPoint);
		 		     _hashPin.put("pin_marker_view", ivMarker);
		 		     
		 		     for (HashMap<String, Object> collection : _mPinPointArray) {
		 		    	 if (collection.get("pin_marker_view").equals(ivMarker)) {
		 		    		 
		 		    		 _mPinPointArray.remove(collection);
		 		    		 _mPinPointArray.add(_hashPin);
						}
					}
		 		     
		 		    _mTv_PlacePos.setText("Pin Position : " + "("+_customPoint.getTouchX()+" : " + _customPoint.getTouchY()+")");
		 		    _mTv_CreateAt.setText("Placed " + _customPoint.getCreateAtDate());
		 		    
		 			}
		 		}, 1);
	}
	
	public void refreshPinPoint (ArrayList<HashMap<String, Object>> _pinDataArray)
	{
		for (HashMap<String, Object> _hashPinData : _pinDataArray) {
		
			VectorF _initedVector = new VectorF();
			
			CustomPinPoint _customPin = (CustomPinPoint) _hashPinData.get("pin_data");
			ImageView _pinMarker = (ImageView) _hashPinData.get("pin_marker_view");
			
			_initedVector.setStart(_customPin.getInitCenterPos());
			_initedVector.setEnd(new PointF(_customPin.getTouchX(), _customPin.getTouchY()));

			Float _initVectorLength = _initedVector.calculateLength();
			Float _inintVectorAngle = _initedVector.calculateAngle();
		
			VectorF _currentVector = new VectorF();
			_currentVector.setStart(new PointF(view.getImageX(), view.getImageY()));
			_currentVector.angle = _inintVectorAngle;
			_currentVector.length = _initVectorLength * view.getScale()/_customPin.getZoomOut();
			
			_currentVector.calculateEndPoint();
			
			PointF _currentEndPos = new PointF(_currentVector.end.x, _currentVector.end.y);
		
			_pinMarker.setX(_currentEndPos.x - _pinMarker.getWidth()/2);
			_pinMarker.setY(_currentEndPos.y - _pinMarker.getHeight());
			_pinMarker.setTag(_customPin);
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		_customPinPoint.saveLastMapState();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.string.id_imageview:
			{
				if (!lock_drop_pin) {
					return;
				}
				
				CustomPinPoint _newPinPoint = new CustomPinPoint();
				PointF _startPoint = new PointF(view.getImageX(), view.getImageY());
				_newPinPoint.setTouchPinPoint(_x_touch, _y_touch);
				_newPinPoint.setCenterPosition(_startPoint);
				_newPinPoint.setZoomOut(view.getScale());
				
				if (_flagDropEnabled) {
					addNewPin(_newPinPoint);	
					_currentPin = _newPinPoint;
					_flagDropEnabled = false;
					return;
				}
				
				ArrayList<View> allViewsWithinMyTopView = getAllChildren(_mlayout);
				 for (View _view : allViewsWithinMyTopView) {
					if (_view instanceof ImageView && (CustomPinPoint)_view.getTag() == _currentPin) {
						moveToPin(_newPinPoint, (ImageView)_view);
					}
				}
				
			
			}
				break;
			case R.id.btn_numberic_delete:
			{
				
				if (!v.isSelected()) {
					
					v.setX(v.getX());
					v.setY(0);
					
//					rly_placed_board.setVisibility(View.GONE);
					animMoveTop = AnimationUtils.loadAnimation(this, R.anim.move_fab_button);
					animMoveTop.setAnimationListener(this);
					v.startAnimation(animMoveTop);
					_flagDropEnabled = true;
					
					return;
				}
				
				_flagDropEnabled = true;
				
				_dbMarker.deleteAll();
				for (HashMap<String, Object> _pinInformation :_mPinPointArray) {
					CustomPinPoint _customPin = (CustomPinPoint) _pinInformation.get("pin_data");
					_dbMarker.addContact(_customPin);
				}
				refreshPinsData();
			}
				break;
			default:
				break;
		}
		if (v.getTag() != null) {
			
			CustomPinPoint _pinMarkerData  = new CustomPinPoint();
			_pinMarkerData = (CustomPinPoint) v.getTag();
			_mTv_PlacePos.setText("Pin Position : " + "("+_pinMarkerData.getTouchX()+" : " + _pinMarkerData.getTouchY()+")");
 		    _mTv_CreateAt.setText("Placed " + _pinMarkerData.getCreateAtDate());
		}
	}

	@Override
	public void onTouch(float x, float y) {
		// TODO Auto-generated method stub
		
//		Log.e("Gesture Image Touch Position : ", x + " : " + y);			
		_x_touch = x;
		_y_touch = y;
		
	}

	public void deletePins()
	{
		_dbMarker.deleteAll();
		_mPinPointArray.removeAll(_mPinPointArray);
		
		ArrayList<View> allViewsWithinMyTopView = getAllChildren(_mlayout);
		 for (View _view : allViewsWithinMyTopView) {
			if (_view instanceof ImageView && _view.getId() != R.string.id_imageview) {
				_mlayout.removeView(_view);
			}
		}
	}
	
	public void refreshPinsData()
	{
		_mPinPointArray.removeAll(_mPinPointArray);
		
		ArrayList<View> allViewsWithinMyTopView = getAllChildren(_mlayout);
		 for (View _view : allViewsWithinMyTopView) {
			if (_view instanceof ImageView && _view.getId() != R.string.id_imageview) {
				_mlayout.removeView(_view);
			}
		}
		 
		if (_dbMarker.getContactsCount() != 0) {
				List<CustomPinPoint> _pinSaveDataList = _dbMarker.getAllContacts();
				for (CustomPinPoint _pinSaveData : _pinSaveDataList) {
					addNewPin(_pinSaveData);
				}
		}
	}
	
	public static ArrayList<View> getAllChildren (View v)
	{
		 if (!(v instanceof ViewGroup)) {
		        ArrayList<View> viewArrayList = new ArrayList<View>();
		        viewArrayList.add(v);
		        return viewArrayList;
		 }

		 ArrayList<View> result = new ArrayList<View>();

		 ViewGroup vg = (ViewGroup) v;
		 for (int i = 0; i < vg.getChildCount(); i++) {

			 View child = vg.getChildAt(i);
		     ArrayList<View> viewArrayList = new ArrayList<View>();
		     viewArrayList.add(v);
		     viewArrayList.addAll(getAllChildren(child));

		     result.addAll(viewArrayList);
		 }
		 return result;
	}
	
	@Override
	public void onScale(float scale) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPosition(final float x, final float y) {
		// TODO Auto-generated method stub
//		Log.e("Gesture Image Position : ", x + " : " + y);
		
		handler.postDelayed(new Runnable(){
		@Override
			public void run(){
				_customPinPoint.setMapInformation(x, y, view.getScaledWidth(), view.getScaledHeight(), view.getImageX(), view.getImageY(), view.getScale());
				refreshPinPoint(_mPinPointArray);
		   	}
		}, 1);
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		lock_drop_pin = true;
		btnClearPins.setSelected(true);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
}