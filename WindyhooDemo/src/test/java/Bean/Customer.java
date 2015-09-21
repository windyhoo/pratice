package Bean;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
  
public class Customer 
{
    private List<Object> lists;
    private Set<Object> sets;
    private Map<Object, Object> maps;
    private Properties pros;
    
	public List<Object> getLists() {
		return lists;
	}
	
	public void setLists(List<Object> lists) {
		this.lists = lists;
	}
	
	public Set<Object> getSets() {
		return sets;
	}
	
	public void setSets(Set<Object> sets) {
		this.sets = sets;
	}
	
	public Map<Object, Object> getMaps() {
		return maps;
	}
	
	public void setMaps(Map<Object, Object> maps) {
		this.maps = maps;
	}
	
	public Properties getPros() {
		return pros;
	}
	
	public void setPros(Properties pros) {
		this.pros = pros;
	}
  
    //...
    public  String   toString(){  
        System.out.print("[");  
        for(int i=0;i<lists.size();i++){  
            System.out.print(lists.get(i));  
        }
        System.out.println("]");  
          
        System.out.print("[");  
            System.out.print(sets.toString());  
        System.out.println("]");  
          
        System.out.print("[");  

        Set<Object> set2 =    maps.keySet();  
       // System.out.println(set2.size());  
        for(Iterator<Object>  i =set2.iterator();i.hasNext();){  
            String next = i.next().toString();  
            System.out.print(next+":"+maps.get(next).toString());  
        }
        System.out.println("]");  
          
        System.out.println("["+pros+"]");  
        return  "";  
    }  
}