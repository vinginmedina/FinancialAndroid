package com.ving.financialstatement;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class CatagoryListAdapter extends BaseExpandableListAdapter {
	
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<CatagoryGroup> catagoryData;
    private MyApplication myApp;
    private CatagoriesActivity activity;
    
    public CatagoryListAdapter(Context context, MyApplication myApp, CatagoriesActivity act){
    	mContext = context;
    	this.myApp = myApp;
    	activity = act;
    	catagoryData = this.myApp.catagories().catagories();
        inflater = LayoutInflater.from(mContext);
    }
    
    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return catagoryData.size();
    }
 
    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
    	return catagoryData.get(i).groupList().size();
    }
 
    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return catagoryData.get(i);
    }
 
    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
    	return catagoryData.get(i).groupList().get(i1);
    }
 
    @Override
    public long getGroupId(int i) {
        return i;
    }
 
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
 
    @Override
    public boolean hasStableIds() {
        return true;
    }
    
    @Override
    public void onGroupExpanded(final int groupPosition) {
        super.onGroupExpanded(groupPosition);
        activity.scrollAdapter(groupPosition);
   }
 
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
 
    	final CatagoryGroup catgrp = (CatagoryGroup) getGroup(i);
    	final MoneyObject mo = new MoneyObject(catgrp.groupName(), catgrp.total());
    	String[] rowData = mo.values();
        if (view == null) {
            view = inflater.inflate(R.layout.statement_item, viewGroup, false);
        }
        
        TextView label = (TextView) view.findViewById(R.id.label);
        label.setText(rowData[0]);
        TextView value = (TextView) view.findViewById(R.id.value);
        value.setText(rowData[1]);
        
        return view;
    }
 
    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
    	
    	final CatagoryGroup catgrp = (CatagoryGroup) getGroup(i);
    	final Catagory cat = (Catagory) getChild(i, i1);
    	MoneyObject mo = new MoneyObject(cat.toString(), catgrp.total(cat));
    	String[] rowData = mo.values();
    	if (view == null) {
            view = inflater.inflate(R.layout.statement_detail_item, viewGroup,false);
        }
    	
    	TextView label = (TextView) view.findViewById(R.id.label);
        label.setText(rowData[0]);
        TextView value = (TextView) view.findViewById(R.id.value);
        value.setText(rowData[1]);
        
        view.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				myApp.setDisplayTrans(new TransactionList(catgrp.transactions(cat)), false);
				Intent userCreationIntent = new Intent(myApp, TransactionActivity.class);
				userCreationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				myApp.startActivity(userCreationIntent);
				return false;
			}
        });

        return view;
        
    }
 
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
 
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        /* used to make the notifyDataSetChanged() method work */
        super.registerDataSetObserver(observer);
    }

}
