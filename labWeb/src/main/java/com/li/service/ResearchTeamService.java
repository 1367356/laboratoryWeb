package com.li.service;

import com.li.pojo.InsertParameter;
import com.li.pojo.ResearchTeam;

import java.util.List;

public interface ResearchTeamService {
    boolean insert(InsertParameter parameter) throws Exception;

    int delete(long lhtmlid) throws Exception;

    List<String> query(String id);

    List<ResearchTeam> queryGroup(String next,String id);

    int update(InsertParameter parameter);
}
