package usegit;

import java.util.Arrays;

/**
 *【Idea集成Git】
 *
 *【配置GIT忽略文件】针对IDEA的全局配置，✔✔注意，全局配置对所有的git管理文件都生效！
 *  有一些文件与项目的实际功能无关，不参与服务器上部署运行。把它们忽略掉能够屏蔽 IDE 工具之间的差异。
 *（1）创建忽略规则文件 xxxx.ignore（前缀名随便起，建议是 git.ignore），这个文件的存放位置原则上在哪里都可以，
 *    为了便于让~/.gitconfig 文件引用，建议也放在用户家目录下（C:\Users\Administrator）
 *（2）git.ignore 文件模版内容如下（该模板可以实现忽略IDEA中的非必要文件），注意以下代码必须要贴紧左边，否则失效✔
 --------------------------------------
# Compiled class file
*.class

# Log file
*.log

# BlueJ files
*.ctxt

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files #
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# virtual machine crash logs, see http://www.java.com/en/download/help/error_hotspot.xml
hs_err_pid*
.classpath
.project
.settings
target
.idea
*.iml
 ----------------------------------------
 *（3）在配置后的全局配置（ C:/用户/.gitconfig）中引用git.ignore
 [core]
 excludesfile = C:/Users/Administrator/git.ignore              # 注意：这里要使用“正斜线（/）”，不要使用“反斜线（\）” （这里不要复制进去）
 *
 *【.gitignore的使用】针对项目的局部配置，✔✔注意：局部配置是在全局配置下的补充
 *  在被git管理的文件夹根目录下，可以创建.gitignore文件。不想让GIT跟踪哪个文件，就写哪个文件名，即可被自动忽略
 *
 *【在IDEA配置Git】
 *  File ==> setting ==> version control ==> git ==> path to git(选择GIT安装包下 BIN目录的git.exe)
 * （1）初始化本地git仓库
 *  如果导航栏没有VCS选项，需要进行如下操作，file-version control-directory mappings-新增当前项目路径为根路径，VCS选择subversion即可
 *  工具栏 ==> VCS(version control setting) ==> create git repository(默认选中的目录为当前项目的根目录，可以更改)
 * （2）提交到本地仓库
 *  上述操作完成后，目录下的文件将受到GIT追踪，文件依据当前状态变色（未受跟踪红色..），可以右键文件-git进行add和commit操作，
 *   也可以右键项目git-git directory将项目下的所有文件进行add和commit操作。add后文件变为绿色，commit后文件变为黑色，修改后的文件是蓝色
 *   (注意提交时写一下提交的备注)
 *（3）版本切换功能
 *  在IDEA中，蓝色的文件可以直接commit不用add到暂存区
 *     查看版本信息：在IDEA左下角有个git，里面的log可以查看每次版本的提交信息
 *     版本切换：log中 右键提交信息-checkout revision 指定版本-可以很方便的切换不同版本
 *     创建分支：在IDEA右下角显示了当前分支，点击后可以创建分支
 *     合并分支（不冲突）：在IDEA右下角点击需要合并的分支，可以很方便的实现合并操作
 *     合并分支（冲突）：若合并冲突，则在合并时会出现conflict，点击merge进行手动解决冲突，左侧是主分支语句，右侧是功能分支语句，中间是不修改的无冲突代码
 *                      点击>>可以将左侧冲突代码添加到中间，点击<<可以将右侧冲突代码添加到中间
 *
 *【在IDEA中配置GitHub】
 * （1）登录github
 *  File ==> setting ==> version control ==> github ==> log in via github              # 使用账号密码登录，IDEA用该方法出奇的慢
 *  File ==> setting ==> version control ==> github ==> log in with token              # 使用密钥登录，token见下
 *   github网站 ==> 头像 ==> setting ==> Developer settings ==> generate new token（把权限打满）     # token
 *   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                                           # 我的token，我把时间设置为无限了
 * (2)分享项目到github：正常需要先在github中创建远程库 (IDEA可以直接创建远程库，但我个人习惯直接在网页端创建好)
 *  创建远程库(可选)：工具栏 ==> git ==> github ==> share project on github ==> 输入远程库的名称以及别名（remote）
 *  使用SSH PUSH项目（默认使用https push）：工具栏 ==> git==> push ==> master origin:master ==> 点击origin并输入输入当前库的SSH链接（默认本地分支为origin，可以不修改） ==> push
 *  pull代码：工具栏 ==> git ==> pull ==> 选择SSH拉取
 *  clone代码：File ==> new ==> project from version control ==> 直接输入SSH的网址就可以克隆代码到本地库
 *
 *
 @author Alex
 @create 2023-01-24-20:11
 */
public class UG05 {
    public static void main(String[] args) {
        int[] arr1 = {1, 5, 2};
        int[] arr2 = {4, 5, 1};

        // 定义结果数组
        int[] result = new int[arr1.length + arr2.length];

        // 将两个数组合并到结果数组中
        // System.arraycopy() 方法的第一个参数是要复制的源数组，第二个参数是目标数组的起始索引位置，
        // 第三个参数是要复制的源数组的结束索引位置(不包括该位置上的元素),第四个参数是要复制的元素数量。如果要复制的元素数量不足源数组中的元素数量，则会复制所有可用的元素。
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);

        Arrays.sort(result);
        // 输出结果
        System.out.println(Arrays.toString(result)); // [1, 2, 3, 4, 5, 6]
    }
    public static void merge(int[] A, int[] B){
        System.arraycopy(B, 0, A, A.length, B.length);

        // 对A数组进行排序
        Arrays.sort(A);
        // 输出排序后的A数组
        System.out.println(Arrays.toString(A));
    }

}
