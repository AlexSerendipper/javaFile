package acm;

/**
 @author Alex
 @create 2023-08-28-22:33
 */
public class test {
    public static void main(String[] args) {

        String s1 = "waterbottle";
        String s2 = "erbottlewat";
        System.out.println(checkReverseEqual(s1,s2));
    }


        public static boolean checkReverseEqual(String s1, String s2) {
            if(s1==null || s2==null){
                return false;
            }
            if(s1.length() != s2.length()){
                return false;
            }
            String s = s1+s1;
            if(s.contains(s2)){
                return true;
            }
            return false;
        }


}
