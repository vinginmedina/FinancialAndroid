package com.ving.financialstatement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class MyApplication extends Application {
	
	public static final String dailyURL = "URL for Daily Transaction File";
	public static final String statementURL = "URL for Statement File";
	
	private Date reportDate;
	private Float startBalance;
	private StatementData statement;
	private TransactionList currentTrans;
	private TransactionList futureTrans;
	private TransactionCatagories catagories;
	private TransactionList transToDisplay;
	private Boolean useStartBalance;
	
	private Context mContext;
	private MainActivity mainAct;
	private ExpandableListView statementListView;
	private StatementListAdapter statementAdapter;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mainAct = null;
		mContext = null;
		statementListView = null;
		statementAdapter = null;
		reportDate = null;
		startBalance = null;
		statement = null;
		currentTrans = null;
		futureTrans = null;
		catagories = null;
		transToDisplay = null;
		useStartBalance = false;
	}
	
	public void setData(MainActivity ma, Context con, ExpandableListView lv) {
		mainAct = ma;
		mContext = con;
		statementListView = lv;
	}
	
	public void setStatementAdapter(StatementListAdapter sa) {
		statementAdapter = sa;
	}
	
	public void financialData(Date rd, Float sb, StatementData sd, TransactionList ct, TransactionList ft) {
		reportDate = rd;
		startBalance = sb;
		statement = sd;
		currentTrans = ct;
		futureTrans = ft;
		catagories = new TransactionCatagories(currentTrans);
		mainAct.setFrontPage();
	}
	
	public String reportDateString() {
		String rtn = "";
		if (reportDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
			rtn = sdf.format(reportDate);
		}
		
		return rtn;
	}
	
	public Float startingBalance() {
		return startBalance;
	}
	
	public StatementData statement() {
		return statement;
	}
	
	public TransactionCatagories catagories() {
		return catagories;
	}
	
	public TransactionList currentTrans() {
		return currentTrans;
	}
	
	public TransactionList futureTrans() {
		return futureTrans;
	}
	
	public TransactionList displayTrans() {
		return transToDisplay;
	}
	
	public Boolean useStartBalance() {
		return useStartBalance;
	}
	
	public void setDisplayTrans(TransactionList lst, Boolean flag) {
		transToDisplay = lst;
		useStartBalance = flag;
	}

}
