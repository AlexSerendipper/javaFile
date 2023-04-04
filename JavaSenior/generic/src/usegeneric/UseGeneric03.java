package usegeneric;

import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 【泛型方法】
 *  不管此时定义的类是不是泛型类。其中的方法都可以被泛型化
 *   (泛型方法是在方法中出现了新的泛型结构，新的泛型参数与类的泛型参数没有关系)
 *  静态方法中不能使用类的泛型。(类的泛型在实例化时才指定)
 *  ✔泛型方法可以使用静态，因为使用的是自己的泛型，不是类的泛型，也是使用的时候才调用
 *  泛型方法的格式：[访问权限] <泛型> 返回类型 方法名([泛型标识 参数名称]) 抛出的异常
 @author Alex
 @create 2022-12-12-15:36
 */

public class UseGeneric03 {
    @Test
    public void test(){
        Slave<String, Integer> slave = new Slave<>();
        Integer[] nums= new Integer[]{1,2,3};
        List<Integer> integers = slave.copyArrayToList(nums);
        System.out.println(integers);
    }
}


class Slave<T1,T2>{
    T1 name;
    T2 age;
    // 该方法并不是泛型方法，只是一个普通方法使用了类的泛型✔这样只能使用类的泛型中定义的类型
    public T1 getName(){
        return name;
    }
    // 下列泛型方法的声明是典型的错误，编码器会误认为存在一个名为E的类，需要告知它E是泛型
    //    public List<E> copyArrayToList(E[] arr){
    //    }

    // 泛型方法：泛型方法是在方法中出现了新的泛型结构，新的泛型参数与类的泛型参数没有关系
    // 静态方法中不能使用类的泛型(类的泛型在实例化时才指定)✔泛型方法可以使用静态
      public static <E> List<E> copyArrayToList(E[] arr){
          ArrayList<E> arrayList = new ArrayList<>();
          for(E i:arr){
             arrayList.add(i);
         }
          return arrayList;
      }

}