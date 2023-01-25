package usegit;

import org.junit.Test;

/**
 *
 * 【Git命令】
 *
 * 【1、Git配置命令】配置后的全局配置文件保存在 C:/用户/.gitconfig中
 *  git --version                              # 查看当前Git的版本
 *  git config --global user.name 用户名	    # ✔首次安装后需要设置用户签名，设置一次即可。签名的作用是区分不同操作者身份。这里设置用户签名和将来登录 GitHub（或其他代码托管中心）的账号没有任何关系。
 *  git config --global user.email 邮箱	    # 首次安装后需要设置用户email地址，设置一次即可
 *  git config --list --global                 # 查看Git的全局配置项
 *  git <命令> -h                              # 在终端中查看相关指令的帮助
 *
 * 【2、初始化命令】
 *  git init                          # 初始化本地库。即对未进行版本控制的项目进行Git控制，会创建一个 .git的隐藏目录
 *  git status -s                     # 输出文件控制报告，查看文件当前状态。 -s表示short，以精简方式显示
 *                                     # ？为未跟踪(untracked file)
 *                                       绿A表示首次跟踪暂存
 *                                       红M表示已修改
 *                                       绿M已修改且已暂存（绿色均表示需要commit）
 *                                       （绿D表示从本地仓库中删除）
 *
 * 【3、暂存命令】
 *  git add 当前目录下文件名            # 用途一：使用git跟踪文件，默认所有文件未被跟踪，首次跟踪直接添加到暂存区
 *                                        用途二：暂存文件，将已跟踪的修改后的文件添加到暂存区
 *  git add .                         # 将当前目录所有未跟踪和已修改的文件都添加到暂存区 ✔
 *
 * 【4、提交本地库命令】Git中是以行来维护，所以当我们修改了一行文字，提交时显示：1 DELETION, 1 INSERTION
 *  git commit -m "本次提交的描述" [文件名]        # 将暂存区的指定文件提交到git仓库保存, 不写具体文件名则将暂存区的所有文件提交到git仓库保存
 *  git commit --amend "本次提交的描述"            # 修改你最近的一次提交的描述。 使用完该命令后再push，远程端只能看到一次提交（即修改了上次提交的提交描述）（ i 编辑提交描述，esc : wq保存并退出）
 *
 * 【5、查看版本与版本穿梭命令】见下方举例说明
 *  git reflog                      # 先后列出所有的版本精简信息，按q退出
 *  git log [--pretty=oneline]      # 先后列出所有的详细信息版本信息。如果觉得显示复杂，可以加上参数 --pretty=oneline，则只会显示版本号和提交时的备注信息
 *                                   # ✔✔log与reflog的最大区别是，git reflog可以查看所有分支的所有操作记录，
 *                                          例如，执行 git reset --hard version1，退回到上一个版本，用git log则是看不出来被删除的commitid，用git reflog则可以看到被删除的commitid
 *  git reset --hard 版本号         # 回到指定的版本，
 *                                   # 注意：该指令会撤销工作区中所有未提交的修改内容。若此时暂存区内有未commit的文件，会直接丢失。
 *                                   # 回退版本指令也会被看作一次提交内容被reflog展示
 *
 * 【6、删除操作】我们通常不会去动本地库的内容
 *  git reset HEAD	当前目录下文件名             # 在暂存区移除指定文件
 *  git reset .                              # 移除暂存区所有文件，如果我们将文件保存到暂存区后又对文件进行修改
 *                                              这时查看git status，✔若只是修改内容，可以再次add，覆盖暂存区的同名文件
 *                                              ✔✔✔若是将文件改名，则改名前的文件仍会存在在暂存区中，需要手动删除。改名后的文件作为新文件需要被首次跟踪
 *  git rm	当前目录下文件名                    # 与直接从目录手动删除文件效果一致✔。即同时从工作区和暂存区中删除文件，即本地的文件也被删除了
 *  git rm --cached 当前目录下文件名           # 从工作区和暂存区中删除文件（保留本地文件）✔但取消该文件的版本控制
 *
 *
 @author Alex
 @create 2023-01-23-19:36
 */
public class UG02 {
    // 重要：版本回退说明
    @Test
    public void test1(){
        /*
         * ------------举例说明----------------
         * 第一次提交：工作区中始终有一个untracked.txt。提交hello1.txt
         * 第二次提交：提交hello2.txt
         * 第三次提交：创建hello3.txt放置暂存区，提交hello4.txt
         * 第四次提交：提交hello5.txt
         * 第五次提交：回到第四个版本，导致hello3.txt永远消失，切要小心。此外untracked.txt始终存在，不受回退版本影响
         * ------------结果如下----------------
         * d1b8dce (HEAD -> master) HEAD@{0}: reset: moving to d1b8dce      # 回退版本的信息仍然能再reflog中看见
         * f294a2f HEAD@{1}: commit: fourth commit
         * cee79c5 HEAD@{2}: commit: third commit
         * d1b8dce (HEAD -> master) HEAD@{3}: commit: second commit         # 回退版本，指定版本中的本地库中的文件覆盖所有受到跟踪的文件✔✔，暂存区的文件消失，未受跟踪的文件不受影响
         * c5eaee6 HEAD@{4}: commit (initial): first commit
         */
    }
}
