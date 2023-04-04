package IoC.bean;

/**
 @author Alex
 @create 2023-02-21-13:56
 */
//员工类
public class Emp {
    private String ename;
    private String gender;
    //员工属于某一个部门，使用对象形式表示 !
    private Dept dept;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "ename='" + ename + '\'' +
                ", gender='" + gender + '\'' +
                ", dept=" + dept +
                '}';
    }
}