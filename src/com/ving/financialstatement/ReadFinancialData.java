package com.ving.financialstatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class ReadFinancialData extends AsyncTask<MyApplication, Integer, MyApplication> {
	
	private MainActivity mainAct;
	private Context mContext;
	private ProgressDialog pd = null;
	private String errorMsg = null;
	private Date reportDate;
	private Float startBalance;
	private TransactionList actualTransactions;
	private TransactionList futureTransactions;
	private TransactionCatagories catagoryList;
	private StatementData statement;
	InputStream inp = null;
	URL url = null;
	URLConnection cnx = null;
	InputStreamReader ipsr = null;
	BufferedReader br = null;
	
	ReadFinancialData(MainActivity act, Context context) {
		mainAct = act;
		mContext = context;
	}
	
	protected void onPreExecute() {
//	    pd = ProgressDialog.show(mContext, "Reading", "Getting Financial Data");
		pd = new ProgressDialog(mContext);
	    pd.setIndeterminate(true);
	    pd.setIndeterminateDrawable(mainAct.getResources().getDrawable(R.drawable.progress_dialog_anim));
	    pd.setCancelable(false);
	    pd.setTitle("Reading");
	    pd.setMessage("Reading the CSV File...");
	    pd.show();
	}
	
	protected void onProgressUpdate(Integer... progress) {

    }
	
	protected MyApplication doInBackground(MyApplication... myApps) {
		try {
			url = new URL(myApps[0].dailyURL);
			cnx = url.openConnection();
			cnx.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
			cnx.setDoInput(true);
            cnx.setDoOutput(true);
			ipsr = new InputStreamReader(cnx.getInputStream());
			br = new BufferedReader(ipsr);
		} catch (IOException e) {
			e.printStackTrace();
			errorMsg = e.toString();
			cancel(true);
		}
		if (! isCancelled()) {
			actualTransactions = new TransactionList();
			futureTransactions = new TransactionList();
			String line;
			Boolean future = false;
			try {
				while ((line=br.readLine())!=null){
					if (line.startsWith("Date")) {
						String values[] = line.split("\\^");
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
						try {
							reportDate = sdf.parse(values[1]);
						} catch (ParseException e) {
							reportDate = null;
						}
					} else if (line.startsWith("^^^^^^")) {
						startBalance = Float.parseFloat(line.replaceAll("\\^","").replaceAll("\\$", ""));
					} else if (line.contains("Projected View")) {
						future = true;
					} else {
						if (future) {
							futureTransactions.add(line);
						} else {
							actualTransactions.add(line);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				errorMsg = e.toString();
				cancel(true);
			}
			try {
				br.close();
				ipsr.close();
			} catch (IOException e) {
				e.printStackTrace();
				errorMsg = e.toString();
				cancel(true);
			}
		}
		if (! isCancelled()) {
			try {
				url = new URL(myApps[0].statementURL);
				cnx = url.openConnection();
				cnx.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
				cnx.setDoInput(true);
	            cnx.setDoOutput(true);
				ipsr = new InputStreamReader(cnx.getInputStream());
				br = new BufferedReader(ipsr);
			} catch (IOException e) {
				e.printStackTrace();
				errorMsg = e.toString();
				cancel(true);
			}
			if (! isCancelled()) {
				statement = new StatementData(br);
				try {
					br.close();
					ipsr.close();
				} catch (IOException e) {
					errorMsg = e.toString();
					cancel(true);
				}
			}
		}
		return myApps[0];
	}
	
	protected void onCancelled(MyApplication myApp) {
		pd.cancel();
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	    builder.setTitle("No Data Retrieved");
	    builder.setMessage("Sorry, there was an error trying to get the Financial Data.\n" + errorMsg);
	    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int arg1) {
		            dialog.dismiss();
		        }});
	    builder.setCancelable(false);
	    AlertDialog myAlertDialog = builder.create();
	    myAlertDialog.show();
	}
	
	protected void onPostExecute(MyApplication myApp) {
		pd.cancel();
		if (! isCancelled()) {
			myApp.financialData(reportDate, startBalance, statement, actualTransactions, futureTransactions);
		}
	}

}
