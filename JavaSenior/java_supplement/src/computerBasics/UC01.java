package computerBasics;

/**
 * 区位码、国际码、机内码之间的转换
-------------
大，这个字的区内码是2083
（1）分解为20和83，分别转换为16进制后为14和53
     分别加20H
     最后转换为3473H（国际码）
（2）分解为34和73
     分别加80，变为114和153
     将前两位转换为16进制
     最后转换为B4F3（机内码）
-------------
 @author Alex
 @create 2023-07-30-12:42
 */
public class UC01 {
}
