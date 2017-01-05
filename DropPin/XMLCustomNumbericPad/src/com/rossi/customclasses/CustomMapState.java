package com.rossi.customclasses;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.util.Log;

public class CustomMapState {

	private Context _mContext;
	private Float x_ImageViewPosition;
	private Float y_ImageViewPosition;
	private Float scale_width;
	private Float scale_height;
	private Float zoomOutVal;
	private float center_x_pos;
	private float center_y_pos;

	public CustomMapState() {
		// TODO Auto-generated method stub
	}
	
	public CustomMapState(Context mContext)
	{
		this._mContext = mContext;
		
		HashMap<String, Object> _initCus = new HashMap<String, Object>();
		
		_initCus = getCustomMapState();
		
		x_ImageViewPosition = (Float)_initCus.get("x_image_pos");
		y_ImageViewPosition = (Float)_initCus.get("y_image_pos");
		
		scale_width = (Float) _initCus.get("scale_width");
		scale_height = (Float) _initCus.get("scale_height");
		
		zoomOutVal = (Float)_initCus.get("zoomOut");
	}

	public void setImagePosition (float x, float y)
	{
		this.x_ImageViewPosition = x;
		this.y_ImageViewPosition = y;
	}
	
	public void setDecliationPosition (float width, float height, PointF _centerPos) {
		this.scale_width = width;
		this.scale_height = height;
		this.center_x_pos = _centerPos.x;
		this.center_y_pos = _centerPos.y;
	}
	
	public void setZoomOut(float zoomOut)
	{
		this.zoomOutVal = zoomOut;
	}
	
	public void setMapInformation (float image_pos_x, float image_pos_y, float image_scaled_width, float image_scaled_height, float _center_x, float _center_y, float zoomVal)
	{
		this.x_ImageViewPosition = image_pos_x;
		this.y_ImageViewPosition = image_pos_y;
		this.scale_width = image_scaled_width;
		this.scale_height = image_scaled_height;
		this.center_x_pos = _center_x;
		this.center_y_pos = _center_y;
		this.zoomOutVal = zoomVal;
	}
	
	
	public void saveLastMapState ()
	{
		saveTouchState(x_ImageViewPosition, y_ImageViewPosition, scale_width, scale_height, center_x_pos, center_y_pos,  this.zoomOutVal);
	}
	
	
	private void saveTouchState(Float x_ImageViewPosition2,
			Float y_ImageViewPosition2, Float scale_width2,
			Float scale_height2, float center_x_pos2, float center_y_pos2,
			Float zoomOutVal2) {
		// TODO Auto-generated method stub
		
		SharedPreferences defaultStandard = _mContext.getSharedPreferences("CustomMapState", 0);
	    SharedPreferences.Editor editer = defaultStandard.edit();
	    
    	editer.putFloat("x_image_pos", x_ImageViewPosition2);
    	editer.putFloat("y_image_pos", y_ImageViewPosition2);
    	editer.putFloat("scale_width", scale_width2);
    	editer.putFloat("scale_height", scale_height2);
    	editer.putFloat("center_x_pos", center_x_pos2);
    	editer.putFloat("center_y_pos", center_y_pos2);
    	editer.putFloat("zoomOut", zoomOutVal2);
    	
    	editer.commit();
		
	}

	private HashMap<String, Object> getCustomMapState() {
		// TODO Auto-generated method stub
		SharedPreferences defaultStandard = _mContext.getSharedPreferences("CustomMapState", 0);
		
		
		HashMap<String, Object> _hashTouchPin = new HashMap<String, Object>();
		
		Float _zoomOut = defaultStandard.getFloat("zoomOut", 2.0f);
		Float _x_image_pos = defaultStandard.getFloat("x_image_pos", 0);
		Float _y_image_pos = defaultStandard.getFloat("y_image_pos", 0);
		Float _scaledWidth = defaultStandard.getFloat("scale_width", 0);
		Float _scaledHeight = defaultStandard.getFloat("scale_height", 0);
		Float _centerX = defaultStandard.getFloat("center_x_pos", 0);
		Float _centerY = defaultStandard.getFloat("center_y_pos", 0);
		
		
		_hashTouchPin.put("x_image_pos", _x_image_pos);
    	_hashTouchPin.put("y_image_pos", _y_image_pos);
		_hashTouchPin.put("scale_width", _scaledWidth);
		_hashTouchPin.put("scale_height", _scaledHeight);
		_hashTouchPin.put("zoomOut", _zoomOut);
		
		PointF _centerPos = new PointF(_centerX, _centerY);
		
		_hashTouchPin.put("center_pos", _centerPos);
		
		return _hashTouchPin;
	}
	
	
	public float getImageViewX()
	{
		return this.x_ImageViewPosition;
	}
	
	public float getImageViewY()
	{
		return this.y_ImageViewPosition;
	}
	
	public float getZoomOut()
	{
		return this.zoomOutVal;
	}
	
	
	public float getInitScaledWidth()
	{
		return this.scale_width;
	}
	
	public float getInitScaledHeight()
	{
		return this.scale_height;
	}
	
	public PointF getInitCenterPos()
	{
		PointF _centerPos = new PointF(center_x_pos, center_y_pos);
		return _centerPos;
	}
	
}
