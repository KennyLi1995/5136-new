package service;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Bakery;
import model.BakerySystem;
import model.FoodItem;
import model.Inventory;
import model.Order;
import model.Store;
import model.User;
import utils.BakeryUtils;

public class BakeryService {

	public void addOrderInDB(Order aOrder, Bakery bakery) {
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
			out.write(bakery.getListOfStore().get(0).getStoreId() + "," + aOrder.getOrderId() + ","
					+ bakery.getListOfStore().get(0).getListOfUser().get(0).getUserId() + "," + n + "," + q + "," + p
					+ "," + aOrder.getTotalCost() + "," + aOrder.getOrderDate() + "," + aOrder.getOrderTime() + ","
					+ aOrder.getNameOfCustomer() + "," + aOrder.getOrderStatus() + "," + aOrder.getCustomerPhone() + ","
					+ aOrder.getLastModifiedBy() + "," + aOrder.getLastModifiedDate() + ","
					+ aOrder.getLastModifiedTime() + "\n");
			out.close();

		} catch (FileNotFoundException e) {
			System.out.println(fileName + " not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double calTotalCost(Order aOrder, BakerySystem bakerySystem) {
		double totalCost = 0;
		for (Map.Entry<FoodItem, Integer> entry : aOrder.getQuantity().entrySet()) {
			for (FoodItem foodItem : bakerySystem.getFoodList()) {
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

	public void createNewOrder(BakerySystem bakerySystem) {
		String itemName;
		int itemQuantity = 0;
		boolean nameCheck = true;
		boolean quantityCheck = true;
		Order aOrder = new Order();
		String option;
		FoodItem aFoodItem;
		do {
			BakeryUtils.displayBakeShop();
			displayCurrentItem(aOrder);
			System.out.println("            Total cost:" + aOrder.getTotalCost());
			ArrayList<String> inventories = readFile("inventory.csv");
			bakerySystem.getBakery().getListOfStore().get(0).getListOfInventory().clear();
			for (String inventory : inventories) {
				String[] i = inventory.split(",");
				Inventory aInventory = new Inventory(i[0], Integer.parseInt(i[1]), i[2]);
				bakerySystem.getBakery().getListOfStore().get(0).getListOfInventory().add(aInventory);
			}
			do {
				if (!nameCheck) {
					System.out.println("!Error: The item name is not valid!");
					System.out.println(
							"****************************************\n" + "Please try enter the item name again.");
				} else
					System.out.println("-- Please enter the item's name:");
				Scanner console = new Scanner(System.in);
				itemName = console.nextLine();
				ArrayList<FoodItem> foodItems = searchItems(itemName, bakerySystem);
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
					System.out.println(getFoodItemQuantity(itemNumber, bakerySystem.getBakery()));
					System.out.println(
							"****************************************\n" + "Please try enter the item quantity again.");
				} else
					System.out.println("-- Please enter the item's quantity:");
				Scanner console = new Scanner(System.in);
				String s = console.nextLine();
				quantityCheck = validateQuantityCheck(itemNumber, s, bakerySystem.getBakery());
				if (quantityCheck) {
					itemQuantity = Integer.parseInt(s);
				}
			} while (!quantityCheck);

			aOrder.getQuantity().put(aFoodItem, itemQuantity);
			aOrder.setTotalCost(calTotalCost(aOrder, bakerySystem));
			BakeryUtils.displayBakeShop();
			displayCurrentItem(aOrder);
			System.out.println("            Total cost:" + aOrder.getTotalCost());
			option = BakeryUtils.displayCreateOrderOption();
			if (option.equals("2")) {
				System.out.println("--  Enter the name of the item you want to cancel:");
				Scanner console = new Scanner(System.in);
				String name = console.nextLine();
				for (Map.Entry<FoodItem, Integer> entry : aOrder.getQuantity().entrySet()) {
					for (FoodItem foodItem : bakerySystem.getFoodList()) {
						if (name.equals(entry.getKey().getFoodItemName())) {
							aOrder.getQuantity().remove(foodItem);
							break;
						}
					}
				}
				aOrder.setTotalCost(calTotalCost(aOrder, bakerySystem));
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
			aOrder.setLastModifiedBy(String
					.valueOf(bakerySystem.getBakery().getListOfStore().get(0).getListOfUser().get(0).getUserId()));
			aOrder.setLastModifiedDate(date);
			aOrder.setLastModifiedTime(time);
			aOrder.setLastModifiedBy(String
					.valueOf(bakerySystem.getBakery().getListOfStore().get(0).getListOfUser().get(0).getUserId()));
			aOrder.setOrderStatus("Confirmed");
			aOrder.setNameOfCustomer(name);
			bakerySystem.getBakery().getListOfStore().get(0).getListOfOrder().add(aOrder);
			String orderId = createOrderId(aOrder);
			aOrder.setOrderId(orderId);
			addOrderInDB(aOrder, bakerySystem.getBakery());
			BakeryUtils.displayBakeShop();
			displayCurrentItem(aOrder);
			System.out.println("            Total cost:" + aOrder.getTotalCost());
			System.out.println("****************************************");
			System.out.println("Order id: " + orderId);
			System.out.println("Order date: " + date);
			System.out.println("Order time: " + time);
			System.out.println("Store id: " + bakerySystem.getBakery().getListOfStore().get(0).getStoreId());
			System.out.println("Employee id: "
					+ bakerySystem.getBakery().getListOfStore().get(0).getListOfUser().get(0).getUserId());
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

	public String getDate() {
		return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	public String getTime() {
		return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
	}

	public int getFoodItemQuantity(String itemNumber, Bakery bakery) {
		for (Inventory inventory : bakery.getListOfStore().get(0).getListOfInventory()) {
			if (itemNumber.equals(inventory.getItemNumber())) {
				return inventory.getQuantity();
			}
		}
		return 0;
	}

	public void initializeFoodItem(BakerySystem bakerySystem) {
		ArrayList<String> foodItems = readFile("foodItem.csv");
		for (String foodItem : foodItems) {
			String[] f = foodItem.split(",");
			FoodItem aFoodItem = new FoodItem();
			aFoodItem.setItemNumber(f[0]);
			aFoodItem.setFoodItemName(f[1]);
			aFoodItem.setFoodType(f[2]);
			aFoodItem.setCurrentPrice(Double.parseDouble(f[3]));
			bakerySystem.getFoodList().add(aFoodItem);
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

	public boolean login(BakerySystem bakerySystem) {
		Scanner console = new Scanner(System.in);
		System.out.println("--Enter your employee id or email:");
		System.out.println("--Enter your password:");
		String account = console.nextLine();
		System.out.println("--Enter your employee id or email:" + account);
		System.out.println("--Enter your password:");
		String password = console.nextLine();
		System.out.println("--Enter your employee id or email:" + account);
		System.out.println("--Enter your password:" + password);

		if (validateUser(account, password, bakerySystem))
			return true;
		else
			return false;

	}

	public void mainOption(User currentUser, BakerySystem bakerySystem) {
		boolean isContinue = true;
		Scanner console = new Scanner(System.in);
		String currentUserName = currentUser.getUserName();
		String currentUserType = currentUser.getUserType();
		while (isContinue) {
			BakeryUtils.displayHomeScreen(currentUserName, currentUserType);
			String selection = console.nextLine();
			if (currentUserType.equals("Staff") || currentUserType.equals("Manager")) {
				switch (selection) {
				case "1":
					createNewOrder(bakerySystem);
					break;
				case "0":
					isContinue = false;
					break;
				default:
					System.out.println("!Error: Your selection is not valid!");
					System.out.println(
							"****************************************\n" + "Please select the correct option.");
				}
			} else if (currentUserType.equals("Owner")) {
				switch (selection) {
				case "1":
					createNewOrder(bakerySystem);
					break;
				case "0":
					isContinue = false;
					break;
				default:
					System.out.println("!Error: Your selection is not valid!");
					System.out.println(
							"****************************************\n" + "Please select the correct option.");
				}
			} else {
				switch (selection) {
				case "0":
					isContinue = false;
					break;
				default:
					System.out.println("!Error: Your selection is not valid!");
					System.out.println(
							"****************************************\n" + "Please select the correct option.");
				}
			}
		}

	}

	public ArrayList<FoodItem> searchItems(String s, BakerySystem bakerySystem) {
		ArrayList<FoodItem> items = new ArrayList<>();
		s = s.strip();
		for (FoodItem aFoodItem : bakerySystem.getFoodList()) {
			if (aFoodItem.getFoodItemName().strip().contains(s)) {
				String itemNumber = aFoodItem.getItemNumber();
				for (Inventory inventory : bakerySystem.getBakery().getListOfStore().get(0).getListOfInventory()) {
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
				System.out
						.println("****************************************\n" + "Please try selecting a option again.");
				continue;
			}
			return selection;
		}
	}

	public void updateInventory(Order aOrder, Bakery bakery) {
		ArrayList<String> inventories = readFile("inventory.csv");
		bakery.getListOfStore().get(0).getListOfInventory().clear();
		for (String inventory : inventories) {
			String[] i = inventory.split(",");
			Inventory aInventory = new Inventory(i[0], Integer.parseInt(i[1]), i[2]);
			bakery.getListOfStore().get(0).getListOfInventory().add(aInventory);
		}
		for (Map.Entry<FoodItem, Integer> entry : aOrder.getQuantity().entrySet()) {
			for (Inventory aInventory : bakery.getListOfStore().get(0).getListOfInventory()) {
				if (aInventory.getItemNumber().equals(entry.getKey())) {
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
			out.write(bakery.getListOfStore().get(0).getStoreId() + "," + aOrder.getOrderId() + ","
					+ bakery.getListOfStore().get(0).getListOfUser().get(0).getUserId() + "," + n + "," + q + "," + p
					+ "," + aOrder.getTotalCost() + "," + aOrder.getOrderDate() + "," + aOrder.getOrderTime() + ","
					+ aOrder.getNameOfCustomer() + "," + aOrder.getOrderStatus() + "," + aOrder.getCustomerPhone() + ","
					+ aOrder.getLastModifiedBy() + "," + aOrder.getLastModifiedDate() + ","
					+ aOrder.getLastModifiedTime() + "\n");
			out.close();

		} catch (FileNotFoundException e) {
			System.out.println(fileName + " not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean validateQuantityCheck(String itemNumber, String s, Bakery bakery) {
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

	public boolean validateUser(String account, String password, BakerySystem bakerySystem) {
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
						bakerySystem.getBakery().setListOfStore(storeList);
						break;
					}
				}
				initializeFoodItem(bakerySystem);
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
