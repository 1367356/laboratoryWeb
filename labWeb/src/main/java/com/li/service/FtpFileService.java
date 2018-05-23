package com.li.service;

import com.li.pojo.FtpFile;

import java.util.List;

public interface FtpFileService {
    int uploadFileParam(FtpFile uploadParameter);

    List<FtpFile> queryPublicFile(int page) throws Exception;
    List<FtpFile> queryPrivateFile(int page) throws Exception;
}
