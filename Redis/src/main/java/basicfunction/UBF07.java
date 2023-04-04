package basicfunction;

/**
 * 【AOF混合持久化】
 *  在同时开启rdb和aof持久化时，重启时只会加载aof文件，不会加载rbd文件
 *   若aof文件不存在，才会加载rdb文件
 *  在通常情况下AOF文件保存的数据集要比RDB文件保存的数据集要完整, 但不推荐只使用AOF
 *    因为RDB更适合用于备份数据库（AOF不断变化不好备份），留着RDB作为备份
 *  故推荐使用rdb+aof混合的方式，即使用RDB镜像作全量持久化，AOF作增量持久化
 *     aof-use-rdb-preamble yes              # 开启rdb+aof混合模式，默认开启
 *
 * 【纯缓存模式】
 *  在做某些高并发高性能的缓存服务器时，可能不需要开启rdb和aof功能，持久化功能交给其他程序完成
 *     开启 save""                           # 禁用rdb, 注意：我们仍然可以使用命令save、bgsave生成rdb文件
 *     appendonly no                         # 禁用aof，注意：我们仍然可以使用命令 bgrewriteaof生成aof文件
 *
 @author Alex
 @create 2023-03-30-20:36
 */
public class UBF07 {
}
