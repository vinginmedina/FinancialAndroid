package com.ving.financialstatement;

public class Catagory implements Comparable<Catagory> {
	
	private String parent;
	private String subcat;
	
	public Catagory () {
		this.parent = null;
		this.subcat = null;
	}
	
	public Catagory (String catagory) {
		String splitVals[] = catagory.split(":");
		if (splitVals[0] != null) {
			this.parent = splitVals[0].trim();
		}
		if ((splitVals.length > 1) && (splitVals[1] != null)) {
			this.subcat = splitVals[1].trim();
		} else {
			this.subcat = null;
		}
	}
	
	public String toString() {
		String rtn = "";
		if (this.parent != null) {
			rtn = this.parent;
		}
		if (this.subcat != null) {
			rtn += " : " + this.subcat;
		}
		
		return rtn;
	}
	
	public String parent() {
		return this.parent;
	}
	
	public String subcat() {
		return this.subcat;
	}
	
	public Boolean sameParent(Catagory cat) {
		Boolean rtn;
		
		if ((this.parent != null) && (cat.parent != null)) {
			rtn = this.parent.equals(cat.parent);
		} else {
			rtn = ((this.parent == null) && (cat.parent == null));
		}

		return rtn;
	}
	
	public Boolean equals(Catagory cat) {
		Boolean rtn = this.sameParent(cat);
		if (rtn) {
			if ((this.subcat != null) && (cat.subcat != null)) {
				rtn = this.subcat.equals(cat.subcat);
			} else {
				rtn = ((this.subcat == null) && (cat.subcat == null));
			}
		}
		
		return rtn;
	}
	
	public int compareTo(Catagory cat) {
		int rtn;
		
		if ((this.parent == null) && (cat.parent != null)) {
			rtn = -1;
		} else if ((this.parent != null) &&(cat.parent == null)) {
			rtn = 1;
		} else if (this.sameParent(cat)) {
			if ((this.subcat == null) && (cat.subcat != null)) {
				rtn = -1;
			} else if ((this.subcat != null) && (cat.subcat == null)) {
				rtn = 1;
			} else if ((this.subcat == null) && (cat.subcat == null)) {
				rtn = 0;
			} else {
				rtn = this.subcat.compareTo(cat.subcat);
			}
		} else {
			rtn = this.parent.compareTo(cat.parent);
		}
		
		return rtn;
	}

}
