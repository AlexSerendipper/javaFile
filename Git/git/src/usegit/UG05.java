package usegit;

/**
 *【Idea集成Git】
 *
 *【配置GIT忽略文件】针对IDEA的全局配置
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
 *【.gitignore的使用】针对项目的局部配置
 *  在被git管理的文件夹根目录下，可以创建.gitignore文件。不想让GIT跟踪哪个文件，就写哪个文件名，即可被自动忽略
 *
 *【在IDEA配置Git】
 *  File ==> setting ==> version control ==> git ==> path to git(选择GIT安装包下 BIN目录的git.exe)
 *  工具栏 ==> VCS(version control setting) ==> create git repository(默认选中的目录为当前项目的根目录，可以更改)
 *  上述操作完成后，目录下的文件将受到GIT追踪，文件依据当前状态变色（未受跟踪红色..），可以右键文件-git进行add和commit操作，
 *   也可以右键项目-git-将项目下的所有文件进行add和commit操作。add后文件变为绿色，commit后文件变为黑色，修改后的文件是蓝色
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
 *   github网站 ==> 头像 ==>  Developer settings ==> generate new token（把权限打满）      # token
 *   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                                           # 我的token，我把时间设置为无限了
 * (2)分享项目到github：正常需要现在github中创建远程库，IDEA可以直接创建远程库
 *  创建远程库并上传项目：工具栏 ==> VCS ==> share project on github ==> 输入远程库的名称以及别名（remote）
 *  使用SSH PUSH项目（默认使用https push）：工具栏 ==> git==> push ==> master origin:master ==> 点击origin定义SSH连接（输入当前库的SSH的网址并取名） ==> 将origin修改为我们取的别名 ==> 此时push即为SSH push
 *  pull代码：工具栏 ==> git ==> pull ==> 选择SSH拉取
 *  clone代码：File ==> new ==> project from version control ==> 直接输入SSH的网址就可以克隆代码到本地库
 *
 @author Alex
 @create 2023-01-24-20:11
 */
public class UG05 {
}
