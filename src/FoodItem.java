public class FoodItem
{
    private String itemNumber;
    private String foodItemName;
    private String foodType;
    private double currentPrice;

    public FoodItem(String itemNumber, String foodItemName, String foodType, double currentPrice)
    {
        this.itemNumber = itemNumber;
        this.foodItemName = foodItemName;
        this.foodType = foodType;
        this.currentPrice = currentPrice;
    }

    public FoodItem()
    {
        this.itemNumber = "";
        this.foodItemName = "";
        this.foodType = "";
        this.currentPrice = 0.00;
    }

    public String getItemNumber()
    {
        return itemNumber;
    }

    public String getFoodItemName()
    {
        return foodItemName;
    }

    public String getFoodType()
    {
        return foodType;
    }

    public double getCurrentPrice()
    {
        return currentPrice;
    }

    public void setItemNumber(String itemNumber)
    {
        this.itemNumber = itemNumber;
    }

    public void setFoodItemName(String foodItemName)
    {
        this.foodItemName = foodItemName;
    }

    public void setFoodType(String foodType)
    {
        this.foodType = foodType;
    }

    public void setCurrentPrice(double currentPrice)
    {
        this.currentPrice = currentPrice;
    }
}
