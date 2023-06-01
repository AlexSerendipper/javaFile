package basicfunction;

/**
 * 【redis安装】
 * (1) redis强烈建议安装在linux中
 * (2) gcc是linux下的c语言编译程序，要安装redis需要确保具备gcc编译环境，
 *      gcc --version                            # 查看当前linux的gcc版本(sudo apt install gcc)
 * (3) 配置gcc所需要的c++库环境。Ubuntu提供了一个build-essential软件包，安装了该软件包，编译c/c++所需要的软件包也都会被安装
 *      sudo apt install build-essential
 * (4) 下载redis7.0.9.tar.gz后放入Linux目录/opt
 *      tar -zxvf redis-7.0.10.tar.gz            # 在/opt目录下进行解压操作
 * (5) 进入到文件目录中 (/opt目录下)
 *      sudo -s                                  # 使用root用户权限进行安装
 *      make && make install                     # 进行编译和安装操作（默认ubuntu安装到当前目录的src目录下）
 *                                                                   (默认CentOS安装到user/local/bin目录下)
 * (6) 安装后，src目录下多出一下内容
 *      redis-benchmark: 性能测试工具，服务启动后运行该命令 可以查看自己电脑性能如何
 *      redis-check-aof: 修复有问题的AOF文件
 *      redis-check-dump: 修复有问题的dump.rdb文件
 *      redis-cil: 客户端，操作入口✔
 *      redis-sentinel: redis集群使用
 *      redis-server: redis服务器启动命令✔
 * (7) 修改 /opt/redis-7.0.10/redis.conf 配置文件
 *      将原本的redis.conf拷贝到自己定义好的路径下备份好，如 /myredis/redis7.conf
 *      配置文件修改，默认 daemonize no 改为 daemonize yes。允许redis后台运行
 *      配置文件修改，默认 protected-mode yes 改为 protected-mode no。关闭保护模式，允许其他机器连接
 *      配置文件修改，默认 bind 127.0.0.1 改为 直接注释掉。
 *      配置文件修改，默认 requirepass foobared，改为 取消注释，并将foobared改为你的redis密码
 *      配置文件修改，默认 port 6379，默认端口号为 6379, 可以进行修改
 *      重新启动虚拟机
 * (8) 启动服务
 *      ps -ef|grep redis                      # 可以使用该命令查看当前redis的连接状态（需要在root用户处）✔✔
 *      redis-server myredis/redis7.conf       # 以该配置文件（相对路径）启动redis
 * (9) 客户端连接服务
 *      redis-cli -a qqabcd -p 6379            # 客户端访问redis。输入访问的密码以及端口号，默认为6479
 *      redis-cli -a qqabcd -p 6379 --raw      # 以支持中文模式登录redis（当redis中存储中文时使用）
 *      ping                                   # 此时输入ping命令如果响应pong则代表连接服务成功
 *      quit                                   # 退出当前客户端连接
 * (10) 关闭服务
 *      shutdown                               # 在当前服务终端可以直接通过shutdown命令关闭
 *        quit
 *      redis-cli -a 对应的密码 shutdown        # 在其他位置，指定本机登录的redis密码，也可以关闭redis服务
 *        redis-cli -p 对应的端口号 shutdown       # 在其他位置，指定本机登录的redis的端口号，也可以关闭redis服务
 * (11) redis卸载：首先关闭服务
 *      rm -rf /opt/redis-7.0.10/src/redis-*   # 卸载对应目录下所有redis开头
 *
 @author Alex
 @create 2023-03-29-17:32
 */
public class UBF02 {
}
