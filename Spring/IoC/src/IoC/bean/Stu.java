package IoC.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 @author Alex
 @create 2023-02-21-14:43
 */
public class Stu {
    //1 数组类型属性
    private String[] array;
    //2 list 集合类型属性
    private List<String> list;
    //3 map 集合类型属性
    private Map<String,String> maps;
    //4 set 集合类型属性
    private Set<String> sets;


    // 学生学的多门课程1：常规注入
    private List<Course> courseList;

    // 学生学的多门课程2：提取集合中注入的部分
    private List<Course> courseList2;


    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public Set<String> getSets() {
        return sets;
    }

    public void setSets(Set<String> sets) {
        this.sets = sets;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<Course> getCourseList2() {
        return courseList2;
    }

    public void setCourseList2(List<Course> courseList2) {
        this.courseList2 = courseList2;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "array=" + Arrays.toString(array) +
                ", list=" + list +
                ", maps=" + maps +
                ", sets=" + sets +
                ", courseList=" + courseList +
                ", courseList2=" + courseList2 +
                '}';
    }
}
