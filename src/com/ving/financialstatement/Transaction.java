package com.ving.financialstatement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Transaction {

	public static final int CREDIT = 1;
	public static final int DEBIT = 2;
	private Date date;
	private Integer num;
	private Catagory catagory;
	private Integer type;
	private MoneyObject money;
	
	public Transaction() {
		date = null;
		num = null;
		catagory = null;
		type = null;
		money = null;
	}
	
	public Transaction(Date date, Integer num, String payee, String catagory, Integer type, Float amount) {
		this.date = date;
		this.num = num;
		this.catagory = new Catagory(catagory);
		this.type = type;
		this.money = new MoneyObject(payee,amount);
	}
	
	public Transaction (String data) {
		String values[] = data.split("\\^");
		if (values.length >= 6) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
			try {
				date = sdf.parse(values[1]);
			} catch (ParseException e) {
				date = null;
			}
			if (values[0].equals("")) {
				num = null;
			} else {
				num = Integer.parseInt(values[0]);
			}
			catagory = new Catagory(values[3]);
			if ((values[4].equals("")) && (! values[5].equals(""))) {
				type = CREDIT;
				money = new MoneyObject(values[2],values[5]);
			} else if ((! values[4].equals("")) && (values[5].equals(""))) {
				type = DEBIT;
				money = new MoneyObject(values[2],values[4]);
			}
		} else {
			date = null;
			num = null;
			catagory = null;
			type = null;
			money = null;
		}
	}
	
	public Integer number() {
		return num;
	}
	
	public Date date() {
		return date;
	}
	
	public String payee() {
		return money.name();
	}
	
	public Catagory catagory() {
		return catagory;
	}
	
	public Integer type() {
		return type;
	}
	
	public Float amount() {
		return money.amount();
	}
	
	public String[] printValues(Float startBalance) {
		Float balance = null;
		String rtn[] = new String[7];
		if (num != null) {
			rtn[0] = num.toString();
		} else {
			rtn[0] = "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		rtn[1] = sdf.format(date);
		rtn[2] = money.name();
		rtn[3] = catagory.toString();
		if (type == DEBIT) {
			rtn[4] = "$" + String.format("%.2f", money.amount());
			rtn[5] = "";
			balance = startBalance - money.amount();
		} else if (type == CREDIT) {
			rtn[4] = "";
			rtn[5] = "$" + String.format("%.2f", money.amount());
			balance = startBalance + money.amount();
		}
		if (balance != null) {
			rtn[6] = "$" + String.format("%.2f", balance);
		} else {
			rtn[6] = "";
		}
		
		return rtn;
	}
	
	public String[] printValues() {
		String rtn[] = new String[6];
		if (num != null) {
			rtn[0] = num.toString();
		} else {
			rtn[0] = "";
		}
		if (date != null) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		rtn[1] = sdf.format(date);
		} else {
			rtn[1] = "";
		}
		rtn[2] = money.name();
		rtn[3] = catagory.toString();
		if (type == DEBIT) {
			rtn[4] = "$" + String.format("%.2f", money.amount());
			rtn[5] = "";
		} else if (type == CREDIT) {
			rtn[4] = "";
			rtn[5] = "$" + String.format("%.2f", money.amount());
		}
		
		return rtn;
	}
	
	public static Comparator<Transaction> dateSort = new Comparator<Transaction>() {
		public int compare(Transaction t1, Transaction t2) {
			int rtn;
			rtn = t1.date.compareTo(t2.date);
			return rtn;
		}
	};
	
	public static Comparator<Transaction> catagorySort = new Comparator<Transaction>() {
		public int compare(Transaction t1, Transaction t2) {
			int rtn;
			rtn = t1.catagory.compareTo(t2.catagory);
			if (rtn == 0) {
				rtn = t1.date.compareTo(t2.date());
			}
			return rtn;
		}
	};
}
