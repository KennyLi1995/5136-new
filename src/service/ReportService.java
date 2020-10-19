package service;

import controller.MainController;
import model.*;
import utils.BakeryUtils;
import utils.FileUtils;
import utils.ReportUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportService {

    private OptionService optionService;

    public ReportService(OptionService optionService) {
        this.optionService = optionService;
    }

    public int chooseReport(){
        BakeryUtils.displayTrackBusinessOption();
        Scanner sc = new Scanner(System.in);
        String option = sc.nextLine();
        boolean isNumeric;
        do {
            isNumeric = true;
            if (option.length() == 0){
                System.out.println("The option cannot be blank");
                isNumeric = false;
            } else if (!BakeryUtils.isNumeric(option)){
                System.out.println("Invalid input");
                isNumeric = false;
            } else if (!option.equals("1") && !option.equals("2")){
                System.out.println("Invalid input");
                isNumeric = false;
            }
            if (!isNumeric){
                System.out.println("Please enter again: ");
                option = sc.nextLine();
            }
        } while (!isNumeric);
        return Integer.parseInt(option);
    }

    public int setLowInventoryBar(){
        System.out.println("Please enter the number to set the bar for low inventory items: ");
        Scanner sc = new Scanner(System.in);
        String barForLowInventory = sc.nextLine();
        boolean isNumeric;
        do {
            isNumeric = true;
            if (barForLowInventory.length() == 0){
                System.out.println("The standard of low inventory cannot be blank");
                isNumeric = false;
            } else if (!BakeryUtils.isNumeric(barForLowInventory)){
                System.out.println("The standard should be a positive number");
                isNumeric = false;
            }
            if (!isNumeric){
                System.out.println("Please enter again: ");
                barForLowInventory = sc.nextLine();
            } else {
                System.out.println("You set the bar for low inventory at " + barForLowInventory);
            }
        } while (!isNumeric);
        return Integer.parseInt(barForLowInventory);
        }

    public void generateReportOfLowInventory(Store store, List<FoodItem> foodList, int lowInventory){
        System.out.println("Food items low in inventory: ");
        for (int i = 0; i < store.getListOfInventory().size(); i++) {
            if (store.getListOfInventory().get(i).getQuantity() < lowInventory) {
                String itemNumber = store.getListOfInventory().get(i).getItemNumber();
                int itemQuantity = store.getListOfInventory().get(i).getQuantity();
                for (FoodItem item : foodList) {
                    if (itemNumber.equals(item.getItemNumber())) {
                        String itemName = item.getFoodItemName();
                        System.out.println("itemName:" + itemName + "   " + "quantity:" + itemQuantity);
                    }
                }
            }
        }
    }

    public void generateReportOfSoldFoodItem(){
        List<String> orders = FileUtils.readFile("order.csv");
        int totalNum = 0;
        for (String order: orders){
            String[] quantities = order.split(",");
            String[] quantity = quantities[4].split("\\|");
            for (String q : quantity){
                totalNum += Integer.parseInt(q);
            }
        }
        System.out.println("Total number of sold items in last month is: " + totalNum);
    }

    public void generateReport(User currentUser, BakerySystem bakerySystem, Store store){
        int choice = chooseReport();
        if (choice == 1){
            Report reportOfLowInventory = new Report(LocalDate.now(), "items low in inventory",
                    "inventory report", store);
            int lowInventory = setLowInventoryBar();
            ReportUtils.displayReportTitle(reportOfLowInventory, store);
            generateReportOfLowInventory(store, bakerySystem.getFoodList(), lowInventory);
        } else if (choice == 2){
            Report reportOfSoldFoodItem = new Report(LocalDate.now(), "Number of sold items in last month",
                    "business report", store);
            ReportUtils.displayReportTitle(reportOfSoldFoodItem, store);
            generateReportOfSoldFoodItem();
        }
        optionService.previousOption(currentUser, bakerySystem, u -> generateReport(currentUser, bakerySystem, store));
    }
}
