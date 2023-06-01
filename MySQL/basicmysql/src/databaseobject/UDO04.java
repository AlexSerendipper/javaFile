package databaseobject;

/**
 * 【定义条件与处理程序】就是MySQL的异常处理机制
 *  定义条件：定义了程序执行过程中可能遇到的问题
 *   处理程序：定义了在遇到问题时应当采取的处理方式，并且保证存储过程或函数在遇到警告或错误时能继续执行。
 *  定义条件和处理程序在存储过程、存储函数中被支持使用
 *
 * 【定义条件】
 *  定义条件就是给MySQL中的错误码命名，将一个错误名字和指定的错误条件关联起来。这个名字可以随后被用在定义处理程序的DECLARE HANDLER语句中。
 *  MySQL_error_code和sqlstate_value都可以表示MySQL的错误。MySQL_error_code是数值类型错误代码。sqlstate_value是长度为5的字符串类型错误代码。
 *   例如，在ERROR 1418 (HY000)中，1418是MySQL_error_code，'HY000'是sqlstate_value。
 *  DECLARE 错误名称 CONDITION FOR 错误码（或错误条件）         # 定义MySQL_error_code
 *  DECLARE 错误名称 CONDITION FOR SQLSTATE '错误码'          # 定义sqlstate_value
 *
 * 【处理程序】
 *  可以为SQL执行过程中发生的某种类型的错误定义特殊的处理程序，真正的解决异常
 *  DECLARE 处理方式 HANDLER FOR 错误类型 处理语句
 * （1）处理方式：处理方式有3个取值
 *      CONTINUE ：表示遇到错误不处理，继续执行。
 *      EXIT ：表示遇到错误马上退出。
 *      UNDO ：表示遇到错误后撤回之前的操作。MySQL中暂时不支持这样的操作。
 * （2）错误类型（即条件）可以有如下取值：
 *      SQLSTATE '字符串错误码' ：      表示长度为5的sqlstate_value类型的错误代码；
 *      MySQL_error_code ：            匹配数值类型错误代码；
 *      错误名称 ：                    表示DECLARE ... CONDITION自定义的错误条件名称（所以其实可以不用自定义名称的）
 *      SQLWARNING ：                 匹配所有以01开头的SQLSTATE错误代码；
 *      NOT FOUND ：                  匹配所有以02开头的SQLSTATE错误代码；
 *      SQLEXCEPTION ：               匹配所有没有被SQLWARNING或NOT FOUND捕获的SQLSTATE错误代码；
 * （3）处理语句：
 * 如果出现上述条件之一，则采用对应的处理方式，并执行指定的处理语句。
 * 语句可以是像“ SET 变量 = 值 ”这样的简单语句，也可以是使用 BEGIN ... END 编写的复合语句。
 *
 * 【抛出异常】
 *  SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = '薪资高于领导薪资错误';
 @author Alex
 @create 2023-01-21-16:06
 */
public class UDO04 {
}
