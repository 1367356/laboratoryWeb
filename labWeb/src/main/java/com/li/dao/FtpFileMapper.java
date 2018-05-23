package com.li.dao;

import com.li.pojo.FtpFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FtpFileMapper {
    int uploadFileParam(@Param("parameter") FtpFile uploadParameter);

    List<FtpFile> queryPublicFile(@Param("page") int page);
    List<FtpFile> queryPrivateFile(@Param("page") int page);
}
