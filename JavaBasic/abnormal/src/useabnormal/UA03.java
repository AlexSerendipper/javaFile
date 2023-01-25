package useabnormal;

import org.junit.Test;

/**
 * 【异常处理机制二：throws】
 *  声明抛出异常是Java中处理异常的第二种方式：应显示地声明抛出异常，表明该方法将不对这些异常进行处理，而由该方法的调用者负责处理。
 *  用throws语句可以声明抛出异常的列表，throws后面的异常类型可以是方法中产生的异常类型，也可以是它的父类
 *  重写方法不能抛出比被重写方法范围更大的异常类型。
 *  如果一个方法内抛出异常，该异常对象会被抛给调用者方法中处理。如果异常没有在调用者方法中处理，它继续被抛给这个调用
 * 方法的上层方法。这个过程将一直继续下去，直到异常被处理。这一过程称为捕获(catch)异常。
 *  如果一个异常回到main()方法，并且main()也不处理，则程序运行终止。
 * 异常代码后续的代码不会执行
 *
 * 【try-catch-finally与throws】
 *  try-catch-finally是将异常抓住后进行处理。throws是将异常抓住后抛出，实际上并没有处理异常
 *  开发中如何选用try-catch还是throws（经验之）
 *    （1）如果父类被重写的方法没有throws,子类重写的方法也不用throws，即子类重写的方法如果有异常，必须用try-catch处理
 *     (2) 执行方法中a，先后调用了b c d几个方法，并且这几个方法是递进关键，建议在b c d中使用throws，在a中使用try-catch处理
 *
 * 【自定义异常类】
 *  ✔用户自定义异常类都是RuntimeException的子类。（用户自己的异常类必须继承现有的异常类。）
 *  ✔自定义异常类通常需要编写几个重载的构造器。
 *  ✔自定义异常需要提供serialVersionUID
 *  自定义的异常通过throw抛出。
 *  自定义异常最重要的是异常类的名字，当异常出现时，可以根据名字判断异常类型。
 *
 * 【手动抛出异常】可以抛出已有的异常以及自定义异常类
 *  首先要生成异常类对象，然后通过throw语句实现抛出操作。如throw new IOException();
 *  可以抛出的异常必须是Throwable或其子类的实例
 *
 * 【throw和throws的区别】
 *  throw表示抛出一个异常类的对象，声明在方法体内
 *  throws表示异常处理的一种方式，声明在方法的声明处
 *
 @author Alex
 @create 2023-01-11-16:50
 */
public class UA03 {
    @Test
    public void test1(){
        throw new PersonalException();  // 抛出自定义异常
        // throw new RuntimeException();  // 也可以抛出已有的异常
    }


}

class PersonalException extends RuntimeException{  // 1、必须继承于现有的异常类
    static final long serialVersionUID = -7034897112345766939L;  // 2、必须提供全局常量serialVersionUID

    public PersonalException() {
        super();
    }

    public PersonalException(String message) {  // 3、必须提供重载的构造器
        super(message);
    }

}