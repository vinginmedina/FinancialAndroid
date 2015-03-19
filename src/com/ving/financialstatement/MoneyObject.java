package com.ving.financialstatement;

public class MoneyObject {
	private String name;
	private Float amount;
	
	public MoneyObject (String name, String amt) {
		this.name = name;
		amount = Float.parseFloat(amt.replaceAll("\\$", ""));
	}
	
	public MoneyObject (String[] values) {
		if (values.length > 1) {
			this.name = values[0];
			amount = Float.parseFloat(values[1].replaceAll("\\$",""));
		} else {
			this.name = null;
			amount = null;
		}
	}
	
	public MoneyObject (String name, Float amt) {
		this.name = name;
		amount = amt;
	}
	
	public String name() {
		return name;
	}
	
	public Float amount() {
		return amount;
	}
	
	public String toString() {
		return String.format("$%.2f",amount);
	}
	
	public String[] values() {
		String[] rtn = new String[2];
		rtn[0] = name;
		rtn[1] = String.format("$%.2f", amount);
		
		return rtn;
	}
	
	public String[] values(int tag) {
		String[] rtn = new String[3];
		rtn[0] = name;
		rtn[1] = String.format("$%.2f", amount);
		rtn[2] = Integer.toString(tag);
		
		return rtn;
	}
}
