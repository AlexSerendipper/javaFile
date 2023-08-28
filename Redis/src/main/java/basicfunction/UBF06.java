package basicfunction;

/**
 * 【AOF（append only file）】
 *  以日志的形式来记录每个写操作，将Redis执行过的所有写指令记录下来(读操作不记录)，只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据。
 *   换言之，redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作
 *  默认情况下，redis是没有开启AOF的
 *   需要在配置文件中开启AOF功能  appendonly yes
 *
 * 【AOF工作流程】
 * （1）命令到达redis server后，会将命令放入AOF缓冲区中进行保存，当命令达到一定数量后再写入磁盘
 * （2）AOF缓冲会根据指定的 AOF缓冲区同步文件的写回策略(共三种) ，将命令写入磁盘上的AOF文件
 * （3）随着AOF内容的增加，会根据规则进行命令的合并（又称AOF重写），从而起到压缩AOF文件的目的
 *
 * 【三种回写策略】推荐就使用默认的everysec
 * （1）appendfsync always: 同步写回，每个写命令执行完立刻同步地将日志写回磁盘, 这种方式虽然性能较差但数据完整性比较好
 * （2）appendfsync everysec :每秒写回，每个写命令执行完，只是先把日志写到AOF缓冲区，每隔1s把缓存区地数据写入磁盘
 * （3）appendfsync no: 操作系统控制写回，只是将日志先写到AOF文件的内存缓冲区，由操作系统决定何时将缓冲区内容写回磁盘
 *
 * 【配置文件修改】
 *   appendonly yes          # 1380行
 *   appendfsync everysec    # 使用默认的回写机制
 *   dir myredis/                     # 生成的RDB文件目录
 *    appenddirname "appendonlydir"    # 生成的AOF文件目录，注意最后AOF文件存放在  myredis/appendonlydir
 *   appendfilename "appendonly.aof"  #  AOF文件的名称
 *   redis7采用了Multipart AOF的设计，在其AOF文件目录下有三个文件
 *    appendonly.aof.1.base.rdb       # 基本文件
 *    appendonly.aof.1.incr.aof       # 增量文件（主要在该文件中记录写操作命令）
 *    appendonly.aof.manifest         # 清单文件
 *
 * 【AOF修复指令】需要在当前文件的src目录下
 *   在网络闪断时，aof文件写入了错误的指令，此时再重启时，发现服务都无法启动。需要使用异常修复命令
 *   redis-check-aof --fix myredis/appenonlydir/appendonly.aof.1.incr.aof         # 进行AOF文件修复指令
 *
 * 【AOF优劣势】
 *   优势：更好的保护数据不丢失、性能高、可做紧急恢复
 *   劣势：相同数据集的数据而言aof文件要远大于rdb文件，恢复速度慢于rdb
 *          aof运行效率要慢于rdb，每秒同步策略效率较好，不同步效率和rdb相同
 *
 * 【AOF重写机制】根据规则进行命令的合并（又称AOF重写），只保留可以恢复数据的最小指令集，从而起到压缩AOF文件的目的
 * （1）自动触发：配置文件中修改。 每次重写AOF都会记录上一次地AOF大小，当AOF文件大小是上次rewrite大小的一倍且文件大于64M时，触发重写
 *               重写时，会将自动瘦身，将瘦身后的记录存储在appendonly.aof.1.base.rdb中，然后创建一个appendonly.aof.2.incr.aof进行记录
 *   auto-aof-rewrite-percentage 100
 *   auto-aof-rewrite-min-size 64mb
 * （2）手动触发：立刻进行重写，重写时，会将自动瘦身，将瘦身后的记录存储在appendonly.aof.1.base.rdb中，然后创建一个appendonly.aof.2.incr.aof进行记录
 *   bgrewriteaof
 *
 @author Alex
 @create 2023-03-30-19:14
 */
public class UBF06 {
}
