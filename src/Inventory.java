public class Inventory
{
    private String itemNumber;
    private int quantity;
    private String dateAdded;

    public Inventory()
    {
        this.itemNumber = "";
        this.quantity = 0;
        this.dateAdded = "";
    }

    public Inventory(String itemNumber, int quantity, String dateAdded)
    {
        this.itemNumber = itemNumber;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
