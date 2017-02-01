import java.util.Scanner;

public class AddressBookEntryApp {

	public static void main(String[] args) {
		System.out.println("Welcome to the Address Book application");
		Scanner sc = new Scanner(System.in);
		AddressBookEntry AE = new AddressBookEntry();
		int menuNumber = 0;
		String name = null;
		String emailAddress = null;
		String phoneNumber = null;
		 
		System.out.println("\n1 - List entries");
		System.out.println("2 - Add entry");
		System.out.println("3 - Exit");
		
		while(menuNumber != 3){

			System.out.print("\nEnter menu number: ");
			
			menuNumber = sc.nextInt();

			if(menuNumber == 1){
				System.out.println(AddressBookIO.getEntriesString());
			}
			if(menuNumber == 2){
				AE.setName(name);
				AE.setEmailAddress(emailAddress);
				AE.setPhoneNumber(phoneNumber);
				AddressBookIO.saveEntry(AE);
				System.out.println("\nThis entry has been saved.");
			}
			if(menuNumber == 3){
				System.out.println("\nGoodbye.");
			}
		}

	}

}
