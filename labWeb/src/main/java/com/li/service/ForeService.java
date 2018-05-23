package com.li.service;

import com.li.pojo.News;

public interface ForeService {
    News queryNews(long htmlid);

    int updateCount(long htmlid, int count);
}
