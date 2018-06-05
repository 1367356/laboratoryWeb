package com.li.service;

import com.li.pojo.FtpFile;

import java.util.List;

public interface FtpFileService {
    int uploadFileParam(FtpFile uploadParameter);

    List<FtpFile> queryPublicFile(int page) throws Exception;
    List<FtpFile> queryPrivateFile(int page,String username) throws Exception;

    int uploadPrivateFileParam(FtpFile uploadParameter);

    int uploadPublicFileParam(FtpFile uploadParameter);

    int deletePublicFile(String id);

    int deletePrivateFile(String id);
}
