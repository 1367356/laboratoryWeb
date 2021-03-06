package com.li.service.impl;

import com.li.dao.ManageMapper;
import com.li.pojo.InsertParameter;
import com.li.pojo.News;
import com.li.pojo.NewsList;
import com.li.service.ManageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Transactional(value="txManager1")
    @CacheEvict(cacheNames = {"queryByCategory","queryNews","backQuery","selectCount"},allEntries=true)
    public int delete(long htmlid) throws Exception{
        int i1=manageMapper.deleteNewshtmlid(htmlid);
        int i2=manageMapper.deleteNewslistHtmlid(htmlid);
        return i1;
    }

    @Cacheable(cacheNames = {"queryByCategory"})
    public List<NewsList> queryByCategory(String pid, String id, int page) throws Exception{
        logger.debug("查询列表pid:"+pid+"id:"+id+"page:"+page);
        page=(page-1)*10;
        return manageMapper.queryByCategory(pid,id,page);
    }

    @Override
    public long getMaxHtmlId() {
        return manageMapper.getMaxHtmlId();
    }

    @Cacheable(cacheNames = {"backQuery"})
    public News backQuery(long htmlid) {
        logger.debug(htmlid);
        News news=manageMapper.backQuery(htmlid);
        logger.debug(news.toString()+"toString");
        return news;
    }

    @Override
    @CacheEvict(cacheNames = {"queryByCategory","queryNews","backQuery"},allEntries=true)
    public int update(InsertParameter parameter) {
        return manageMapper.update(parameter);
    }

    @Transactional(value="txManager1")
    @CacheEvict(cacheNames = {"queryByCategory","queryNews","backQuery","selectCount"},allEntries=true)
    public boolean insert(InsertParameter parameter) throws Exception {
        int i1=manageMapper.insertNewsList(parameter);;
        int i2=manageMapper.insertNews(parameter);
        if (i1!=i2 || i1!=1) {
            return false;
        }
        return true;
    }

    @Cacheable(cacheNames = {"selectCount"})
    public int selectCount(String pid, String id) {
        logger.debug("selectCount");
        return manageMapper.selectCount(pid,id);
    }

    @Override
    public int updateNewsList(InsertParameter parameter) {
        return manageMapper.updateNewsList(parameter);
    }
}
