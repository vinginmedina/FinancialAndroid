package com.ving.financialstatement;

import java.util.ArrayList;
import java.util.Collections;

public class TransactionCatagories {
	
	private ArrayList<CatagoryGroup> catagories;
	private TransactionList transactions;
	
	public TransactionCatagories (TransactionList transList) {
		transactions = transList;
		catagories = new ArrayList<CatagoryGroup>();
		for (Transaction trans : transList.allTransactions()) {
			if ((trans.catagory().toString().equals("Transfer From : Checking AFCU")) ||
					(trans.catagory().toString().equals("Transfer To : Cash"))){
				continue;
			}
			Boolean found = false;
			for (CatagoryGroup cg : catagories) {
				if ((! found) && (cg.matchGroup(trans.catagory()))) {
					cg.add(trans);
					found = true;
				}
			}
			if (! found) {
				CatagoryGroup cg = new CatagoryGroup(trans.catagory().parent());
				cg.add(trans);
				catagories.add(cg);
			}
		}
		Collections.sort(catagories);
		for (CatagoryGroup cg : catagories) {
			cg.sort();
		}
	}
	
	public ArrayList<CatagoryGroup> catagories() {
		return catagories;
	}

}
