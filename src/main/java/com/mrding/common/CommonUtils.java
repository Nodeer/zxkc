package com.mrding.common;

import java.util.*;
import java.util.Map.Entry;

import com.mrding.zxkc.dm.CkDmBean;

/**
 * 静态方法工具
 * @author Lenovo
 *
 */
public class CommonUtils {
	
	/**
	 * 判断字符串是否为空（“”或null）
	 * @param username
	 * @return
	 */
	public static boolean isNotBlank(Object obj) {
		return obj != null && !obj.equals("");
	}
	
	/**
	 * 对list判空
	 * @param list
	 * @return
	 */
	public static boolean listIsNotBlank(List<Object[]> list) {
	    return list != null && list.size() > 0;
	}

	/**
	 * 将list转换为下拉单数据
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> convertSelectData(
		List<Object[]> list, String valueKey, String displayKey) {
	    List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
	    if (listIsNotBlank(list)) {
			for (Object[] objs : list) {
			    Map<String, Object> map = new HashMap<String, Object>();
			    map.put(valueKey, objs[0]); 
			    map.put(displayKey, objs[1]); 
			    rtnList.add(map);
			}
	    }
	    return rtnList;
	}

	public static List<Map<String, String>> createSelect(String key, String value) {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		Set<Entry<String, String>> entrySet = CkDmBean.map.entrySet();
		Iterator it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) it.next();
			Map<String, String> eMap = new HashMap<String, String>();
			eMap.put(key, entry.getKey());
			eMap.put(value, entry.getValue());
			rtnList.add(eMap);
		}
		return rtnList;
	}

	/**
	 * 对map判空
	 * @param map
	 * @return
	 */
	public static boolean mapIsBlank(Map<String, String> map) {
		return map == null || map.size() == 0;
	}

	/**
	 * 将list转换成map
	 * @param list
	 * @return
	 */
	public static Map<String, String> listToMap(List<Object[]> list) {
		Map<String, String> map = new HashMap<String, String>();
		if (CommonUtils.listIsNotBlank(list)) {
			for (Object[] objs : list) {
				map.put((String) objs[0], (String) objs[1]);
			}
		}
		return map;
	}
	
}
