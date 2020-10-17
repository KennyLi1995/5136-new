import java.util.Scanner;

public class UserInterface {

	public static void displayBakeShop() {
		System.out.println("=======================================");
		System.out.println("|                                     |");
		System.out.println("|               Bake Shop             |");
		System.out.println("|                                     |");
		System.out.println("=======================================");
	}

	public static String displayCreateOrderOption() {
		String option;
		boolean optionCheck = true;
		do {
			if (!optionCheck) {
				System.out.println("!Error: Your selection is not valid!");
			}
			System.out.println("****************************************\n"
					+ "-- Please select one option by entering the number:\n" + "1. Continue entering items\n"
					+ "2. Cancel items\n" + "3. Confirm\n" + "4. Back to home screen");
			Scanner console = new Scanner(System.in);
			option = console.nextLine();
			optionCheck = option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4");
		} while (!optionCheck);
		return option;
	}

	public static void displayHomeScreen(String userName, String userType) {
		displayBakeShop();
		System.out.println(" *****Welcome, " + userName + "(" + userType + ")*****");
		switch (userType) {
		case "Staff" -> {
			System.out.println("-- Please select one option by entering the number:");
			System.out.println("1. Create new order");
			System.out.println("2. Create new special order for roast coffee bean");
			System.out.println("3. Manage order");
			System.out.println("4. Manage inventory");
			System.out.println("5. View my profile");
			System.out.println("0. Logout");
		}
		case "Manager" -> {
			System.out.println("-- Please select one option by entering the number:");
			System.out.println("1. Create new order");
			System.out.println("2. Create new special order for roast coffee bean");
			System.out.println("3. Manage order");
			System.out.println("4. Manage inventory");
			System.out.println("5. Manage all advance order");
			System.out.println("6. View my profile");
			System.out.println("0. Logout");
		}
		case "Owner" -> {
			System.out.println("-- Please select one option by entering the number:");
			System.out.println("1. Create new order");
			System.out.println("2. Create new special order for roast coffee bean");
			System.out.println("3. Manage order");
			System.out.println("4. Manage inventory");
			System.out.println("5. Manage all advance order");
			System.out.println("6. Manage my stores");
			System.out.println("7. Manage employee");
			System.out.println("8. Track my business");
			System.out.println("9. View my profile");
			System.out.println("0. Logout");
		}
		default -> {
			System.out.println("-- Please select one option by entering the number:");
			System.out.println("!Error: User Type is not valid!");
			System.out.println("0. Logout");
		}
		}

	}

	public static void displayLoginError() {
		System.out.println("!Error: User is not valid!");
		System.out.println("****************************************\n"
				+ "Please try login again.or contact the owner to reset the password.");
	}

	public static void main(String args[]) {
		while (true) {
			BakerySystem bakerySystem = new BakerySystem();
			boolean check;
			displayBakeShop();
			System.out.println("***Please enter your user credential***");
			check = bakerySystem.login();
			while (!check) {
				displayLoginError();
				check = bakerySystem.login();
			}
			User currentUser = bakerySystem.getBakery().getListOfStore().get(0).getListOfUser().get(0);
			bakerySystem.mainOption(currentUser);
		}

	}

}
