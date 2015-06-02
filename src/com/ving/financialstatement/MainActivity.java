package com.ving.financialstatement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private MyApplication myApp = null;
	private MainActivity myAct = null;
	private Context mContext = null;
	private ExpandableListView statementListView = null;
	private StatementListAdapter statementAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApp = (MyApplication) getApplication();
		setContentView(R.layout.activity_main);
		myAct = this;
		mContext = this;
		statementListView = (ExpandableListView) findViewById(R.id.statementDataList);
		myApp.setData(this, mContext, statementListView);
		ReadFinancialData readDataTask = new ReadFinancialData(myAct, mContext);
		readDataTask.execute(myApp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Boolean rtn = true;
		Intent userCreationIntent = null;
		
		switch (item.getItemId()) { 
		case R.id.catagories:
			userCreationIntent = new Intent(myApp, CatagoriesActivity.class);
			break;
		case R.id.alltrans:
			myApp.setDisplayTrans(myApp.currentTrans(), true);
			userCreationIntent = new Intent(myApp, TransactionActivity.class);
			break;
		case R.id.furture:
			myApp.setDisplayTrans(myApp.futureTrans(), false);
			userCreationIntent = new Intent(myApp, TransactionActivity.class);
			break;
		default:
			rtn = super.onOptionsItemSelected(item);
		}
		if (userCreationIntent != null) {
			userCreationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			myApp.startActivity(userCreationIntent);
		}
		return rtn;
	}
	
	public void setFrontPage() {
		TextView statementDate = (TextView) findViewById(R.id.statementDate);
		statementDate.setText(myApp.reportDateString());
		
		statementAdapter = new StatementListAdapter(mContext, myApp, this);
		statementListView.setAdapter(statementAdapter);
		myApp.setStatementAdapter(statementAdapter);
		statementAdapter.notifyDataSetChanged();
	}
	
	public void scrollAdapter(final int posn) {
		if ((statementListView != null) && (statementAdapter != null) &&
				((posn >= 0) && (posn < myApp.statement().tableData().length))) {
			statementListView.post(new Runnable() {
	            @Override
	            public void run() {
	            	statementListView.setSelectedGroup(posn);
	            	statementListView.smoothScrollToPosition(posn);
	            }
	        });
		}
	}
}
