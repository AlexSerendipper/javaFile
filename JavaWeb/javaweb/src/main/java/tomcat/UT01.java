package tomcat;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

/**
 * 【XML】全称为Extensible markup language，和HTML一样是一种标记语言，但是HTML是用来描述网页内容
 *  xml是一个可扩展的标记性语言。
 *  xml 的主要作用有：
 *   （1）用来保存数据，而且这些数据具有自我描述性
 *   （2）它还可以做为项目或者模块的配置文件（XML主要功能）
 *   （3）还可以做为网络传输数据的格式（现在 JSON 为主）。
 *
 *【XML语法】xml属性、文本区域（CDATA 区）
 * （1）文档声明
 *   <?xml version="1.0" encoding="UTF-8"?>               # 此为xml声明。version为xml的版本，encoding为编码方式
 *  (2) 注释
 *   <!-- html 注释 -->                                   # html 和 XML 注释 一样 :
 *  (3)元素（标签）
 *   如：<title>java编程思想</title>                       # 元素是指从开始标签到结束标签的内容，和HTML不同，HTML中一个标签就是一个标签，如<div>
 *   xml中的标签也分为单标签和双标签
 *     单标签格式： <标签名 属性=”值” 属性=”值” ...... />
 *     双标签格式： <标签名 属性=”值” 属性=”值” ......>文本数据或子标签</标签名>
 *   XML标签必须遵循以下命名规则：
 *     名称可以含字母、数字以及其他的字符
 *     名称不能以数字或者标点符号开始
 *     名称不能包含空格
 *   XML标签对大小写敏感
 *   XML文档有且只能有一个根元素（顶级元素）：即只能有一个最外层的元素，见示例
 *   XML中的特殊字符和HTML中的特殊字符表示方法一致
 * （4）xml属性：和html标签的自定义属性非常类似的，属性可以提供元素的额外信息。
 *   一个标签上可以书写多个属性。每个属性的值必须使用 引号 引起来✔
 * （5）文本区域（CDATA 区）
 *   CDATA 语法可以告诉 xml 解析器，我 CDATA 里的文本内容，只是纯文本，不需要 xml 语法解析
 *   <![CDATA[ 这里可以把你输入的字符原样显示，不会解析 xml ]]>
 *
 *【XML解析技术】了解
 *  不管是 html 文件还是 xml 文件它们都是标记型文档，都可以使用 w3c 组织制定的 dom 技术来解析。
 *  早期 JDK 为我们提供了两种 xml 解析技术 DOM 和 Sax，不过都已经过时，
 *   现在通常使用Dom4j进行xml解析。它是第三方的解析技术。需要使用第三方提供的类库才可以使用（导入）
 *  Dom4j读取xml流程
 *   （1）在模块下创建bin目录（右键add as library），导入Dom4j的jar包
 *   （2）先创建一个 SAXReader输入流对象
 *    (3) 使用SAXReader对象的read方法读取数据，返回document对象
 *  Dom4j遍历遍历标签。获取所有标签中的内容流程
 *   （1）通过创建 SAXReader 对象。来读取 xml 文件，获取 Document 对象
 *   （2）通过 Document 对象。拿到 XML 的根元素对象
 *         document.getRootElement();使用该方法可以获取根元素
 *   （3）通过根元素对象。获取所有的 book 标签对象
 *         Element.element(标签名)可以拿到当前元素下的指定的子元素
 *         Element.elements(标签名)它可以拿到当前元素下的指定的子元素的集合
 *   （4）遍历每个 book 标签对象。遍历 book 标签对象内的每一个元素
 *         通过 getText()方法拿到起始标签和结束标签之间的文本内容
 *
 @author Alex
 @create 2023-01-27-16:09
 */
public class UT01 {
    // Dom4j遍历遍历标签
    @Test
    public void test1() throws DocumentException {
        // 要创建一个 Document 对象，需要我们先创建一个 SAXReader 对象
        SAXReader reader = new SAXReader();
        // 这个对象用于读取 xml 文件，然后返回一个 Document。
        Document document = reader.read("src/main/java/tomcat/UT01.xml");  // junit是从模块名开始算相对路径
        // 打印到控制台，看看是否创建成功
        System.out.println(document);
        // 第二步，通过 Document 对象。拿到 XML 的根元素对象
        Element root = document.getRootElement();
        // 第三步，通过根元素对象。获取所有的 book 标签对象
         List<Element> books = root.elements("book");
        // 第四步，遍历每个 book 标签对象。然后获取到 book 标签对象内的每一个元素，
        for (Element book : books) {
            Element nameElement = book.element("name");
            Element priceElement = book.element("price");
            Element authorElement = book.element("author");
            System.out.println("书名" + nameElement.getText() + " , 价格:"
                    + priceElement.getText() + ", 作者：" + authorElement.getText());
            // 可以转换为book对象
            System.out.println(new book(nameElement.getText(), Double.parseDouble(priceElement.getText()), authorElement.getText()));
        }
}
}

class book{
    String name;
    double price;
    String author;

    public book() {
    }

    public book(String name, double price, String author) {
        this.name = name;
        this.price = price;
        this.author = author;
    }

    @Override
    public String toString() {
        return "book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                '}';
    }
}
