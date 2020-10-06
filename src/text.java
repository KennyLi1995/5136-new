import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class text {
    public static void main(String[] args) {



        BakerySystem b = new BakerySystem();
        ArrayList<String> c = b.readFile("store.csv");
        int a = 6;
        for (String store : c)
        {
            String e = "3,925-5080 Varius St.,Belgrave,Victoria,2002,(06) 3123 8923";
            String[] d = e.split(",");
            String[] s = store.split(",");
            Store aStore = new Store();
            aStore.setStoreId(s[0]);
            aStore.setStoreAddress(s[1]);
            aStore.setStoreContactNumber(s[2]);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            System.out.println(date);
        }
    }

}
