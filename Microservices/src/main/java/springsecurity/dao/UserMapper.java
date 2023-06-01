package springsecurity.dao;


import org.apache.ibatis.annotations.Mapper;
import springsecurity.entity.User;

@Mapper
public interface UserMapper {

    User selectByName(String username);

}
