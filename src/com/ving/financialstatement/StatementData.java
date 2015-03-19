package com.ving.financialstatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class StatementData {
	
	public static final int UNDEF = 0;
	public static final int BALANCE = 1;
	public static final int EXPECTEDINCOME = 2;
	public static final int PROJECTEDBALANCE = 3;
	public static final int BILLS = 4;
	public static final int FINALBALANCE = 5;
	public static final int PLANNEDBALANCE = 6;
	public static final int PROJECTION = 7;
	private ArrayList<MoneyObject> accounts;
	private MoneyObject availableBalance;
	private ArrayList<MoneyObject> income;
	private MoneyObject expectedIncome;
	private MoneyObject projectedBalance;
	private ArrayList<MoneyObject> bills;
	private MoneyObject total;
	private MoneyObject end;
	private MoneyObject planned;
	private ArrayList<MoneyObject> projection;
	private Integer weeks;
	
	public StatementData (BufferedReader br) {
		String line;
		String values[];
		MoneyObject mo;
		Boolean readingBills = false;
		
		accounts = new ArrayList<MoneyObject>();
		income = new ArrayList<MoneyObject>();
		bills = new ArrayList<MoneyObject>();
		availableBalance = null;
		expectedIncome = null;
		Float expInc = null;
		projectedBalance = null;
		total = null;
		end = null;
		planned = null;
		projection = new ArrayList<MoneyObject>();
		weeks = null;
		try {
			while ((line=br.readLine())!=null){
				values = line.split("\\^");
				if (values[0].equals("Blank")) {
					continue;
				}
				if (values[0].startsWith("Balance")) {
					values[0].replace("Balance ", "");
					mo = new MoneyObject(values);
					accounts.add(mo);
				}
				if (values[0].equals("Available Balance")) {
					availableBalance = new MoneyObject(values);
				}
				if (values[0].startsWith("Expected Income")) {
					values[0].replace("Expected Income ", "");
					mo = new MoneyObject(values);
					income.add(mo);
					if (expInc == null) {
						expInc = mo.amount();
					} else {
						expInc += mo.amount();
					}
				}
				if (values[0].equals("Projected Balance")) {
					projectedBalance = new MoneyObject(values);
				}
				if (values[0].equals("End Remaining Bills")) {
					readingBills = false;
				}
				if (readingBills) {
					mo = new MoneyObject(values[1],values[0]);
					bills.add(mo);
				}
				if (values[0].equals("Remaining Bills")) {
					readingBills = true;
				}
				if (values[0].equals("Total")) {
					total = new MoneyObject("Remaining Bills",values[1]);
				}
				if (values[0].equals("Ending Balance")) {
					end = new MoneyObject(values);
				}
				if (values[0].equals("Planned Balance")) {
					planned = new MoneyObject(values);
				}
				if ((values.length == 3) && (! values[0].equals("Planned Balance"))) {
					mo = new MoneyObject(values);
					projection.add(mo);
					weeks = Integer.valueOf(values[2].replaceAll("\\D+",""));
				}
			}
			if (expInc != null) {
				expectedIncome = new MoneyObject("Expected Income",expInc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[][] tableData() {
		int i;
		int size = 12;
		if (projection != null) {
			size += projection.size() + 1;
		}
		String[][] rtn = new String[size][3];
		for (i=0;i<size;i++) {
			rtn[i][0] = "";
			rtn[i][1] = "";
			rtn[i][2] = Integer.toString(UNDEF);
		}
		if (availableBalance != null) {
			rtn[0] = availableBalance.values(BALANCE);
		}
		if (expectedIncome != null) {
			rtn[2] = expectedIncome.values(EXPECTEDINCOME);
		}
		if (projectedBalance != null) {
			rtn[4] = projectedBalance.values(UNDEF);
		}
		if (total != null) {
			rtn[6] = total.values(BILLS);
		}
		if (end != null) {
			rtn[8] = end.values(UNDEF);
		}
		if (planned != null) {
			rtn[10] = planned.values(UNDEF);
		}
		if (projection != null) {
			i = 12;
			for (MoneyObject mo : projection) {
				rtn[i] = mo.values(UNDEF);
				i++;
			}
			rtn[i][0] = "  Per week for "+weeks+ " weeks";
		}
		
		return rtn;
	}
	
	public String[][] tableList(int type) {
		String[][] rtn = null;
		ArrayList<MoneyObject> list = null;
		
		switch (type) {
		case BALANCE:
			list = accounts;
			break;
		case EXPECTEDINCOME:
			list = income;
			break;
		case BILLS:
			list = bills;
			break;
		case PROJECTION:
			list = projection;
			break;
		}
		if (list != null) {
			rtn = new String[list.size()][2];
			int i = 0;
			for (MoneyObject mo : list) {
				rtn[i] = mo.values();
				i++;
			}
		}
		
		return rtn;
	}
	
	public Integer procectionWeeks() {
		return weeks;
	}
	
	public MoneyObject data(int type) {
		MoneyObject rtn = null;
		switch (type) {
		case BALANCE:
			rtn = availableBalance;
			break;
		case EXPECTEDINCOME:
			rtn = expectedIncome;
			break;
		case PROJECTEDBALANCE:
			rtn = projectedBalance;
			break;
		case BILLS:
			rtn = total;
			break;
		case FINALBALANCE:
			rtn = end;
			break;
		case PLANNEDBALANCE:
			rtn = planned;
			break;
		}
		
		return rtn;
	}
	
	public ArrayList<MoneyObject> list(int type) {
		ArrayList<MoneyObject> rtn = null;
		switch (type) {
		case BALANCE:
			rtn = accounts;
			break;
		case EXPECTEDINCOME:
			rtn = income;
			break;
		case BILLS:
			rtn = bills;
			break;
		case PROJECTION:
			rtn = projection;
			break;
		}
		
		return rtn;
	}

}
