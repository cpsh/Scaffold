package com.cpsh.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 项目名称: dealer2<br/>
 * 类名称: com.jiuxing.utils.ArraysUtil<br/>
 * 创建人: Young<br/>
 * 创建时间: 2014-1-15 下午04:41:26<br/>
 *
 * 类描述：数组操作工具类<br/>
 *
 */
public class ArraysUtil {
    
    /**
     * 
     * 方法名称: ArraysUtil.union<br/>
     * 创建人: Young <br/>
     * 创建时间: 2014-1-15 下午04:52:25<br/>
     *
     * 方法描述：求两个数组的并集，利用set的元素唯一性
     * (set 不包含满足 e1.equals(e2) 的元素对 e1 和 e2，并且最多包含一个 null 元素)<br/>
     * 用法：支持基本类(其它自定义类需要重写相关equals方法)<br/>
     * 
     * @return Object[] -- <br/>
     *
     * @param arr1
     * @param arr2
     * @return<br/>
     *
     */
    public static <T> T[] union(T[] arr1, T[] arr2) {
        Set<T> set = new HashSet<T>();  
        for (T str : arr1) {  
            set.add(str);  
        }  
        for (T str : arr2) {  
            set.add(str);  
        }  
        return (T[])set.toArray();  
    }
    
    /**
     * 
     * 方法名称: ArraysUtil.noDuplicate<br/>
     * 创建人: Young <br/>
     * 创建时间: 2014-1-15 下午04:57:03<br/>
     *
     * 方法描述：一个数组去重,原理同上<br/>
     * 用法：支持基本类(其它自定义类需要重写相关equals方法)<br/>
     * 
     * @return Object[] -- <br/>
     *
     * @param arr1
     * @return<br/>
     *
     */
    public static <T> T[] noDuplicate(T[] arr1) {
        Set<T> set = new HashSet<T>();  
        for (T str : arr1) {  
            set.add(str);  
        }
        return (T[])set.toArray();  
    }
    
    /**
     * 
     * 方法名称: ArraysUtil.intersect<br/>
     * 创建人: Young <br/>
     * 创建时间: 2014-1-15 下午05:03:13<br/>
     *
     * 方法描述：求两个数据交集<br/>
     * 用法：支持基本类(其它自定义类需要重写相关equals方法)<br/>
     * 
     * @return Object[] -- <br/>
     *
     * @param arr1
     * @param arr2
     * @return<br/>
     *
     */
    public static Long[] intersect(Long[] arr1, Long[] arr2) {  
            Map<Long, Boolean> map = new HashMap<Long, Boolean>();  
            LinkedList<Long> list = new LinkedList<Long>();  
            for (Long str : arr1) {  
                if (!map.containsKey(str)) {  
                    map.put(str, Boolean.FALSE);  
                }  
            }  
            for (Long str : arr2) {  
                if (map.containsKey(str)) {  
                    map.put(str, Boolean.TRUE);  
                }  
            }  
            for (Entry<Long, Boolean> e : map.entrySet()) {  
                if (e.getValue().equals(Boolean.TRUE)) {  
                    list.add(e.getKey());  
                }  
            }  
            return list.toArray(new Long[0]);  
        }
    }

