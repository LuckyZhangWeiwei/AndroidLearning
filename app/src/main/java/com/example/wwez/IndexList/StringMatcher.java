package com.example.wwez.IndexList;

public class StringMatcher {
    //value  文本
    //keyword 索引列表中的字符串
    public static boolean match(String value, String keyword){
        if(value == null || keyword == null){
            return false;
        }
        if(keyword.length() > value.length()) {
            return false;
        }
        // i value 的指针
        //j keyword的指针
        int i = 0, j = 0;
        do{
            if(keyword.charAt(j) == value.charAt(i)) {
                i++;j++;
            } else if(j > 0){
                break;
            } else {
                i++;
            }
        } while (i < value.length() && j < keyword.length());
        return j == keyword.length();
    }
}
