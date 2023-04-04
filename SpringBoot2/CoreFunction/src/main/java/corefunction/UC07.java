package corefunction;

/**
 * 【springboot的日志框架整合 logback】
 *  springboot的日志框架整合相当于是对Spring的日志框架整合的进一步封装
 *   无需引入相关jar包 和 编写 logback-spring.xml 可以直接使用
 *
 * 【logback日志框架使用】和log4j2很类似
 *  private static final Logger log = LoggerFactory.getLogger(UW01.class)            # 创建logger，以当前类为参数传入，即指定了logger的名字（输出时知道出处）
 *    （注意引入的是org.slf4j下的相关类）
 *     此时在类中运行的程序就会自动给输出相关日志
 *  log.info/warn                                   # 手动输出相关级别的日志
 *
 *  logging.level.webfunction=warn                  # 在application.properties中设置测试类包路径的日志级别（代替了log4j2.xml的功能）
 *  logging.file.path=d:/test/                      # 在application.properties中设置输出日志的保存路径（代替了log4j2.xml的功能）
 *
 * 【logback扩展功能】
 *  当需要实现一些复杂功能，如不同类型的日志分别存储，存储到一定大小时创建新文件等
 *   就需要编写logback-spring.xml（见xmind）
 *
 *
 @author Alex
 @create 2023-04-04-13:30
 */
public class UC07 {
}
