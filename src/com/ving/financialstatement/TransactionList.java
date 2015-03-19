package com.ving.financialstatement;

import java.util.ArrayList;
import java.util.Collections;

public class TransactionList {
	
	public static final int DATE_SORT = 1;
	public static final int CATAGORY_SORT = 2;
	private ArrayList<Transaction> transactions;
	private int sortType;
	
	public TransactionList() {
		transactions = new ArrayList<Transaction>();
		sortType = DATE_SORT;
	}
	
	public TransactionList(ArrayList<Transaction> trans) {
		transactions = trans;
		sortType = DATE_SORT;
		Collections.sort(transactions, Transaction.dateSort);
	}
	
	public int size() {
		return transactions.size();
	}
	
	public void sortType(int st) {
		if ((st == DATE_SORT) || (st == CATAGORY_SORT)) {
			sortType = st;
		}
	}
	
	public void sort() {
		switch (sortType)
		{
			case DATE_SORT:
				Collections.sort(transactions, Transaction.dateSort);
				break;
			case CATAGORY_SORT:
				Collections.sort(transactions, Transaction.catagorySort);
				break;
		}		
	}
	
	public void add(Transaction trans) {
		transactions.add(trans);
	}
	
	public void add(String data) {
		Transaction trans = new Transaction(data);
		transactions.add(trans);
	}
	
	public ArrayList<Transaction> allTransactions() {
		return transactions;
	}
	
	public ArrayList<Transaction> catagory(Catagory cat) {
		ArrayList<Transaction> rtn = new ArrayList<Transaction>();
		for (Transaction trans : transactions) {
			if (trans.catagory().equals(cat)) {
				rtn.add(trans);
			}
		}
		return rtn;
	}
	
	public ArrayList<Transaction> parent(Catagory cat) {
		ArrayList<Transaction> rtn = new ArrayList<Transaction>();
		for (Transaction trans : transactions) {
			if (trans.catagory().sameParent(cat)) {
				rtn.add(trans);
			}
		}
		return rtn;
	}
	
	public String[][] tableData(Float startBalance) {
		String[][] data = null;
		Float balance = startBalance;
		if (transactions.size() > 0) {
			if (balance != null) {
				data = new String[transactions.size()][7];
				int i = 0;
				for (Transaction trans : transactions) {
					data[i] = trans.printValues(balance);
					if (trans.type() == Transaction.CREDIT) {
						balance += trans.amount();
					} else {
						balance -= trans.amount();
					}
					i++;
				}
			} else {
				data = tableData();
			}
		}
		
		return data;
	}
	
	public String[][] tableData() {
		String[][] data = null;
		if (transactions.size() > 0) {
			data = new String[transactions.size()][6];
			int i = 0;
			for (Transaction trans : transactions) {
				data[i] = trans.printValues();
				i++;
			}
		}
		
		return data;
	}

}
