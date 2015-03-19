package com.ving.financialstatement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class CatagoriesActivity extends Activity {
	
	private MyApplication myApp = null;
	private Context mContext = null;
	private ExpandableListView catagoryListView = null;
	private CatagoryListAdapter catagoryAdapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		myApp = (MyApplication) getApplication();
		setContentView(R.layout.activity_catagories);
		catagoryListView = (ExpandableListView) findViewById(R.id.catagoryDataList);
		catagoryAdapter = new CatagoryListAdapter(mContext, myApp, this);
		catagoryListView.setAdapter(catagoryAdapter);
		catagoryListView.setOnItemLongClickListener(new OnItemLongClickListener() {
		    @Override
		    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		    	Log.i("onItemLongClick","Did a long click "+ExpandableListView.getPackedPositionType(id));
		    	int groupPosition = ExpandableListView.getPackedPositionGroup(id);
		    	int childPosition = ExpandableListView.getPackedPositionChild(id);
		        if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
		            // You now have everything that you would as if this was an OnChildClickListener() 
		            // Add your logic here.
//		            Log.i("LongPress","did a long press "+groupPosition+" "+childPosition);
		        	CatagoryGroup catgrp = (CatagoryGroup) myApp.catagories().catagories().get(groupPosition);
		        	myApp.setDisplayTrans(catgrp.transactions(), false);
		        	Intent userCreationIntent = new Intent(myApp, TransactionActivity.class);
		        	userCreationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					myApp.startActivity(userCreationIntent);

		            // Return true as we are handling the event.
		            return true;
		        }
		        return false;
		    }
		});
	}
	
	public void scrollAdapter(final int posn) {
		if ((catagoryListView != null) && (catagoryAdapter != null) &&
				((posn >= 0) && (posn < myApp.catagories().catagories().size()))) {
			catagoryListView.post(new Runnable() {
	            @Override
	            public void run() {
	            	catagoryListView.setSelectedGroup(posn);
	            	catagoryListView.smoothScrollToPosition(posn);
	            }
	        });
		}
	}

}
