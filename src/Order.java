import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;

public class Order
{
    private String orderDate;
    private String orderTime;
    private String orderStatus;
    private Map<FoodItem,Integer> quantity;
    private double totalCost;
    private String lastModifiedDate;
    private String lastModifiedTime;
    private String nameOfCustomer;
    private String lastModifiedBy;

    public Order(){
        this.orderDate = "";
        this.orderTime = "";
        this.orderStatus = "";
        this.quantity = new HashMap<FoodItem,Integer>();
        this.totalCost = 0;
        this.lastModifiedDate = "";
        this.lastModifiedTime = "";
        this.nameOfCustomer = "";
        this.lastModifiedBy = "";
    }

    public Order(String orderDate, String orderTime, String orderStatus, Map<FoodItem,Integer> quantity, double totalCost,
                 String lastModifiedDate, String lastModifiedTime, String nameOfCustomer, String lastModifiedBy) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedTime = lastModifiedTime;
        this.nameOfCustomer = nameOfCustomer;
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Map<FoodItem, Integer> getQuantity() {
        return quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public String getNameOfCustomer() {
        return nameOfCustomer;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setQuantity(Map<FoodItem, Integer> quantity) {
        this.quantity = quantity;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public void setNameOfCustomer(String nameOfCustomer) {
        this.nameOfCustomer = nameOfCustomer;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}


