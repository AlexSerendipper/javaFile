package usegit;

/**
 * 【团队协作与远程库】很重要
 *  使用远程库进行团队内协作的流程：主管A push自己的本地库到代码托管中心，员工B clone一份代码到自己电脑成立一份本地库，然后进行
 *   修改后再将代码push到代码托管中心，主管A pull代码托管中心的代码，就可以更新本地库的代码（将员工B的更新添加到自己的本地库），
 *   从而让员工B 的本地库、主管 A的本地库、远程库三者保持一致，实现团队内协作。
 *  使用远程库进行跨团队协作的流程：团队C请求团队D的帮助，团队D可以使用fork命令将团队C的远程库复制一份到自己的远程库（相当于拉取一个分支），
 *   然后团队D clone一份代码到自己电脑成立一份本地库，修改后再将代码push到代码托管中心，然后向团队C发起Pull request请求审核，团队C查看代码
 *   并审核通过后，可以将团队D的修改merge到自己的远程库（完成了远程库的修改），然后再pull远程库的代码到本地，更新本地库的代码
 *   从而实现团队C、团队D的远程库和本地库保持一致，实现跨团队合作。

 * 【GitHub使用】不要上传了100M以上的文件！会失败！而且失败后巨麻烦
 * （1）建立远程仓库：使用右上角new repository建立
 * （2）连接远程仓库：
 *      HTTPS零配置访问，到访问时需要重复输入账号密码（按照Github上提示输入即可，但是我sign in with your broswers失效，原因未知）
 *         git remote add 远仓别名 https://github.com/AlexSerendipper/ttest.git       # 将远程仓库（即其网址.git）重命名为别名（可省略）。使用git remote -v可以查看当前远程仓库的别名
 *         git branch -M 分支别名                                                     # 为当前分支起别名（若起了别名，推送到远程仓库后用的是别名）（常省略）
 *         git push 远仓别名 分支别名                                                  # 推送分支到远程仓库，可以不使用别名直接输入全名，只是使用HTTPS需要输入密码
 *      SSH需要额外配置，配置成功后不用重复输入账号密码✔✔✔✔✔
 *        ① ssh-keygen -t rsa -b 4096 -C "642671525@qq.com"                            # 注意此时使用的是最早设置的邮箱
 *        ② 在终端输入上述指令后，连续敲击 3 次回车，即可在 C:\Users\.ssh 目录中生成 id_rsa(私钥文件)和 id_rsa.pub(公钥文件)两个文件
 *        ③ 使用记事本打开 id_rsa.pub 文件，复制里面的文本内容
 *        ④ 在浏览器中登录 Github，点击头像 -> Settings -> SSH and GPG Keys -> New SSH key
 *        ⑤ 将 id_rsa.pub 文件中的内容，粘贴到 Key 对应的文本框中
 *        ⑥ 在 Title文本框中任意填写一个名称，作标识即可
 *         git remote add origin git@github.com:AlexSerendipper/ttest.git            # 将远程仓库（SSH网址）重命名为别名（可省略）
 *         git branch -M main                                                        # 为当前分支起别名（常省略）
 *         git push SSH网址 分支名                                                    # 推送分支到远程仓库
 * （3）远程仓库操作
 *    ✔ 如果配置时起了别名就使用别名，如果没有使用别名，可以输入完整地址
 *    ✔ 已经上传文件的仓库可以点击CODE选项获取SSH/HTTPS的地址
 *     git remote -v                               # 查看当前远程仓库的别名
 *     git push 远仓别名 分支别名                   # 推送分支到远程仓库，push最小以分支为单位。可以不使用别名直接输入地址
 *     git pull 远仓别名 分支别名                   # 拉取远程仓库的文件合并到本地库（当远程仓库被别人更新后使用）（同样可能出现合并冲突的问题）
 *     git clone 远仓别名                           # 公共库的克隆操作是完全不需要登录的！使用SSH/HTTPS的地址均可
 *                                                    ✔克隆会完成三个操作 1、拉取代码 2、初始化本地仓库。3、创建别名（默认创建为origin）
 *     git remote rm 远仓别名                       # 为远仓起的别名可以通过该命令删除
 *     github页面 - repository - setting - delete   # 删除github中的远程仓库
 *
 * 【团队内协作】✔
 *   只有库的创建者（主管A）拉取员工B后，员工B 才能和 主管A 共用一个远程库，员工B才能向远程库中push，形成团队协作
 * （1）远程仓库A - settings - collaborator - add people - 输入共创者账号 - 拿到一封邀请函（url地址）
 * （2）将邀请函发送给员工B，员工B打开后点击接收邀请，员工B在自己的账号界面就可以看到主管A的远程库
 * （3）员工B可以直接向主管A的远程仓库中push, 即 push 主管A的远仓地址 分支别名
 *      此时主管A pull自己的远仓，可以合并员工B的修改到本地库
 *
 * 【跨团队协作】✔团队C请求团队D的帮助
 * （1）团队D首先在github中打开团队C的项目，点击fork命令将团队C的远程库复制一份到自己的远程库（相当于拉取一个分支），修改后再将代码push到自己的远程库，
 * （2）然后点击pull requests - new pull requests - create pull request - 设置发送名以及对团队C所说的话 - create pull request
 * （3）进行上述操作后，团队C可以在自己的远程库中看到pull requests消息，此时可以查看到团队D修改的代码，也可以对团队D继续说话
 * （4）在确认代码无误后可以点击merge pull request - confirm merge, 即将团队D的修改merge到自己的远程库，实现了跨团队协作
 *
 @author Alex
 @create 2023-01-24-17:36
 */
public class UG04 {
}
