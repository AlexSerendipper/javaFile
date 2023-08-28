package acm;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.REUtil;
import sun.applet.resources.MsgAppletViewer;

import java.util.*;

/**
 @author Alex
 @create 2023-08-26-10:06
 */
public class Main3 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int q = Integer.parseInt(in.nextLine());
        // 名字 - <卡号，安全码>
        HashMap<String, Map<String, String>> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();
        for (int i = 0; i < q; i++) {
            String s = in.nextLine();
            String[] s1 = s.split(" ");
            if(s1[0].equals("1")){
                String username = s1[1];
                String cardCode = s1[2];
                String security = s1[3];
                String msg = register(username, cardCode, security, map1, map2);
                System.out.println(msg);
            }else if(s1[0].equals("2")){
                String username = s1[1];
                String password = s1[2];
                String msg = transfer(username, password, map1, map2);
                System.out.println(msg);
            }else if(s1[0].equals("3")){
                String username = s1[1];
                String msg = delete(username, map1);
                System.out.println(msg);
            }
        }

    }

    public static String register(String username,String cardCode,String security,HashMap<String, Map<String, String>> map1, HashMap<String, Integer> map2){
        for (Map<String, String> value : map1.values()) {
            if(value.containsValue(cardCode)){
                return "register failed";
            }

        }

        if(map1.containsKey(username)){
            return "register failed";
        }

        HashMap<String, String> m = new HashMap<>();
        m.put("cardCode",cardCode);
        m.put("security",security);
        m.put("status","health");

        map1.put(username,m);
        map2.put(username,0);
        return "register accomplished";
    }

    public static String transfer(String username, String password, HashMap<String, Map<String, String>> map1, HashMap<String, Integer> map2){
        if(!map1.containsKey(username)){
            return "unexisted name";
        }

        Map<String, String> m = map1.get(username);
        String cardCode = m.get("cardCode");
        String security = m.get("security");
        String status = m.get("status");

        if(status.equals("locked")){
            return "card locked";
        }

        String pwd = cardCode.substring(cardCode.length()-4,cardCode.length()) + security;
        if(pwd.equals(password)){
            return "passed";
        }else {
            map2.put(username,map2.get(username)+1);
            if(map2.get(username)>=3){
                m.put("status","locked");
            }
            return "unsanctioned";
        }

    }

    public static String delete(String username, HashMap<String, Map<String, String>> map1){
        if(!map1.containsKey(username)){
            return "unexisted name";
        }

        map1.remove(username);
        return "cancellation succeed";
    }
}

