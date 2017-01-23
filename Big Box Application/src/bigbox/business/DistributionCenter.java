package bigbox.business;

public class DistributionCenter extends Facility {
	private String dcNbr;
	private double expenses;
	
	public DistributionCenter(String dcNbr, double expenses, int facId, String facName,
							String facAddress, String facCity, String facState, String facZip) {
		this.dcNbr = dcNbr;
		this.expenses = expenses;
		setId(facId);
		setName(facName);
		setAddress(facAddress);
		setCity(facCity);
		setState(facState);
		setZipCode(facZip);
	}

	public String getDcNbr() {
		return dcNbr;
	}
	
	public void setDcNbr(String dcNbr) {
		this.dcNbr = dcNbr;
	}
	
	public double getExpenses() {
		return expenses;
	}
	
	public void setExpenses(double expenses) {
		this.expenses = expenses;
	}
}
