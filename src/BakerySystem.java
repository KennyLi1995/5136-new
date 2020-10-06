import java.io.*;
import java.util.*;

public class BakerySystem
{
    private Bakery bakery;
    private ArrayList<FoodItem> foodList;

    public BakerySystem()
    {
        this.bakery = new Bakery();
        this.foodList = new ArrayList<FoodItem>();
    }

    public BakerySystem(Bakery bakery, ArrayList<FoodItem> foodList)
    {
        this.bakery = bakery;
        this.foodList = foodList;
    }

    public void createNewOrder()
    {
        String itemName = "";
        do {
            UserInterface.displayCreateNewOrder();
            Order aOrder = new Order();
            ArrayList<String> inventories = readFile("inventory.csv");
            for (String inventory : inventories) {
                String[] i = inventory.split(",");
                Inventory aInventory = new Inventory(i[0], Integer.parseInt(i[1]), i[2]);
                bakery.getListOfStore().get(0).getListOfInventory().add(aInventory);
            }
            System.out.println("-- Please enter the item's name:");
            Scanner console = new Scanner(System.in);
            itemName = console.nextLine();
        } while (!validateNameCheck(itemName));

    }

    public Bakery getBakery()
    {
        return bakery;
    }

    public ArrayList<FoodItem> getFoodList()
    {
        return foodList;
    }

    public boolean login()
    {
        Scanner console = new Scanner(System.in);
        System.out.println("--Enter your employee id or email:");
        System.out.println("--Enter your password:");
        String account = console.nextLine();
        UserInterface.displayLoginScreen();
        System.out.println("--Enter your employee id or email:" + account);
        System.out.println("--Enter your password:");
        String password = console.nextLine();
        UserInterface.displayLoginScreen();
        System.out.println("--Enter your employee id or email:" + account);
        System.out.println("--Enter your password:" + password);

        if (validateUser(account, password))
            return true;
        else
            return false;

    }

    public void logout()
    {
        String[] args = new String[1];
        UserInterface.main(args);
    }

    public void mainOption(String currentUserType)
    {
        if (currentUserType.equals("Manager"))
        {
            Scanner console = new Scanner(System.in);
            String option = console.nextLine();
            if (option.equals("1"))
                createNewOrder();
            else if (option.equals("7"))
                logout();
        }
    }

    public boolean validateNameCheck(String itemName)
    {
        String itemId = "";
        for (FoodItem foodItem : foodList)
        {
            if (foodItem.getFoodItemName().equals(itemName))
            {
                itemId = foodItem.getItemNumber();
                break;
            }
        }

        for (Inventory inventory : bakery.getListOfStore().get(0).getListOfInventory())
        {
            if (itemId.equals(inventory.getItemNumber()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean validateUser(String account, String password)
    {
        ArrayList<String> users = readFile("user.csv");
        for (String user : users)
        {
            String[] u = user.split(",");
            if ((Integer.parseInt(u[0]) == Integer.parseInt(account)|| u[2].equals(account))
                    && u[3].equals(password))
            {
                int userId = Integer.parseInt(u[0]);
                User aUser = new User(userId,u[1],u[2],u[3],u[4],u[5],u[6],u[7],u[8],u[9]);
                ArrayList<String> stores = readFile("store.csv");
                for (String store : stores)
                {

                    String[] s = store.split(",");
                    if (s[0].equals(u[9]))
                    {
                        Store aStore = new Store();
                        aStore.setStoreId(s[0]);
                        aStore.setStoreAddress(s[1]);
                        aStore.setStoreContactNumber(s[2]);
                        ArrayList<User> userList = new ArrayList<User>();
                        userList.add(aUser);
                        aStore.setListOfUser(userList);
                        ArrayList<Store> storeList = new ArrayList<Store>();
                        storeList.add(aStore);
                        bakery.setListOfStore(storeList);
                        break;
                    }
                }
                ArrayList<String> foodItems = readFile("foodItem.csv");
                for (String foodItem : foodItems)
                {
                    String[] f = foodItem.split(",");
                    FoodItem aFoodItem = new FoodItem();
                    aFoodItem.setItemNumber(f[0]);
                    aFoodItem.setFoodItemName(f[1]);
                    aFoodItem.setFoodType(f[2]);
                    aFoodItem.setCurrentPrice(Double.parseDouble(f[3]));
                    foodList.add(aFoodItem);
                }

                return true;
            }


        }
        return false;
    }

    public ArrayList<String> readFile(String fileName)
    {
        ArrayList<String> strings = new ArrayList<String>();
        try
        {
            FileReader inputFile = new FileReader(fileName);
            try
            {
                Scanner parser = new Scanner(inputFile);
                parser.nextLine();
                while (parser.hasNextLine())
                {
                    String line = parser.nextLine();
                    if (line.isEmpty())
                        continue;
                    strings.add(line);
                }
            }
            finally
            {
                inputFile.close();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(fileName + " not found");
        }
        catch (IOException e)
        {
            System.out.println("Unexpected I/O exception occur");
        }
        return strings;
    }

    public void setBakery(Bakery bakery)
    {
        this.bakery = bakery;
    }

    public void setFoodList(ArrayList<FoodItem> foodList)
    {
        this.foodList = foodList;
    }
}
