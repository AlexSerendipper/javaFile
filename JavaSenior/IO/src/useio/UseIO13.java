package useio;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 【Java NIO】由于后续框架会对NIO2进行封装✔，所以这里就不多进行赘述，了解即可
 *   Java NIO是从Java 1.4版本开始引入的一套新的IO API,可以替代标准的Java IO API。NIO与原来的IO有同样的作用和目
 *  的，但是使用的方式完全不同，NIO支持面向缓冲区的(IO是面向流的)、基于通道的IO操作。NIO将以更加高效的方式进行文件的读写操作。
 *   Java API中提供了两套NIO，一套是针对标准输入输出NIO，另一套就是网络编程NIO。
 *
 * 【NIO2】
 *   随着 JDK 7 的发布，Java对NIO进行了极大的扩展，增强了对文件处理和文件系统特性的支持，以至于我们称他们为 NIO.2。
 *   NIO. 2中，引入了Path接口用以取代原先的File✔
 *   NIO. 2中提供了Files、Paths工具类，Files包含了大量静态的工具方法来操作文件；Paths则包含了两个返回Path的静态工厂方法。
 *      static Path get(String first, String … more) : 用于将多个字符串串连成路径
 *      static Path get(URI uri): 返回指定uri对应的Path路径
 *
 * 【path类常用方法】
 *  String toString() ： 返回调用 Path 对象的字符串表示形式
 *  boolean startsWith(String path) : 判断是否以 path 路径开始
 *  boolean endsWith(String path) : 判断是否以 path 路径结束
 *  boolean isAbsolute() : 判断是否是绝对路径
 *  Path getParent() ：返回Path对象包含整个路径，不包含 Path 对象指定的文件路径
 *  Path getRoot() ：返回调用 Path 对象的根路径
 *  Path getFileName() : 返回与调用 Path 对象关联的文件名
 *  int getNameCount() : 返回Path 根目录后面元素的数量
 *  Path getName(int idx) : 返回指定索引位置 idx 的路径名称
 *  Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
 *  Path resolve(Path p) :合并两个路径，返回合并后的路径对应的Path对象
 *  File toFile(): 将Path转化为File类的对象
 *
 * 【Files工具类常用方法】
 *  Path copy(Path src, Path dest, CopyOption … how) : 文件的复制
 *  Path createDirectory(Path path, FileAttribute<?> … attr) : 创建一个目录
 *  Path createFile(Path path, FileAttribute<?> … arr) : 创建一个文件
 *  void delete(Path path) : 删除一个文件/目录，如果不存在，执行报错
 *  void deleteIfExists(Path path) : Path对应的文件/目录如果存在，执行删除
 *  Path move(Path src, Path dest, CopyOption…how) : 将 src 移动到 dest 位置
 *  long size(Path path) : 返回 path 指定文件的大小
 *
 * 【Files工具类常用方法：用于判断】
 *  boolean exists(Path path, LinkOption … opts) : 判断文件是否存在
 *  boolean isDirectory(Path path, LinkOption … opts) : 判断是否是目录
 *  boolean isRegularFile(Path path, LinkOption … opts) : 判断是否是文件
 *  boolean isHidden(Path path) : 判断是否是隐藏文件
 *  boolean isReadable(Path path) : 判断文件是否可读
 *  boolean isWritable(Path path) : 判断文件是否可写
 *  boolean notExists(Path path, LinkOption … opts) : 判断文件是否不存在
 *
 *【Files工具类常用方法：用于操作内容】
 *  SeekableByteChannel newByteChannel(Path path, OpenOption…how) : 获取与指定文件的连接，how 指定打开方式。
 *  DirectoryStream<Path> newDirectoryStream(Path path) : 打开 path 指定的目录
 *  InputStream newInputStream(Path path, OpenOption…how):获取 InputStream 对象
 *  OutputStream newOutputStream(Path path, OpenOption…how) : 获取 OutputStream 对象
 @author Alex
 @create 2022-12-19-16:46
 */
public class UseIO13 {
    public static void main(String[] args) throws IOException {
        File file = new File("IO\\111.png");
        File file1 = new File("IO\\222.png");
        // 第三方写好的包，方便我们执行一些操作拉~~~~~
        FileUtils.copyFile(file,file1);

    }
}
