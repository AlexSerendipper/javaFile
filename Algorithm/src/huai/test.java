package huai;

/**
 @author Alex
 @create 2024-04-29-14:12
 */
public class test {
    public static void main(String[] args) {
        circle c1 = new circle();
        rectangle r1 = new rectangle();

        c1.setr(9);
        r1.x=10;
        r1.y=15;

        System.out.println("圆形的面积为: "+c1.getarea());
        System.out.println("圆形的周长为: "+c1.getp());
        System.out.println("方形的面积为: "+r1.getarea());
        System.out.println("方形的周长为: "+r1.getp());
    }
}
