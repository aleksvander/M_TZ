package ru.novotelnovad.classes;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import ru.novotelnovad.entity.jpa.Task;
import ru.novotelnovad.entity.jpa.User;

/**
 * 
 * @author Новотельнов А.Д.
 * @param <E> - Task / User
 */
public class ConverterObjToStrJSON<E> {
	
	private Gson gson = new Gson();
	
	//Сущности разнотипны и генерик метод не подходит для его реализации
	//Рефлексия тоже не особо хороший помошник в этом плане
	/*public String convertObjToStr(List<E> lobj) {
		HashMap<String, String> lstr = new HashMap<String, String>();
		for (E elobj : lobj) {
			//lstr.add(elobj != null ? elobj.toString() : null);
			lstr.put("id", elobj.toString());
			lstr.put("name", elobj.toString());
		}
		return gson.toJson(lobj);
	}*/
	
	/**
	 * Метод конвертер для User
	 * @param lobj
	 * @return
	 */
	public String convertObjToStrUser(List<User> lobj) {
		HashMap<Object, Object> lstr = new HashMap<Object, Object>();
		for (User elobj : lobj) {
			lstr.put("id", (Integer)elobj.getId());
			lstr.put("name", elobj.getName());
		}
		return gson.toJson(lstr);
	}	
	
	/**
	 * Метод конвертер для Task
	 * @param lobj
	 * @return
	 */
	public String convertObjToStrTask(List<Task> lobj) {
		HashMap<Object, Object> lstr = new HashMap<Object, Object>();
		StringBuilder strColl = new StringBuilder("{\"task\":[");
		int i = 0;
		for (Task elobj : lobj) {
			
			if (i > 0)
				strColl.append(",");
				
			lstr.put("id", (Integer)elobj.getId());
			lstr.put("name", elobj.getName());
			lstr.put("lasttime", elobj.getLastTime());
			lstr.put("summtime", elobj.getSummTime());
			
			strColl.append(gson.toJson(lstr));
			i++;
		}
		strColl.append("]}");
		
		return strColl.toString();
	}
}
