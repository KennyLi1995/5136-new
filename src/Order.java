import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order
{
    private String orderDate;
    private String orderTime;
    private String orderStatus;
    private ArrayList<FoodItem> listOfItem;
    private ArrayList<Integer> listOfQuantity;
    private ArrayList<Double> listOfPrice;
    private double totalCost;
    private String lastModifiedDate;
    private String lastModifiedTime;
    private String nameOfCustomer;
    private String lastModifiedBy;

    public Order()
    {
        this.orderDate = "";
        this.orderTime = "";
        this.orderStatus = "";
        this.listOfItem = new ArrayList<FoodItem>();
        this.listOfQuantity = new ArrayList<Integer>();
        this.listOfPrice = new ArrayList<Double>();
        this.totalCost = 0;
        this.lastModifiedDate = "";
        this.lastModifiedTime = "";
        this.nameOfCustomer = "";
        this.lastModifiedBy = "";
    }

    public Order(String orderDate, String orderTime, String orderStatus, ArrayList<FoodItem> listOfItem,
                 ArrayList<Integer> listOfQuantity, ArrayList<Double> listOfPrice,
                 double totalCost, String lastModifiedDate, String lastModifiedTime,
                 String nameOfCustomer, String lastModifiedBy)
    {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.listOfItem = listOfItem;
        this.listOfQuantity = listOfQuantity;
        this.listOfPrice = listOfPrice;
        this.totalCost = totalCost;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedTime = lastModifiedTime;
        this.nameOfCustomer = nameOfCustomer;
        this.lastModifiedBy = lastModifiedBy;
    }
}


