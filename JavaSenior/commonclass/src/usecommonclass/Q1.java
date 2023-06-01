package usecommonclass;

/**
 * 判断下列程序的执行结构,流程图见Xmind
 * @author Alex
 * @create 2022-11-20-13:24
 */
public class Q1 {
        String str = new String("good");
        char[] ch = { 't', 'e', 's', 't' };
        public void change(String str, char ch[]) {
            str = "test ok";  // 体现了字符串的不可变性！
            ch[0] = 'b';
        }

        public static void main(String[] args) {
            Q1 ex = new Q1();
            ex.change(ex.str, ex.ch);  // 在change中重新存了一个str，但在change中的ch，仍然指向原来的ch
            System.out.print(ex.str + " and ");
            System.out.println(ex.ch);
        }
}
