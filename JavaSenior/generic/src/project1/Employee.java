package project1;

/**
 * @author Alex
 * @create 2022-12-05-16:08
 */
public class Employee implements Comparable<Employee>{
    private String name;
    private int age;
    private MyDate birthday;

    public Employee(String name, int age, MyDate birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MyDate getBirthday() {
        return birthday;
    }

    public void setBirthday(MyDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }



    // 修改为指明泛型，因为编译时就会报错，所以不用抛异常拉
    @Override
    public int compareTo(Employee o) {
        return this.name.compareTo(o.name);
    }

//    @Override
//    public int compareTo(Object o) {
//        if(o instanceof Employee){
//            Employee o1 = (Employee) o;
//            return this.name.compareTo(o1.name);
//        }
//        // 效果和抛异常是一样的，返回0就添加不进去了
//        return 0;
//        // throw new RuntimeException("输入类型错误");
//    }


}
