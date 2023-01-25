package useio;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 【File类的使用】java.io.file
 * 1、file类的一个对象，就代表一个文件，或者代表一个文件目录（文件夹）
 * 2、File能新建、删除、重命名文件和目录，但File不能访问文件内容本身。如果需要访问文件内容本身，则需要使用输入/输出流.
 * 3、想要在Java程序中表示一个真实存在的文件或目录，那么必须有一个File对象。但是Java程序中的一个File对象，可能没有一个真实存在的文件或目录。
 * 4、若使用Junit测试，file类的相对路径指相对于当前Module下！
 *    若在main方法中测试，file类的相对路径指相对于当前的project下
 * 5、 路径分隔符和系统有关：windows和DOS系统默认使用“\”来表示。UNIX和URL使用“/”来表示
 *    Java为了解决这个隐患，File类提供了一个常量：public static final String separator。根据操作系统，动态的提供分隔符
 * 6. file类的对象后续会常作为参数传递到流的构造器中，用流来访问文件内容本身
 *
 *【file类的常见方法】
 * 1、构造器
 *  public File(String pathname)：以pathname为路径创建File对象，可以是绝对路径或者相对路径
 *  public File(String parent,String child)：以parent为父路径，child为子路径创建File对象
 *  public File(File parent,String child)：根据一个父File对象和子文件路径创建File对象
 * 2、获取（查找）功能
 *  public String getAbsolutePath()：获取绝对路径
 *  public String getPath() ：获取路径(存什么路径，输出什么路径)
 *  public String getName() ：获取名称
 *  public String getParent()：获取上层文件目录路径(相对路径无法显式返回路径)。若无，返回null
 *  public long length() ：获取文件长度（即：字节数）。不能获取目录的长度。
 *  public long lastModified() ：获取最后一次的修改时间，毫秒值
 *  public String[] list() ：(适用于文件目录)获取指定目录下的所有文件或者文件目录的名称数组
 *  public File[] listFiles() ：(适用于文件目录)获取指定目录下的所有文件或者文件目录的File数组
 * 3、重命名功能
 *  public boolean renameTo(File dest):把文件重命名为指定的文件路径
 *   file1.renameTo(File file2)，要想保证改名成功，需要file1在硬盘中存在，且file2在硬盘中不能存在！！
 * 4、判断功能
 *  public boolean isDirectory()：判断是否是文件目录
 *  public boolean isFile() ：判断是否是文件
 *  public boolean exists() ：判断是否存在
 *  public boolean canRead() ：判断是否可读
 *  public boolean canWrite() ：判断是否可写
 *  public boolean isHidden() ：判断是否隐藏
 * 5、创建与删除功能
 *  public boolean createNewFile() ：创建文件。若文件存在，则不创建，返回false
 *  public boolean mkdir() ：创建文件目录。如果此文件目录存在，就不创建了。
 *                            如果此文件目录的上层目录不存在，也不创建。
 *  public boolean mkdirs() ：创建文件目录。如果上层文件目录不存在，一并创建
 *                             注意事项：如果你创建文件或者文件目录没有写盘符路径，那么，默认在项目路径下。
 *  public boolean delete()：删除文件或者文件夹删除注意事项：
 *                            (1)Java中的删除不走回收站。
 *                            (2)要删除一个文件目录，请注意该文件目录内不能包含文件或者文件目录
 @author Alex
 @create 2022-12-15-9:45
 */
public class UseIO01 {
    @Test
    public void test() {
        // 构造器1
        File file = new File("hello.txt");
        File file1 = new File("D:\\桌面存放\\hh.txt");
        File file2 = new File("D:" + File.separator + "桌面存放" + File.separator + "gg.txt");
        System.out.println(file2);
        // 构造器2(指定当前目录下的文件目录（文件夹）)
        File file3 = new File("D:\\桌面存放", "test");
        System.out.println("file3 = " + file3);
        // 构造器3(在构造器2的文件目录下的文件)
        File file4 = new File(file3, "ttest.txt");
        System.out.println("file4 = " + file4);
    }

    // 获取功能
    @Test
    public void test1() {
        File file = new File("hello.txt");
        File file1 = new File("D:\\桌面存放\\test\\zzj\\zzj.txt");
        File file2 = new File("D:\\桌面存放", "test");
        System.out.println("file.getAbsoluteFile() = " + file.getAbsoluteFile());
        System.out.println("file.getPath() = " + file.getPath());
        System.out.println("file1.getPath() = " + file1.getPath());
        System.out.println("file1.getName() = " + file1.getName());
        System.out.println("file.getParent() = " + file.getParent());
        System.out.println("file.length() = " + file.length());
        System.out.println("file.lastModified() = " + file.lastModified());
        System.out.println(new Date(file.lastModified()));
        System.out.println("file.list() = " + file.list());
        System.out.println("file.listFiles() = " + file.listFiles());
        String[] list = file2.list();
        for (String str : list) {
            System.out.println(str);
        }
        for (File listFile : file2.listFiles()) {
            System.out.println("listFile.getName() = " + listFile);  // File的toString方法
        }

    }
    // 重命名功能
    @Test
    public void test2() {
        File file1 = new File("D:\\桌面存放\\test\\zzj\\zzj.txt");
        File file2 = new File("D:\\桌面存放\\test\\zzj\\lzy.txt");
        boolean b = file1.renameTo(file2);
        System.out.println(b);

    }

    // File类的判断功能
    @Test
    public void test3(){
        File file2 = new File("D:\\桌面存放\\test\\zzj\\lzy.txt");
        System.out.println("file2.isDirectory() = " + file2.isDirectory());
        System.out.println("file2.isFile() = " + file2.isFile());
        System.out.println("file2.exists() = " + file2.exists());
        System.out.println("file2.canRead() = " + file2.canRead());
        System.out.println("file2.canWrite() = " + file2.canWrite());
        System.out.println("file2.isHidden() = " + file2.isHidden());
    }

    // File类的创建与删除
    @Test
    public void test4() throws IOException {
        File file = new File("D:\\桌面存放\\test\\zzj\\hjy.txt");
        if(!file.exists()){
            file.createNewFile();
            System.out.println("创建成功");
        }else{
            file.delete();
            System.out.println("文件已存在，删除成功");
        }

        File file1 = new File("D:\\桌面存放\\test\\zzj\\new");
        if(!file1.exists()){
            file1.mkdir();
            System.out.println("创建成功");
        }else{
            file1.delete();
            System.out.println("文件夹已存在，删除成功");
        }
    }
}

