package com.li.dao;

import com.li.pojo.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ForeMapper {
    News queryNews(long htmlid);

    int updateCount(@Param("htmlid") long htmlid, @Param("count") int count);
}
