import java.util.*;

public class Bakery {

    private ArrayList<Store> listOfStore;

    public Bakery()
    {
        listOfStore = new ArrayList<Store>();
    }

    public Bakery(ArrayList<Store> listOfStore)
    {
        this.listOfStore = listOfStore;
    }

    public ArrayList<Store> getListOfStore()
    {
        return listOfStore;
    }

    public void setListOfStore(ArrayList<Store> listOfStore)
    {
        this.listOfStore = listOfStore;
    }
}
