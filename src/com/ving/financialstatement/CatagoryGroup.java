package com.ving.financialstatement;

import java.util.ArrayList;
import java.util.Collections;

public class CatagoryGroup implements Comparable<CatagoryGroup> {
	
	private Catagory group;
	private ArrayList<Catagory> catList;
	private TransactionList transList;
	
	public CatagoryGroup (String groupName) {
		group = new Catagory(groupName);
		catList = new ArrayList<Catagory>();
		transList = new TransactionList();
	}
	
	public String groupName() {
		return group.parent();
	}
	
	public Boolean matchGroup(Catagory cat) {
		return group.sameParent(cat);
	}
	
	public ArrayList<Catagory> groupList() {
		return catList;
	}
	
	public TransactionList transactions() {
		return transList;
	}
	
	public ArrayList<Transaction> transactions(Catagory cat) {
		return transList.catagory(cat);
	}
	
	public Float total() {
		Float rtn = (float) 0.0;
		for (Transaction trans : transList.allTransactions()) {
			if (trans.type() == Transaction.DEBIT) {
				rtn += trans.amount();
			} else {
				rtn -= trans.amount();
			}
		}
		
		return rtn;
	}
	
	public Float total(Catagory cat) {
		Float rtn = (float) 0.0;
		for (Transaction trans : transList.catagory(cat)) {
			if (trans.type() == Transaction.DEBIT) {
				rtn += trans.amount();
			} else {
				rtn -= trans.amount();
			}
		}
		
		return rtn;
	}
	
	public void add(Transaction trans) {
		if (group.sameParent(trans.catagory())) {
			transList.add(trans);
			Boolean found = false;
			for (Catagory cat : catList) {
				if (cat.equals(trans.catagory())) {
					found = true;
				}
			}
			if (! found) {
				catList.add(trans.catagory());
			}
		}
	}
	
	public void sort() {
		Collections.sort(catList);
	}
	
	public int compareTo(CatagoryGroup cg) {
		return this.group.compareTo(cg.group);
	}

}
