package com.rossi.xmlcustomnumberpad;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private EditText _mTvNumbericTxt;
	private Button _mBtnView;
	private ImageButton _mIbtnSetting;
	private LinearLayout _mLyNumbericPad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout);
		
		initGUI();
	}

	private void initGUI() {
		// TODO Auto-generated method stub

		_mTvNumbericTxt = (EditText) findViewById(R.id.tv_numberic_field);
		_mBtnView = (Button) findViewById(R.id.btn_views);
		_mIbtnSetting = (ImageButton) findViewById(R.id.settingsButton);
		
		_mLyNumbericPad = (LinearLayout) findViewById(R.id.ly_numbericpad_parent);
		_mIbtnSetting.setOnClickListener(MainActivity.this);
		_mBtnView.setOnClickListener(MainActivity.this);
		_mTvNumbericTxt.setEnabled(false);
		 ArrayList<View> allViewsWithinMyTopView = getAllChildren(_mLyNumbericPad);
		 for (View _view : allViewsWithinMyTopView) {
			if (_view instanceof Button) {
				_view.setOnClickListener(MainActivity.this);
				_view.setTag(((Button) _view).getText());
			}
		}
	}

	private ArrayList<View> getAllChildren (View v)
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_views:
			{
				Log.e("Selected View Button", "==================");
				
				Intent intent = new Intent(MainActivity.this, StandardImageProgrammaticWithStartSettings.class);
				startActivity(intent);
				
				finish();
			}
				break;
			case R.id.settingsButton:
			{
				Log.e("Selected setting Button", "==================");
			}
				break;
			case R.id.btn_numberic_delete:
			{
				Log.e("Selected numberic delete Button", "==================");
				int length = _mTvNumbericTxt.getText().length();
				
				if (length > 0) {
					_mTvNumbericTxt.getText().delete(length-1, length);
				}
			}
				break;
			case R.id.btn_numberic_trans:
			{
				Log.e("Selected numberic trans Button", "==================");
			}
				break;
			default:
			{
				if(v instanceof Button)
				{
					
					String text = (String) v.getTag();
					_mTvNumbericTxt.append(text);
					Log.e("Selected Numberic Button : ", "========="+text+"========");
				}
			}
				break;
		}
	}

	
}
