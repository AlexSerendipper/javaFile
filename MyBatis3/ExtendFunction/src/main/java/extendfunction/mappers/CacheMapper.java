package extendfunction.mappers;

import extendfunction.pojo.Emp;

/**
 @author Alex
 @create 2023-03-15-16:16
 */
public interface CacheMapper {
    /**
     * 根据ID查询，测试缓存
     */
    Emp GetEmpById(Integer eid);
}
