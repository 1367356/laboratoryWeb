package com.li.dao;

import com.li.pojo.InsertParameter;
import com.li.pojo.News;
import com.li.pojo.NewsList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManageMapper {

    int insertNews(@Param("newsParameter") InsertParameter parameter);     //向新闻中添加

    int insertNewsList(@Param("newsListParameter") InsertParameter parameter);  //向索引列表中添加

    int delete(@Param("htmlid") long htmlid);

    List<NewsList> queryByCategory(@Param("pid") String pid, @Param("id") String id, @Param("page") int page);

    long getMaxHtmlId();

    News backQuery(Long htmlid);

    int update(@Param("newsParameter") InsertParameter parameter);

    int selectCount(@Param("pid") String pid, @Param("id") String id);

}
