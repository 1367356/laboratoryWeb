package com.li.dao;

import com.li.pojo.InsertParameter;
import com.li.pojo.ResearchTeam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearchTeamMapper {
    int insertResearchTeam(@Param("newsListParameter") InsertParameter parameter);  //团队列表

    int insertNews(@Param("newsParameter") InsertParameter newsParameter);

    int deleteNewshtmlid(long htmlid);

    int deleteResearchTeamHtmlid(long htmlid);

    List<String> query(String id);

    List<ResearchTeam> queryGroup(@Param("next") String next, @Param("id") String id);

    int update(@Param("teamParameter") InsertParameter teamParameter);

    String queryByHtmlid(String htmlid);
}
