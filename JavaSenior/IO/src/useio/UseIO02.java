package useio;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 【一些File类的练习题】
 @author Alex
 @create 2022-12-17-9:34
 */
public class UseIO02 {
    //1.利用File构造器，new一个文件目录file
    //1)在其中创建多个文件和目录
    //2)实现删除file中指定文件的操作
    @Test
    public void test() throws IOException {
        File file = new File("D:\\桌面存放\\test\\hyq");
        File file1 = new File(file, "stupid.jpg");
        File file2 = new File(file, "stupid2.jpg");
        File file3 = new File("D:\\桌面存放\\test\\hyq");
        file3.mkdir();
        if (!file1.exists() & !file2.exists()) {
            file1.createNewFile();
            file2.createNewFile();
        } else {
            System.out.println("文件已存在");
        }
    }

    //2. 判断指定目录下是否有后缀名为.jpg的文件，如果有，就输出该文件名称
    @Test
    public void test2() {
        File file = new File("D:\\桌面存放\\test\\hyq");
        for (String s : file.list()) {
            if (s.endsWith(".jpg")) {  // 调用string的方法，endwith
                System.out.println(s);
            }
        }

    }

    //3. ✔遍历指定目录所有文件及其子文件，输出所有文件名（不包括目录名）
    //✔拓展1：并计算指定目录占用空间的大小
    //✔拓展2：删除指定文件目录及其下的所有文件
    @Test
    public void test3() {
        File file = new File("D:\\桌面存放\\test");
        File file1 = new File("D:\\桌面存放\\test\\hyq");
        // 输出文件名
        printFile(file);
        // 计算占用空间
        System.out.println("printFileLength(file) = " + printFileLength(file));
        // 删除指定文件
        deleteFile(file1);
    }

    public static void printFile(File file) {
        File[] listFiles = file.listFiles();
        for (File i : listFiles) {
            if (i.isDirectory()) {
                printFile(i);
            } else {
                System.out.println(i.getName());
            }
        }
    }

    static long fileLength;
    public static long printFileLength(File file) {
        File[] listFiles = file.listFiles();
        for (File i : listFiles) {
            if (i.isDirectory()) {
                printFileLength(i);
            } else {
                fileLength = fileLength + i.length();
            }
        }
        return fileLength;
    }


    public static void deleteFile(File file) {
        File[] listFiles = file.listFiles();
        for (File i : listFiles) {
            if (i.isDirectory()) {
                deleteFile(i);
            } else {
                i.delete();
            }
        }
    }
}

