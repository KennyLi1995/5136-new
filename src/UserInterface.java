public class UserInterface {

    public static void displayCreateNewOrder()
    {
        System.out.println("=======================================");
        System.out.println("|                                     |");
        System.out.println("|               Bake Shop             |");
        System.out.println("|                                     |");
        System.out.println("=======================================");
        System.out.println("Id        " + "Name       " + "Quantity     " + "Cost      ");
    }

    public static void displayCreateNewOrderOption()
    {
        System.out.println("****************************************\n" +
                "-- Please select one option by entering the number:\n" +
                "1. Continue entering items\n" +
                "2. Cancel the last item\n" +
                "3. Confirm\n" +
                "4. Back to home screen");
    }

    public static void displayHomeScreen(String userName, String userType)
    {
        System.out.println("=======================================");
        System.out.println("|                                     |");
        System.out.println("|               Bake Shop             |");
        System.out.println("|                                     |");
        System.out.println("=======================================");
        System.out.println(" *****Welcome, " + userName + "(" + userType + ")*****");
        if (userType.equals("Staff"))
        {
            System.out.println("-- Please select one option by entering the number:");
            System.out.println("1. Create new order");
            System.out.println("2. Create new special order for roast coffee bean");
            System.out.println("3. Manage order");
            System.out.println("4. Manage inventory");
            System.out.println("5. View my profile");
            System.out.println("6. Logout");
        }
        else if (userType.equals("Manager"))
        {
            System.out.println("-- Please select one option by entering the number:");
            System.out.println("1. Create new order");
            System.out.println("2. Create new special order for roast coffee bean");
            System.out.println("3. Manage order");
            System.out.println("4. Manage inventory");
            System.out.println("5. Manage all advance order");
            System.out.println("6. View my profile");
            System.out.println("7. Logout");
        }
        else if (userType.equals("Owner"))
        {
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
        else
            {
                System.out.println("-- Please select one option by entering the number:");
                System.out.println("!Error: User Type is not valid!");
                System.out.println("1. Logout");
            }

    }

    public static void displayLoginScreen()
    {
        System.out.println("=======================================");
        System.out.println("|                                     |");
        System.out.println("|               Bake Shop             |");
        System.out.println("|                                     |");
        System.out.println("=======================================");
        System.out.println("***Please enter your user credential***");
    }

    public static void displayLoginError()
    {
        System.out.println("!Error: User is not valid!");
        System.out.println("****************************************\n" +
                "Please try login again.or contact the owner to reset the password.");
    }

    public static void main(String[] args)
    {
        BakerySystem bakerySystem = new BakerySystem();
        boolean check;
        displayLoginScreen();
        check = bakerySystem.login();
        while (!check)
        {
            displayLoginError();
            check = bakerySystem.login();
        }
        String currentUser = bakerySystem.getBakery().getListOfStore().get(0).getListOfUser().get(0).getUserName();
        String currentUserType = bakerySystem.getBakery().getListOfStore().get(0).getListOfUser().get(0).getUserType();
        displayHomeScreen(currentUser, currentUserType);
        bakerySystem.mainOption(currentUserType);
        {

        }
    }


}
