package com.rossi.xmlcustomnumberpad;

import javax.crypto.spec.IvParameterSpec;

import com.polites.android.GestureImageView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class StandardImageProgrammatic extends ExampleActivity {
	
	protected GestureImageView view;
	protected ViewGroup _mlayout;
	protected ImageButton btnClearPins;
	protected RelativeLayout rly_placed_board;
	protected TextView _mTv_PlacePos;
	protected TextView _mTv_CreateAt;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.empty);
    	
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        view = new GestureImageView(this);
        view.setImageResource(R.drawable.image_design);
        view.setLayoutParams(params);
        view.setId(R.string.id_imageview);
       
        btnClearPins = (ImageButton) findViewById(R.id.btn_numberic_delete);
        rly_placed_board = (RelativeLayout) findViewById(R.id.rly_user_board);
        _mTv_PlacePos = (TextView) findViewById(R.id.tv_placement_id);
        _mTv_CreateAt = (TextView) findViewById(R.id.tv_placed_info);
        _mlayout = (ViewGroup) findViewById(R.id.layout);
        _mlayout.addView(view);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	
    	return super.onCreateOptionsMenu(menu);
    	
    }
}