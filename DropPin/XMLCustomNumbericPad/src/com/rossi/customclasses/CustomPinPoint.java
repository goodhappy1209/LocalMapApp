package com.rossi.customclasses;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.util.Log;

public class CustomPinPoint {

	Integer tbl_maker_id;
	float x_touch_pos;
	float y_touch_pos;
	float center_x_pos;
	float center_y_pos;
	float zoomOutVal;
	String _mCreateAtDate;
	
	Context _mContext;
	
	public CustomPinPoint()
	{
		
	}
	
	public CustomPinPoint(Context mContext) {
		// TODO Auto-generated constructor stub
		this._mContext = mContext;
	}
	

	public void setTouchPinPoint (float x, float y)
	{
		this.x_touch_pos = x;
		this.y_touch_pos = y;
	}
	
	
	public void setCenterPosition (PointF _centerPos) {
		this.center_x_pos = _centerPos.x;
		this.center_y_pos = _centerPos.y;
	}

	public void setZoomOut(float zoomOut)
	{
		this.zoomOutVal = zoomOut;
	}

	public void setDBId(Integer _dbID)
	{
		this.tbl_maker_id = _dbID;
	}
	
	public void setCreateAtDate(String createAt)
	{
		this._mCreateAtDate = createAt;
	}
	
	public void setCustomPinState(Integer _dbID, float x_touch_pos2, float y_touch_pos2, float centerX, float centerY,
			float zoomOutVal2, String createAt) {
		// TODO Auto-generated method stub
		x_touch_pos = x_touch_pos2;
		y_touch_pos = y_touch_pos2;
		zoomOutVal = zoomOutVal2;
		center_x_pos = centerX;
		center_y_pos = centerY;
		tbl_maker_id = _dbID;
		_mCreateAtDate = createAt;
	}
	
	public float getTouchX()
	{
		return this.x_touch_pos;
	}
	
	public float getTouchY()
	{
		return this.y_touch_pos;
	}
	
	public float getZoomOut()
	{
		return this.zoomOutVal;
	}
	
	public PointF getInitCenterPos()
	{
		PointF _centerPos = new PointF(center_x_pos, center_y_pos);
		return _centerPos;
	}
	
	public Integer getDBID()
	{
		return tbl_maker_id;
	}
	
	public String getCreateAtDate()
	{
		return _mCreateAtDate;
	}
}
