package com.amosannn.mapper;

import com.amosannn.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

  String TABLE_NAME = " user ";
  String INSERT_FIELDS = " email, password, activation_state, activation_code, join_time, username, avatar_url, gender, simple_desc, position, industry, career, education, full_desc, liked_count, collected_count, follow_count, followed_count, scaned_count, weibo_user_id ";
  String SELECT_FILEDS = " userId " + INSERT_FIELDS;

  @Select({"select ", " count(*) from ", TABLE_NAME, " where email like #{email}"})
  int emailCount(String email) ;

  @Insert({"insert into ", TABLE_NAME, " (email, password, activation_code, join_time, username, avatar_url", ") values (#{email}, #{password}, #{activationCode}, #{joinTime}, #{username}, #{avatarUrl})"})
  void insertUser(User user);
}
