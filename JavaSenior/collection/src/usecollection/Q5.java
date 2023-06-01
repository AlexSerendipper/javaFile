package usecollection;

import org.junit.Test;

import java.util.*;

/**
 @author Alex
 @create 2022-12-11-21:30
 */
public class Q5 {
    // 一、从键盘随机输入10个整数保存到List中，并按倒序、从大到小的顺序显示出来
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            System.out.println("请输入整数：");
            int num = scanner.nextInt();
            arrayList.add(num);
        }
        Collections.sort(arrayList);
        Collections.reverse(arrayList);
        System.out.println(arrayList);
    }

    //二、 请把学生名与考试分数录入到集合中，并按分数显示前三名成绩学员的名字
    @Test
    public void test() {
        TreeSet treeSet = new TreeSet();
        treeSet.add(new Student("zzj",99));
        treeSet.add(new Student("hyq",69));
        treeSet.add(new Student("lzy",109));
        treeSet.add(new Student("hjy",19));
        treeSet.add(new Student("zrs",79));
        Iterator iterator = treeSet.iterator();
        for (int i = 0; i < 3; i++) {
            Object next = iterator.next();
            Student next1 = (Student) next;
            System.out.println(next1.name);
        }
    }
    // 三、对一个Java源文件中的关键字进行计数。
    @Test
    public void test2(){
//        File file = new File("Test.java");  // 读取一个源文件
//        思路如下：Java源文件中的每一个单词，需要确定该单词是否是一个关键字。为
//        了高效处理这个问题，将所有的关键字保存在一个HashSet keyWord中。用contains()来测试。
//        Scanner scanner = new Scanner(file);
//        while(scanner.hasNext()){
//            String word = scanner.next();  // 自动根据空格截取为一个个单词了
//
//            System.out.println(word);
    }


    class Student implements Comparable{
        String name;
        int score;

        public Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            if(o instanceof Student){
                Student o1 = (Student) o;
                return -Integer.compare(this.score,o1.score);
            }else {
                throw new RuntimeException("输入用户数据异常");
            }
        }
    }
}
