package compute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FullSort {

        //将NUM设置为待排列数组的长度即实现全排列  
        private static int NUM = 10;
        
        private static Set<String> result = new HashSet<String>(); 
      
        /** 
         * 递归算法：将数据分为两部分，递归将数据从左侧移右侧实现全排列 
         * 
         * @param datas 
         * @param target 
         */  
        private static void sort(List<String> datas, List<String> target) {  
            if (target.size() == NUM) {
                Collections.sort(target);
//                System.out.println(target);
                StringBuffer b = new StringBuffer();
                for (String obj : target)  
                    b.append(obj);  
                result.add(b.toString());
                return;  
            }  
            for (int i = 0; i < datas.size(); i++) {  
                List<String> newDatas = new ArrayList<String>(datas);  
                List<String> newTarget = new ArrayList<String>(target);  
                newTarget.add(newDatas.get(i));  
                newDatas.remove(i);  
                sort(newDatas, newTarget);  
            }  
        }
        
        public static void main(String[] args) {  
            String[] datas = new String[] { "a", "b", "c", "d", "e", "f", "g","h","i","j","k","l","m","n","o","p","q"};  
            sort(Arrays.asList(datas), new ArrayList<String>());
            System.out.println(result.size());
            List<String> l = new ArrayList<String>();
            l.addAll(result);
            Collections.sort(l);
            for(String s : l){
                System.out.println(s);
            }
        }  
      
}
