package com.li.service.impl;

import com.li.dao.ForeMapper;
import com.li.pojo.News;
import com.li.service.ForeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForeServiceImpl implements ForeService{

    Logger logger = LogManager.getLogger(ForeServiceImpl.class);
    @Autowired
    ForeMapper foreMapper;
    @Override
    public News queryNews(long htmlid) {
        logger.debug(htmlid);
        return foreMapper.queryNews(htmlid);
    }

    public int updateCount(long htmlid, int count) {
        int i = foreMapper.updateCount(htmlid, count);
        return i;
    }
}
