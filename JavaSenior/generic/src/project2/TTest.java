package project2;

import org.junit.Test;

/**
 @author Alex
 @create 2022-12-12-21:44
 */
public class TTest {
    @Test
    public void test() {
        DAO<User> dao = new DAO<>();
        User zzj = new User(1, 12, "zzj");
        User lyj = new User(2,41,"lyj");
        User hjy = new User(3,31,"hjy");
        User hyq = new User(4,1,"hyq");
        // 1.save
        dao.save("1",zzj);
        dao.save("2",lyj);
        dao.save("3",hjy);
        System.out.println("dao.get(1) = " + dao.get("1"));
        // 2.update
        dao.update("2",hyq);
        System.out.println("dao.get(2) = " + dao.get("2"));
        // 3. list
        for (User user : dao.list()) {
            System.out.println(user);
        }
        // 4.delete ，注意删除的是对应map中的id，不是user中的id
        dao.delete("2");
        System.out.println("******************");
        for (User user : dao.list()) {
            System.out.println(user);
        }

    }
}
