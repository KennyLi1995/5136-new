import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;


public class BakerySystem {
    private Bakery bakery;
    private ArrayList<FoodItem> foodList;

    public BakerySystem() {
        this.bakery = new Bakery();
        this.foodList = new ArrayList<FoodItem>();
    }

    public BakerySystem(Bakery bakery, ArrayList<FoodItem> foodList) {
        this.bakery = bakery;
        this.foodList = foodList;
    }


    public void addOrderInDB(Order aOrder) {
        BufferedWriter out = null;
        String fileName = "order.csv";
        try {
            ArrayList<String> itemName = new ArrayList<>();
            ArrayList<String> itemPrice = new ArrayList<>();
            ArrayList<String> itemQuantity = new ArrayList<>();
            for (Map.Entry<FoodItem, Integer> entry : aOrder.getQuantity().entrySet()) {
                itemName.add(entry.getKey().getFoodItemName());
                itemPrice.add(String.valueOf(entry.getKey().getCurrentPrice()));
                itemQuantity.add(String.valueOf(entry.getValue()));
            }
            String n = "";
            String q = "";
            String p = "";
            int length = itemName.size();
            for (int i = 0; i < length; i++) {
                if (i == length - 1) {
                    n += itemName.get(i);
                    q += itemQuantity.get(i);
                    p += itemPrice.get(i);
                } else {
                    n += itemName.get(i) + "|";
                    q += itemQuantity.get(i) + "|";
                    p += itemPrice.get(i) + "|";
                }
            }
            out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(bakery.getListOfStore().get(0).getStoreId() + "," + aOrder.getOrderId() + "," +
                    bakery.getListOfStore().get(0).getListOfUser().get(0).getUserId() + "," + n + "," + q + "," + p + "," +
                    aOrder.getTotalCost() + "," + aOrder.getOrderDate() + "," + aOrder.getOrderTime() + "," +
                    aOrder.getNameOfCustomer() + "," + aOrder.getOrderStatus() + "," + aOrder.getCustomerPhone() + "," +
                    aOrder.getLastModifiedBy() +
                    "," + aOrder.getLastModifiedDate() + "," + aOrder.getLastModifiedTime() + "\n");
            out.close();

        } catch (FileNotFoundException e) {
            System.out.println(fileName + " not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double calTotalCost(Order aOrder) {
        double totalCost = 0;
        for (Map.Entry<FoodItem, Integer> entry : aOrder.getQuantity().entrySet()) {
            for (FoodItem foodItem : foodList) {
                double currentPrice;
                if (foodItem.equals(entry.getKey())) {
                    currentPrice = foodItem.getCurrentPrice();
                    totalCost += entry.getValue() * currentPrice;
                    break;
                }
            }
        }
        return totalCost;
    }

    public void createNewOrder() {
        String itemName;
        int itemQuantity = 0;
        boolean nameCheck = true;
        boolean quantityCheck = true;
        Order aOrder = new Order();
        String option;
        FoodItem aFoodItem;
        do {
            UserInterface.displayBakeShop();
            displayCurrentItem(aOrder);
            System.out.println("            Total cost:" + aOrder.getTotalCost());
            ArrayList<String> inventories = readFile("inventory.csv");
            bakery.getListOfStore().get(0).getListOfInventory().clear();
            for (String inventory : inventories) {
                String[] i = inventory.split(",");
                Inventory aInventory = new Inventory(i[0], Integer.parseInt(i[1]), i[2]);
                bakery.getListOfStore().get(0).getListOfInventory().add(aInventory);
            }
            do {
                if (!nameCheck) {
                    System.out.println("!Error: The item name is not valid!");
                    System.out.println("****************************************\n" +
                            "Please try enter the item name again.");
                } else
                    System.out.println("-- Please enter the item's name:");
                Scanner console = new Scanner(System.in);
                itemName = console.nextLine();
                ArrayList<FoodItem> foodItems = searchItems(itemName);
                if (foodItems.size() == 0) {
                    nameCheck = false;
                    continue;
                }
                nameCheck = true;
                String selection = selectItem(foodItems);
                if (Integer.parseInt(selection) != (foodItems.size() + 1)) {
                    aFoodItem = foodItems.get(Integer.parseInt(selection) - 1);
                    break;
                }
            } while (true);
            String itemNumber = aFoodItem.getItemNumber();
            do {
                if (!quantityCheck) {
                    System.out.println("!Error: The item quantity is not valid!");
                    System.out.println("The current quantity in inventory for this item is:");
                    System.out.println(getFoodItemQuantity(itemNumber));
                    System.out.println("****************************************\n" +
                            "Please try enter the item quantity again.");
                } else
                    System.out.println("-- Please enter the item's quantity:");
                Scanner console = new Scanner(System.in);
                String s = console.nextLine();
                quantityCheck = validateQuantityCheck(itemNumber, s);
                if (quantityCheck) {
                    itemQuantity = Integer.parseInt(s);
                }
            } while (!quantityCheck);

            aOrder.getQuantity().put(aFoodItem, itemQuantity);
            aOrder.setTotalCost(calTotalCost(aOrder));
            UserInterface.displayBakeShop();
            displayCurrentItem(aOrder);
            System.out.println("            Total cost:" + aOrder.getTotalCost());
            option = UserInterface.displayCreateOrderOption();
            if (option.equals("2")) {
                System.out.println("--  Enter the name of the item you want to cancel:");
                Scanner console = new Scanner(System.in);
                String name = console.nextLine();
                for (Map.Entry<FoodItem, Integer> entry : aOrder.getQuantity().entrySet()) {
                    for (FoodItem foodItem : foodList) {
                        if (name.equals(entry.getKey().getFoodItemName())) {
                            aOrder.getQuantity().remove(foodItem);
                            break;
                        }
                    }
                }
                aOrder.setTotalCost(calTotalCost(aOrder));
            }

        } while (option.equals("1") || option.equals("2"));

        if (option.equals("3")) {
            Scanner console = new Scanner(System.in);
            System.out.println("--  Enter the name of the customer:");
            String name = console.nextLine();
            String date = getDate();
            String time = getTime();
            aOrder.setOrderDate(date);
            aOrder.setOrderTime(time);
            aOrder.setLastModifiedBy(String.valueOf(bakery.getListOfStore().get(0).getListOfUser().get(0).getUserId()));
            aOrder.setLastModifiedDate(date);
            aOrder.setLastModifiedTime(time);
            aOrder.setLastModifiedBy(String.valueOf(bakery.getListOfStore().get(0).getListOfUser().get(0).getUserId()));
            aOrder.setOrderStatus("Confirmed");
            aOrder.setNameOfCustomer(name);
            bakery.getListOfStore().get(0).getListOfOrder().add(aOrder);
            String orderId = createOrderId(aOrder);
            aOrder.setOrderId(orderId);
            addOrderInDB(aOrder);
            UserInterface.displayBakeShop();
            displayCurrentItem(aOrder);
            System.out.println("            Total cost:" + aOrder.getTotalCost());
            System.out.println("****************************************");
            System.out.println("Order id: " + orderId);
            System.out.println("Order date: " + date);
            System.out.println("Order time: " + time);
            System.out.println("Store id: " + bakery.getListOfStore().get(0).getStoreId());
            System.out.println("Employee id: " + bakery.getListOfStore().get(0).getListOfUser().get(0).getUserId());
            System.out.println("Customer name: " + name);
            System.out.println("****************************************");
            System.out.println("The order has been successfully created! ");
        }

    }

    public String createOrderId(Order aOrder) {
        ArrayList<String> orders = readFile("order.csv");
        int biggest = 0;
        for (String order : orders) {
            String[] o = order.split(",");
            String orderID = o[1];
            String[] subOrderId = orderID.split("-");
            int idNumber = Integer.parseInt(subOrderId[0]);
            if (idNumber > biggest)
                biggest = idNumber;
        }
        int currentIdNumber = biggest + 1;
        String str = String.format("%06d", currentIdNumber);
        String year = aOrder.getOrderDate().split("-")[0];
        String month = aOrder.getOrderDate().split("-")[1];
        String orderId = str + "-" + month + year;
        return orderId;
    }

    public void displayCurrentItem(Order aOrder) {
        System.out.println("Id    " + "Name               " + "Quantity " + "Cost");
        for (Map.Entry<FoodItem, Integer> entry : aOrder.getQuantity().entrySet()) {
            System.out.printf("%-6s", entry.getKey().getItemNumber());
            System.out.printf("%-19s", entry.getKey().getFoodItemName());
            System.out.printf("%-9s", entry.getValue());
            System.out.print(entry.getKey().getCurrentPrice());
            System.out.println();
        }
    }

    public Bakery getBakery() {
        return bakery;
    }

    public String getDate() {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sf.format(date);
        return date1;
    }

    public ArrayList<FoodItem> getFoodList() {
        return foodList;
    }

    public int getFoodItemQuantity(String itemNumber) {
        for (Inventory inventory : bakery.getListOfStore().get(0).getListOfInventory()) {
            if (itemNumber.equals(inventory.getItemNumber())) {
                return inventory.getQuantity();
            }
        }
        return 0;
    }

    public String getTime() {
        Date time = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        String time1 = sf.format(time);
        return time1;
    }

    public void initializeFoodItem() {
        ArrayList<String> foodItems = readFile("foodItem.csv");
        for (String foodItem : foodItems) {
            String[] f = foodItem.split(",");
            FoodItem aFoodItem = new FoodItem();
            aFoodItem.setItemNumber(f[0]);
            aFoodItem.setFoodItemName(f[1]);
            aFoodItem.setFoodType(f[2]);
            aFoodItem.setCurrentPrice(Double.parseDouble(f[3]));
            foodList.add(aFoodItem);
        }
    }

    public boolean isNumeric(String s) {
        for (int j = 0; j < s.length(); j++) {
            if (!(s.charAt(j) >= 48 && s.charAt(j) <= 57)) {
                return false;
            }
        }
        return true;
    }

    public boolean login() {
        Scanner console = new Scanner(System.in);
        System.out.println("--Enter your employee id or email:");
        System.out.println("--Enter your password:");
        String account = console.nextLine();
        System.out.println("--Enter your employee id or email:" + account);
        System.out.println("--Enter your password:");
        String password = console.nextLine();
        System.out.println("--Enter your employee id or email:" + account);
        System.out.println("--Enter your password:" + password);

        if (validateUser(account, password))
            return true;
        else
            return false;

    }


    public void mainOption(User currentUser) {
        boolean isContinue = true;
        Scanner console = new Scanner(System.in);
        String currentUserName = currentUser.getUserName();
        String currentUserType = currentUser.getUserType();
        while (isContinue) {
            UserInterface.displayHomeScreen(currentUserName, currentUserType);
            String selection = console.nextLine();
            if (currentUserType.equals("Staff") || currentUserType.equals("Manager")) {
                switch (selection) {
                    case "1" -> createNewOrder();
                    case "0" -> isContinue = false;
                    default -> {
                        System.out.println("!Error: Your selection is not valid!");
                        System.out.println("****************************************\n" +
                                "Please select the correct option.");
                    }
                }
            } else if (currentUserType.equals("Owner")) {
                switch (selection) {
                    case "1" -> createNewOrder();
                    case "0" -> isContinue = false;
                    default -> {
                        System.out.println("!Error: Your selection is not valid!");
                        System.out.println("****************************************\n" +
                                "Please select the correct option.");
                    }
                }
            } else {
                switch (selection) {
                    case "0" -> isContinue = false;
                    default -> {
                        System.out.println("!Error: Your selection is not valid!");
                        System.out.println("****************************************\n" +
                                "Please select the correct option.");
                    }
                }
            }
        }

    }

    public ArrayList<FoodItem> searchItems(String s) {
        ArrayList<FoodItem> items = new ArrayList<>();
        s = s.strip();
        for (FoodItem aFoodItem : foodList) {
            if (aFoodItem.getFoodItemName().strip().contains(s)) {
                String itemNumber = aFoodItem.getItemNumber();
                for (Inventory inventory : bakery.getListOfStore().get(0).getListOfInventory()) {
                    if (itemNumber.equals(inventory.getItemNumber()))
                        items.add(aFoodItem);
                }
            }
        }
        return items;
    }

    public String selectItem(ArrayList<FoodItem> foodItems) {
        String selection;
        while (true) {
            System.out.println("-- Please select the item you want:");
            int index = 1;
            for (FoodItem foodItem : foodItems) {
                System.out.println(index + ". " + foodItem.getFoodItemName());
                index += 1;
            }
            System.out.println(index + ". " + "re-enter the food item name");
            Scanner console = new Scanner(System.in);
            selection = console.nextLine();
            selection = selection.strip();
            if (isNumeric(selection) && Integer.parseInt(selection) > index) {
                System.out.println("!Error: Your selection is not valid!");
                System.out.println("****************************************\n" +
                        "Please try selecting a option again.");
                continue;
            }
            return selection;
        }
    }

    public void setBakery(Bakery bakery) {
        this.bakery = bakery;
    }

    public void setFoodList(ArrayList<FoodItem> foodList) {
        this.foodList = foodList;
    }

    public void updateInventory(Order aOrder) {
        ArrayList<String> inventories = readFile("inventory.csv");
        bakery.getListOfStore().get(0).getListOfInventory().clear();
        for (String inventory : inventories) {
            String[] i = inventory.split(",");
            Inventory aInventory = new Inventory(i[0], Integer.parseInt(i[1]), i[2]);
            bakery.getListOfStore().get(0).getListOfInventory().add(aInventory);
        }
        for (Map.Entry<FoodItem, Integer> entry : aOrder.getQuantity().entrySet()) {
            for (Inventory aInventory : bakery.getListOfStore().get(0).getListOfInventory()) {
                if (aInventory.getItemNumber().equals(entry.getKey())){
                    int originQuantity = aInventory.getQuantity();
                    int finalQuantity = originQuantity - entry.getValue();
                    aInventory.setQuantity(finalQuantity);
                }
            }
        }

        BufferedWriter out = null;
        String fileName = "inventory.csv";
        try {
            ArrayList<String> itemName = new ArrayList<>();
            ArrayList<String> itemPrice = new ArrayList<>();
            ArrayList<String> itemQuantity = new ArrayList<>();
            for (Map.Entry<FoodItem, Integer> entry : aOrder.getQuantity().entrySet()) {
                itemName.add(entry.getKey().getFoodItemName());
                itemPrice.add(String.valueOf(entry.getKey().getCurrentPrice()));
                itemQuantity.add(String.valueOf(entry.getValue()));
            }
            String n = "";
            String q = "";
            String p = "";
            int length = itemName.size();
            for (int i = 0; i < length; i++) {
                if (i == length - 1) {
                    n += itemName.get(i);
                    q += itemQuantity.get(i);
                    p += itemPrice.get(i);
                } else {
                    n += itemName.get(i) + "|";
                    q += itemQuantity.get(i) + "|";
                    p += itemPrice.get(i) + "|";
                }
            }
            out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(bakery.getListOfStore().get(0).getStoreId() + "," + aOrder.getOrderId() + "," +
                    bakery.getListOfStore().get(0).getListOfUser().get(0).getUserId() + "," + n + "," + q + "," + p + "," +
                    aOrder.getTotalCost() + "," + aOrder.getOrderDate() + "," + aOrder.getOrderTime() + "," +
                    aOrder.getNameOfCustomer() + "," + aOrder.getOrderStatus() + "," + aOrder.getCustomerPhone() + "," +
                    aOrder.getLastModifiedBy() +
                    "," + aOrder.getLastModifiedDate() + "," + aOrder.getLastModifiedTime() + "\n");
            out.close();

        } catch (FileNotFoundException e) {
            System.out.println(fileName + " not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validateQuantityCheck(String itemNumber, String s) {
        boolean check = false;
        check = isNumeric(s);
        if (check) {
            if (Integer.parseInt(s) <= 0)
                check = false;
        } else
            return false;
        for (Inventory inventory : bakery.getListOfStore().get(0).getListOfInventory()) {
            if (itemNumber.equals(inventory.getItemNumber())) {
                int currentNumber = inventory.getQuantity();
                if (Integer.parseInt(s) <= currentNumber)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    public boolean validateUser(String account, String password) {
        ArrayList<String> users = readFile("user.csv");
        for (String user : users) {
            String[] u = user.split(",");
            if ((Integer.parseInt(u[0]) == Integer.parseInt(account) || u[2].equals(account))
                    && u[3].equals(password)) {
                int userId = Integer.parseInt(u[0]);
                User aUser = new User(userId, u[1], u[2], u[3], u[4], u[5], u[6], u[7], u[8]);
                ArrayList<String> stores = readFile("store.csv");
                for (String store : stores) {

                    String[] s = store.split(",");
                    if (s[0].equals(u[9])) {
                        Store aStore = new Store();
                        aStore.setStoreId(s[0]);
                        aStore.setStoreAddress(s[1]);
                        aStore.setStoreContactNumber(s[2]);
                        ArrayList<User> userList = new ArrayList<>();
                        userList.add(aUser);
                        aStore.setListOfUser(userList);
                        ArrayList<Store> storeList = new ArrayList<>();
                        storeList.add(aStore);
                        bakery.setListOfStore(storeList);
                        break;
                    }
                }
                initializeFoodItem();
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> readFile(String fileName) {
        ArrayList<String> strings = new ArrayList<String>();
        try {
            FileReader inputFile = new FileReader(fileName);
            try {
                Scanner parser = new Scanner(inputFile);
                parser.nextLine();
                while (parser.hasNextLine()) {
                    String line = parser.nextLine();
                    if (line.isEmpty())
                        continue;
                    strings.add(line);
                }
            } finally {
                inputFile.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(fileName + " not found");
        } catch (IOException e) {
            System.out.println("Unexpected I/O exception occur");
        }
        return strings;
    }

}
