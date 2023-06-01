package caffeine;

/**
 * 【caffeine本地缓存】
 * （1）导入相关依赖
---------------
<!--caffine缓存-->
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
    <version>2.7.0</version>
</dependency>
---------------
 * (2) 自定义配置信息（非caffeine要求，为保证后续程序的可扩展性配置）
----------------
# caffeine
# 想让caffeine缓存前15页的数据
caffeine.posts.maxsize=15
# 通常也会使用变动淘汰，即帖子发生变化时更新缓存，但由于我们这里存储的是一页的数据，若一条帖子发生变化，就更新整页的缓存，不合适
caffeine.posts.expire-seconds=180
----------------
 *
 * 【caffeine的使用】
 *  caffeine的核心接口: Cache, Cache有两个最常用的子接口
 *    ✔LoadingCache, 同步缓存，当有多个线程同时访问该缓存, 并且缓存中没有该数据，此时LoadingCache会让所有其他线程等待，直到从数据库中取出数据后返回
 *    AsyncLoadingCache，异步缓存
 *（1）初始化缓存
----------------
postListCache = Caffeine.newBuilder()
                .maximumSize(maxSize)  // 设置最大缓存容量
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)  // 设置缓存存在的时间，在该期间内没有进行读写操作则自动删除缓存
                .build(new CacheLoader<String, PageInfo<DiscussPost>>() {  // 当尝试从缓存中取数据时，若存在数据则正常返回，若不存在相关数据，则需要告诉caffeine如何获取数据，并将其存储到缓存中，
                                                                           // 这里有点奇怪的, 因为每次取不到对应数据居然会调用这个方法~~~，所以只要将其初始化完成后交给IOC容器管理即可
                @Override
                public @Nullable PageInfo<DiscussPost> load(@NonNull String key) throws Exception {
                    if(key == null || key.length() == 0){
                        throw new IllegalArgumentException("参数错误！");
                    }
                    String [] params = key.split(":");
                    if(params == null || params.length!=2){
                        throw new IllegalArgumentException("参数错误！");
                    }
                    Integer pageNum = Integer.valueOf(params[0]);
                    Integer pageSize = Integer.valueOf(params[1]);
                    PageHelper.startPage(pageNum, pageSize);
                    List<DiscussPost> posts = discussPostMapper.selectDiscussPosts(0,1);
                    // 设置导航栏显示7
                    PageInfo<DiscussPost> pageInfo = new PageInfo<>(posts,7);
                    // ✔✔注意，此处在访问数据库之前 可以添加使用二级缓存，即从redis中寻找数据
                    logger.debug("load posts from DB");  // 若从数据库中取数据时，打印日志
                    return pageInfo;
                }
});
----------------
*（2）在需要的时候优先调用缓存中的数据，调用时传入key（该key用于初始化缓存中的load方法！~）
*      postListCache.get(pageNum + ":" + pageSize);
*
 @author Alex
 @create 2023-05-03-13:48
 */
public class UC01 {
}
