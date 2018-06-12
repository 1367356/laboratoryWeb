package com.li.service;

import com.li.pojo.InsertParameter;
import com.li.pojo.News;
import com.li.pojo.NewsList;

import java.util.List;

public interface ManageService {

    int insertNews(InsertParameter parameter);     //向新闻中添加

    int insertNewsList(InsertParameter parameter);  //向索引列表中添加

    int delete(long htmlid) throws Exception;

    List<NewsList> queryByCategory(String pid, String id, int page);

    long getMaxHtmlId();

    News backQuery(long htmlid);

    int update(InsertParameter parameter);  //更新post

    boolean insert(InsertParameter parameter) throws Exception;  //向数据库中添加新闻，包括news和newslist

    int selectCount(String pid, String id);
}
