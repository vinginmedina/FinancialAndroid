package com.ving.financialstatement;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
	
	private Context mContext;
    private ArrayList<Transaction> trans;
    
    public TransactionListAdapter(Context context, int textViewResourceId, ArrayList<Transaction> trans) {
    	super(context, textViewResourceId, trans);
    	mContext = context;
    	this.trans = trans;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = LayoutInflater.from(mContext);
			v = vi.inflate(R.layout.transaction_item, null);
		}
		if (trans.get(position) != null) {
			String[] data = trans.get(position).printValues();
			TextView num = (TextView) v.findViewById(R.id.num);
			TextView date = (TextView) v.findViewById(R.id.date);
			TextView payee = (TextView) v.findViewById(R.id.payee);
			TextView catagory = (TextView) v.findViewById(R.id.catagory);
			TextView debit = (TextView) v.findViewById(R.id.debit);
			TextView credit = (TextView) v.findViewById(R.id.credit);
			num.setText(data[0]);
			date.setText(data[1]);
			payee.setText(data[2]);
			catagory.setText(data[3]);
			debit.setText(data[4]);
			credit.setText(data[5]);
		}
		return v;
    }

}
