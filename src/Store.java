import java.util.ArrayList;

public class Store {
    private String storeId;
    private String storeAddress;
    private String storeContactNumber;
    private ArrayList<User> listOfUser;
    private ArrayList<Order> listOfOrder;
    private ArrayList<Inventory> listOfInventory;

    public Store()
    {
        storeId = "";
        storeAddress = "";
        storeContactNumber = "";
        listOfUser = new ArrayList<User>();
        listOfOrder = new ArrayList<Order>();
        listOfInventory = new ArrayList<Inventory>();
    }

    public Store(String storeId, String storeAddress, String storeContactNumber, ArrayList<User> listOfUser,
                 ArrayList<Order> listOfOrder, ArrayList<Inventory> listOfInventory)
    {
        this.storeId = storeId;
        this.storeAddress = storeAddress;
        this.storeContactNumber = storeContactNumber;
        this.listOfUser = listOfUser;
        this.listOfOrder = listOfOrder;
        this.listOfInventory = listOfInventory;
    }

    public String getStoreId()
    {
        return storeId;
    }

    public String getStoreAddress()
    {
        return storeAddress;
    }

    public String getStoreContactNumber()
    {
        return storeContactNumber;
    }

    public ArrayList<User> getListOfUser()
    {
        return listOfUser;
    }

    public ArrayList<Order> getListOfOrder()
    {
        return listOfOrder;
    }

    public ArrayList<Inventory> getListOfInventory()
    {
        return listOfInventory;
    }

    public void setStoreId(String storeId)
    {
        this.storeId = storeId;
    }

    public void setStoreAddress(String storeAddress)
    {
        this.storeAddress = storeAddress;
    }

    public void setStoreContactNumber(String storeContactNumber)
    {
        this.storeContactNumber = storeContactNumber;
    }

    public void setListOfUser(ArrayList<User> listOfUser)
    {
        this.listOfUser = listOfUser;
    }

    public void setListOfOrder(ArrayList<Order> listOfOrder)
    {
        this.listOfOrder = listOfOrder;
    }

    public void setListOfInventory(ArrayList<Inventory> listOfInventory)
    {
        this.listOfInventory = listOfInventory;
    }
}
