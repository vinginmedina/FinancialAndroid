package com.ving.financialstatement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class TransactionActivity extends Activity {
	
	private MyApplication myApp = null;
	private Context mContext = null;
	private ListView transactionView = null;
	private ArrayAdapter<Transaction> adapter = null;
	private TransactionList trans = null;
	private String[][] data = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		myApp = (MyApplication) getApplication();
		setContentView(R.layout.activity_transactions);
		trans = myApp.displayTrans();
		if (trans == null) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setTitle("No Transaction Data");
			dialog.setMessage("There is no transaction data to display.");
			dialog.setCancelable(false);
			dialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
							finish();
						}
					});
			dialog.show();
		}
		adapter = new TransactionListAdapter(mContext, R.layout.transaction_item, trans.allTransactions());
		transactionView = (ListView) findViewById(R.id.transactionDataList);
		transactionView.setAdapter(adapter);
		if (myApp.useStartBalance()) {
			data = trans.tableData(myApp.startingBalance());
		} else {
			data = trans.tableData();
		}
		
	}

}
