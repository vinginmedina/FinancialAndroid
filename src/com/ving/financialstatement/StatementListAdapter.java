package com.ving.financialstatement;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class StatementListAdapter extends BaseExpandableListAdapter {
	
	private Context mContext;
	private LayoutInflater inflater;
    private String[][] statementData;
    private MyApplication myApp;
    private MainActivity activity;
    private String undef;
 
    public StatementListAdapter(Context context, MyApplication myApp, MainActivity act){
    	mContext = context;
    	this.myApp = myApp;
    	activity = act;
    	statementData = this.myApp.statement().tableData();
        inflater = LayoutInflater.from(context);
        undef = Integer.toString(StatementData.UNDEF);
    }
    
    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return statementData.length;
    }
 
    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
    	int rtn = 0;
    	int type;
    	try {
			type = Integer.parseInt(statementData[i][2]);
		} catch (NumberFormatException e) {
			type = StatementData.UNDEF;
		}
    	if (type != StatementData.UNDEF) {
    		rtn = myApp.statement().tableList(type).length;
    	}
    	
    	return rtn;
    }
 
    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return statementData[i][0];
    }
 
    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
    	String [][] rtn = null;
    	int type;
    	try {
			type = Integer.parseInt(statementData[i][2]);
		} catch (NumberFormatException e) {
			type = StatementData.UNDEF;
		}
    	if (type != StatementData.UNDEF) {
    		rtn = myApp.statement().tableList(type);
    	}
    	
    	return rtn;
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
        if (! statementData[groupPosition][2].equals(undef)) {
        	activity.scrollAdapter(groupPosition);
        }
   }
 
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
 
    	final String[] rowData = statementData[i];
        if (view == null) {
            view = inflater.inflate(R.layout.statement_item, viewGroup,false);
        }
        
        TextView label = (TextView) view.findViewById(R.id.label);
        label.setText(rowData[0]);
        TextView value = (TextView) view.findViewById(R.id.value);
        value.setText(rowData[1]);
        return view;
    }
 
    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
    	final String[] rowData = statementData[i];
    	int type;
    	try {
			type = Integer.parseInt(statementData[i][2]);
		} catch (NumberFormatException e) {
			type = StatementData.UNDEF;
		}
    	if (view == null) {
            view = inflater.inflate(R.layout.statement_detail_item, viewGroup,false);
        }
    	
    	if (type != StatementData.UNDEF) {
	    	final String[][] childRowData = myApp.statement().tableList(type);
	    	TextView label = (TextView) view.findViewById(R.id.label);
	        label.setText(childRowData[i1][0]);
	        TextView value = (TextView) view.findViewById(R.id.value);
	        value.setText(childRowData[i1][1]);
    	}

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
