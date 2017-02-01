import java.util.Scanner;

public class AddressBookEntry {
	Scanner sc = new Scanner(System.in);
	private String name;
	private String emailAddress;
	private String phoneNumber;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		System.out.print("Enter name: ");
		name = sc.nextLine();
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		System.out.print("Enter email address: ");
		emailAddress = sc.next();
		this.emailAddress = emailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		System.out.print("Enter phone number: ");
		phoneNumber = sc.next();
		this.phoneNumber = phoneNumber;
	}
}
