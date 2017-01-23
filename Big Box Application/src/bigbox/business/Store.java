package bigbox.business;

public class Store extends Facility{
	private String storeNumber;
	private String divisionNumber;
	private double sales;
	
	public Store(int inID, String inDivNbr, String inStoreNbr, double inSales, String inFacName, String inFacAddress, 
			String inFacCity, String inFacState, String inFacZip){
		setId(inID);
		setDivisionNumber(inDivNbr);
		setStoreNumber(inStoreNbr);
		setSales(inSales);
		setName(inFacName);
		setAddress(inFacAddress);
		setCity(inFacCity);
		setState(inFacState);
		setZipCode(inFacZip);
	}
	public String getStoreNbr() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getDivNbr() {
		return divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}
	public String toString(){
		return "[Store: Store # = " + getStoreNbr() + ", Div # = " + getDivNbr() + ", Sales = " + getSales() + "]"
				+ super.toString();
	}
}
