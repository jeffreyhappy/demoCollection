package top.lixiangfei.www.leetcode;

import java.util.List;

public class PrintUtils {
    public static void print(List<?> list){
        for (int i = 0 ; i < list.size() ; i ++){
            Object oneItem = list.get(i);
            if (oneItem instanceof List){
                for (Object childItem : (List)oneItem){
                    System.out.print(childItem);
                    System.out.print(",");
                }
                System.out.println();
            }else {
                System.out.print(list.get(i));
                System.out.print(",");
            }
        }
        System.out.println();
    }

    public static <T> void print(T info){
        System.out.println(info);
    }

    public static void print(int[] info){
        for (int i = 0 ; i < info.length ; i ++){
            System.out.print(info[i]);
            System.out.print(",");
        }
        System.out.println();
    }


    public static void printNode(leetcodeNode.ListNode node){
        while (node != null){
            System.out.print(node.val);
            System.out.print(",");
            node = node.next;
        }
        System.out.println();
    }
}
