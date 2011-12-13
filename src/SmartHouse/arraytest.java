import java.util.HashMap;
public class arraytest{
    public static HashMap<int[],Integer> map;
    public static void main(String[] args){
        map = new HashMap<int[],Integer>();
        map.put(new int[]{1,2},15);
        System.out.println(map.get(new int[]{1,2}));    
    }
}
