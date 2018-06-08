package com.li.dao;

import com.li.pojo.FtpFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FtpFileMapper {
    int uploadFileParam(@Param("parameter") FtpFile uploadParameter);

    List<FtpFile> queryPublicFile(@Param("page") int page);

    List<FtpFile> queryPrivateFile(@Param("page") int page,@Param("username") String username);

    int uploadPrivateFileParam(@Param("privateFileParameter") FtpFile uploadPrivateFileParameter);

    int uploadPublicFileParam(@Param("publicFileParameter")FtpFile uploadParameter);

    int deletePublicFile(String id);

    int deletePrivateFile(String id);

    int selectPublicCount();

    int selectPrivateCount(String username);
}
