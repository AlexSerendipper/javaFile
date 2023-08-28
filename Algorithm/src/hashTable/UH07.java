package hashTable;

import java.util.HashMap;

/**
 * 赎金信：https://leetcode.cn/problems/ransom-note/
 *
 * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
 * 如果可以，返回 true ；否则返回 false 。
 * magazine 中的每个字符只能在 ransomNote 中使用一次。
 * ransomNote 和 magazine 由小写英文字母组成
 *
 @author Alex
 @create 2023-06-22-10:18
 */
public class UH07 {
    public static void main(String[] args) {
        String ransomNote = "bg";
        String magazine = "efjbdfbdgfjhhaiigfhbaejahgfbbgbjagbddfgdiaigdadhcfcj";
        boolean b = new UH07().canConstruct(ransomNote, magazine);
    }

    // map解法，magazine 中的每个字符只能在 ransomNote 中使用一次。肯定是要用map啦
    public boolean canConstruct(String ransomNote, String magazine) {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        // 统计magazine中的字符，并记录出现次数
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            map.put(c,map.getOrDefault(c,0)+1);
        }

        HashMap<Character, Integer> map2 = new HashMap<Character, Integer>();
        // 当ransomNote出现对应的字符，将字符存入map2，并且其value值-1
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            if(map2.containsKey(c)){
                map2.put(c,map2.getOrDefault(c,0)-1);
            }else {
                map2.put(c,map.getOrDefault(c,0)-1);
            }
        }

        if(map2.isEmpty()){
            return false;
        }

        // 只要Map2中出现负数，说明有问题了 返回false
        for (Integer v : map2.values()) {
            if(v<0){
                return false;
            }
        }

        return true;
    }

    // 答案：数组解法，这个写法确实高级了许多哈哈哈
    public boolean canConstruct2(String ransomNote, String magazine) {
        // shortcut
        if (ransomNote.length() > magazine.length()) {
            return false;
        }
        // 定义一个哈希映射数组
        int[] record = new int[26];

        // 遍历
        for(char c : magazine.toCharArray()){
            record[c - 'a'] += 1;
        }

        for(char c : ransomNote.toCharArray()){
            record[c - 'a'] -= 1;
        }

        // 如果数组中存在负数，说明ransomNote字符串总存在magazine中没有的字符
        for(int i : record){
            if(i < 0){
                return false;
            }
        }

        return true;
    }
}
