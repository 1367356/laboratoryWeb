package com.li.service.impl;

import com.li.dao.ManageMapper;
import com.li.pojo.InsertParameter;
import com.li.pojo.News;
import com.li.pojo.NewsList;
import com.li.service.ManageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManageServiceImpl implements ManageService{

    Logger logger = LogManager.getLogger(ManageServiceImpl.class);

    @Autowired
    private ManageMapper manageMapper;


    @Override
    public int insertNews(InsertParameter parameter) {
        int i = manageMapper.insertNews(parameter);
        return i;
    }

    @Override
    public int insertNewsList(InsertParameter parameter) {
        int i = manageMapper.insertNewsList(parameter);
        return i;
    }

    @Override
    public int delete(long htmlid) {
        return manageMapper.delete(htmlid);
    }

    @Override
    public List<NewsList> queryByCategory(String pid, String id, int page) {
        return manageMapper.queryByCategory(pid,id,page);
    }

    @Override
    public long getMaxHtmlId() {
        return manageMapper.getMaxHtmlId();
    }

    @Override
    public News backQuery(long htmlid) {
        logger.debug(htmlid);
        News news=manageMapper.backQuery(htmlid);
        logger.debug(news.toString()+"toString");
        return news;
    }

    @Override
    public int update(InsertParameter parameter) {
        return manageMapper.update(parameter);
    }

    @Transactional(value="txManager1")
    @Override
    public boolean insert(InsertParameter parameter) throws Exception {
        int i1=manageMapper.insertNewsList(parameter);;
        int i2=manageMapper.insertNews(parameter);

        if (i1!=i2 || i1!=1) {
            return false;
        }
        return true;
    }
}
