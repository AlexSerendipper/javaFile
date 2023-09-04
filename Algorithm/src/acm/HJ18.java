package acm;


import java.util.Scanner;

/**
 * HJ18 识别有效的IP地址和掩码并进行分类统计：https://www.nowcoder.com/practice/de538edd6f7e4bc3a5689723a7435682?tpId=37&tqId=21241&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D1%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 描述：请解析IP地址和对应的掩码，进行分类识别。要求按照A/B/C/D/E类地址归类，不合法的地址和掩码单独归类。
 * 所有的IP地址划分为 A,B,C,D,E五类
 * A类地址从1.0.0.0到126.255.255.255;
 * B类地址从128.0.0.0到191.255.255.255;
 * C类地址从192.0.0.0到223.255.255.255;
 * D类地址从224.0.0.0到239.255.255.255；
 * E类地址从240.0.0.0到255.255.255.255

 * 私网IP范围是：
 * 从10.0.0.0到10.255.255.255
 * 从172.16.0.0到172.31.255.255
 * 从192.168.0.0到192.168.255.255
 *
 * 子网掩码为二进制下前面是连续的1，然后全是0。（例如：255.255.255.32就是一个非法的掩码）
 * （注意二进制下全是1或者全是0均为非法子网掩码）
 *
 * 注意：
 * 1. 类似于【0.*.*.*】和【127.*.*.*】的IP地址不属于上述输入的任意一类，也不属于不合法ip地址，计数时请忽略
 * 2. 私有IP地址和A,B,C,D,E类地址是不冲突的
 *
 * 输入描述：
 * 多行字符串。每行一个IP地址和掩码，用~隔开。
 *
 * 输出描述：
 * 统计A、B、C、D、E、错误IP地址或错误掩码、私有IP的个数，之间以空格隔开
 *
 @author Alex
 @create 2023-07-18-9:39
 */
public class HJ18 {
    static int Aip=0;
    static int Bip=0;
    static int Cip=0;
    static int Dip=0;
    static int Eip=0;
    //错误IP地址或错误掩码
    static int errip=0;
    //私有IP的个数
    static int sip=0;


    // 正则
    // 0-255(二次和三次)
    static String r255_2 = "(\\.(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)){2}";
    static String r255_3 = "(\\.(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)){3}";
    // 1-126
    static String ARegxp = "([1-9]|([1-9]\\d)|1(([0-1]\\d)|(2[0-6])))" + r255_3;
    // 128-191
    static String BRegxp = "(1((2[8-9])|([3-8]\\d)|(9[0-1])))" + r255_3;
    // 192-223
    static String CRegxp = "((19[2-9])|(2(([0-1]\\d)|(2[0-3]))))" + r255_3;
    // 224-239
    static String DRegxp = "((22[4-9])|(23[0-9]))" + r255_3;
    // 240-255
    static String ERegxp = "((24\\d)|(25[0-5]))" + r255_3;
    //  私网IP范围是：
    //  10.0.0.0～10.255.255.255
    //  172.16.0.0～172.31.255.255
    //  192.168.0.0～192.168.255.255
    static String pReg1 = "10" + r255_3;
    static String pReg2 = "(172\\.((1[6-9])|(2\\d)|(3[0-1])))" + r255_2;
    static String pReg3 = "(192\\.168)" + r255_2;
    // 除外IP
    static String filterReg="(0|(127))" + r255_3;


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String s[] = in.nextLine().split("~");
            get(s[0],s[1]);
        }
//        System.out.println("over");
        // 统计A、B、C、D、E、错误IP地址或错误掩码、私有IP的个数，之间以空格隔开。
        System.out.print(Aip+" "+Bip+" "+Cip+" "+Dip+" "+Eip+" "+errip+" "+sip);
    }

    public static void get(String str, String yan) {
        boolean flag = true;
        flag = isMask(yan);  // 判断是否为掩码
        // 当ip为 0.*.*.*以及127.*.*.*时
        // 不属于任何类别。故不做处理直接return
        if (str.matches(filterReg)) {
            return;
        }

        if (str.matches(ARegxp) && flag) {
            Aip++;
        } else if (str.matches(BRegxp) && flag) {
            Bip++;
        } else if (str.matches(CRegxp) && flag) {
            Cip++;
        } else if (str.matches(DRegxp) && flag) {
            Dip++;
        } else if (str.matches(ERegxp) && flag) {
            Eip++;
        } else {
            errip++;
        }

        // 判断私网
        if ((str.matches(pReg1) || str.matches(pReg2) || str.matches(pReg3)) && flag) {
            sip++;
        }
    }



    public static boolean isMask(String s){
        String[] strs = s.split("\\.");
        String ss = "";
        // 判断是否为掩码，首先将其转换成二进制，然后判断长度必须为8或者为值为0（如果长度不为8就会出现1110001111这种错误的掩码）！！！！！！！！！
        for(String temp:strs){
            int i = Integer.parseInt(temp);
            temp = Integer.toBinaryString(i);
            if(temp.length()==8 || temp.equals("0")){
                ss += temp;
            }else{
                return false;
            }
        }
        // System.out.println(ss);
        // 255.255.255.255,0.0.0.0 为非法子网掩码。故如果掩码全为0，或者全为1，标示非法
        if(ss.matches("1+")){
            return false;
        } else if (ss.matches("0+")){
            return false;
        }else if(ss.matches("(1+0+)")){
            return true;
        }
        return false;
    }
}
