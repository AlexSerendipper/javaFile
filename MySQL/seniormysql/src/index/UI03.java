package index;

/**
 * 【MyISAM中索引方案】见xmind
 *  InnoDB和MyISAM引擎 默认的索引是Btree索引；而Memory引擎 默认的索引是Hash索引。
 *   MyISAM引擎 使用 B+Tree作为索引结构，叶子节点的data域存放的是  数据记录的地址。
 *  MyISAM引擎由于数据和索引分离，主键索引和二级索引在结构上没有任何区别，只是主键索引要求key是唯一的（二级索引的key可以重复）。所有只有 非聚簇索引 这个说法。
 *
 * 【MyISAM 与 InnoDB对比】
 *  MyISAM的索引方式都是 “非聚簇” 的，InnoDB中一定包含1个聚簇索引
 *  在InnoDB存储引擎中，我们只需要根据主键值对 聚簇索引 进行一次查找就能找到对应的记录，而在MyISAM 中却至少需要进行一次 回表（先查找到地址值，再找到对应的记录），
 *   意味着MyISAM中建立的索引相当于全部都是 二级索引。
 *  InnoDB的数据文件本身就是索引文件，而MyISAM索引文件和数据文件是 分离的，索引文件仅保存数据记录的地址。
 *  InnoDB的非聚簇索引data域存储相应记录 主键的值，而MyISAM索引记录的是 地址
 *  MyISAM的回表操作是十分 快速 的，因为是拿着地址偏移量直接到文件中取数据的，反观InnoDB是通过获取主键之后再去聚簇索引里找记录，虽然说也不慢，但还是比不上直接用地址去访问。
 *  InnoDB 要求表 必须有主键（MyISAM可以没有）。如果没有显式指定，则 InnoDB 会自动选择一个可以非空且唯一标识数据记录的列作为主键（如果不存在这种列，则自动创建。
 *
 * 【索引的缺陷】
 *  空间上的代价：每建立一个索引都要为它建立一棵B+树，每一棵B+树的每一个节点都是一个数据页，一个页默认会
 *                占用 16KB 的存储空间，一棵很大的B+树由许多数据页组成，那就是很大的一片存储空间。
 *  时间上的代价：对表中的数据进行 增、删、改 操作时，都需要去修改各个B+树索引（保持其依照 主键递增等特性）。所以存储引擎需
 *                要额外的时间进行一些 记录移位，页面分裂，页面回收 等操作来维护好节点和记录的排序。如果
 *                我们建了许多索引，每个索引对应的B+树都要进行相关的维护操作，会给性能拖后腿。
 *
 *
 @author Alex
 @create 2023-05-16-13:23
 */
public class UI03 {
}