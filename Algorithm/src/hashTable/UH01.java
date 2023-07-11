package hashTable;

/**
 * 有效的字母异位词：https://leetcode.cn/problems/valid-anagram/
 * 如果在做面试题目的时候遇到需要判断一个元素是否出现过的场景也应该第一时间想到哈希法！
 *
 *
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 * 注意：(1)若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
 *      (2)s 和 t 仅包含小写字母
 @author Alex
 @create 2023-06-20-10:04
 */
public class UH01 {
    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println("new UH01().isAnagram(s, t) = " + new UH01().isAnagram(s, t));
    }


    // 方法：哈希数组
    // 定一个数组叫做record，大小为26 就可以了，初始化为0，因为字符a到字符z的ASCII也是26个连续的数值。
    // 需要把字符映射到数组也就是哈希表的索引下标上，因为字符a到字符z的ASCII是26个连续的数值，所以字符a映射为下标0，相应的字符z映射为下标25(所有字符串－a)。
    // 再遍历 字符串s的时候，对s中出现的字符映射哈希表索引上的数值再做+1的操作。做+1 操作即可，并不需要记住字符a的ASCII，只要求出一个相对数值就可以了。这样就将字符串s中字符出现的次数，统计出来了。
    // 同样在遍历字符串t的时候，对t中出现的字符映射哈希表索引上的数值再做-1的操作。
    public boolean isAnagram(String s, String t) {
        int[] record = new int[26];

        for (int i = 0; i < s.length(); i++) {
            record[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < t.length(); i++) {
            record[t.charAt(i) - 'a']--;
        }

        for (int i = 0; i < record.length; i++) {
            if(record[i]!=0){
                return false;
            }
        }
        return true;
    }
}
