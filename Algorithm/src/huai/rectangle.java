package huai;

/**
 @author Alex
 @create 2024-04-29-14:11
 */
public class rectangle extends shape{
    int x;
    int y;

    double getarea() {
        return x*y;
    }

    double getp() {
        return 2*x+2*y;
    }
}
