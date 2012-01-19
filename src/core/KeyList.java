package core;
import java.util.ArrayList;
public class KeyList{
    private ArrayList<Integer> keys;
    public KeyList(){}
    public int hashCode() {
        int hashcode=0;
       for(int i : keys){
            hashcode += i*31;
       }       
       return hashcode;
    }
    public boolean equals(Object o) {
        try{
            KeyList a = (KeyList)o; 
            if(this.size() != a.size()){
                return false;
            }
                for(int i=0;i<keys.size();i++){
                    if(this.get(i)!=a.get(i)){
                        return false;
                    }
                }
                return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public void add(int i){
        keys.add(i);
    }
    public void add(int k, int i){
        keys.add(k,i);
    }
    public int get(int k){
        return keys.get(k);
    }
    public int size(){
        return keys.size();
    }
}
