package huai;

/**
 @author Alex
 @create 2024-04-29-14:07
 */
public class circle extends shape{
    int r;

    public void setr(int t){
        this.r = t;
    }

    public int getr(){
        return r;
    }

    @Override
    double getarea() {
        return Math.PI*r*r;
    }

    @Override
    double getp() {
        return 2*Math.PI*r;
    }
}
