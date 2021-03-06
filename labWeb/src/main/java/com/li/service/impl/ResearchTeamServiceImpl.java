package com.li.service.impl;

import com.li.dao.ResearchTeamMapper;
import com.li.pojo.InsertParameter;
import com.li.pojo.ResearchTeam;
import com.li.service.ResearchTeamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResearchTeamServiceImpl implements ResearchTeamService{

    Logger logger = LogManager.getLogger(ResearchTeamServiceImpl.class);

    @Autowired
    ResearchTeamMapper researchTeamMapper;
    @Transactional(value="txManager1")
    @CacheEvict(cacheNames = {"query","queryGroup","queryByHtmlid"},allEntries=true)
    public boolean insert(InsertParameter parameter) throws Exception{
        int i1=researchTeamMapper.insertResearchTeam(parameter);;
        int i2=researchTeamMapper.insertNews(parameter);

        if (i1!=i2 || i1!=1) {
            return false;
        }
        return true;
    }

    @Transactional(value="txManager1")
    @CacheEvict(cacheNames = {"query","queryGroup","queryByHtmlid"},allEntries=true)
    public int delete(long htmlid) throws Exception{
        int i1=researchTeamMapper.deleteNewshtmlid(htmlid);
        int i2=researchTeamMapper.deleteResearchTeamHtmlid(htmlid);
        return i1;
    }

    @Cacheable(cacheNames = {"query"})
    public List<String> query(String id) {
        List<String> typeList = researchTeamMapper.query(id);
        logger.debug(typeList.toString());
        return typeList;
    }

    @Cacheable(cacheNames = {"queryGroup"})
    public List<ResearchTeam> queryGroup(String next,String id) {
        return researchTeamMapper.queryGroup(next,id);
    }

    @CacheEvict(cacheNames = {"query","queryGroup","queryByHtmlid"},allEntries=true)
    public int update(InsertParameter parameter) {
        return researchTeamMapper.update(parameter);
    }

    @Cacheable(cacheNames = {"queryByHtmlid"})
    public String queryByHtmlid(String htmlid) {
        return researchTeamMapper.queryByHtmlid(htmlid);
    }

}
