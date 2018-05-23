package com.li.service.impl;

import com.li.dao.FtpFileMapper;
import com.li.pojo.FtpFile;
import com.li.service.FtpFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FtpFileServiceImpl implements FtpFileService{

    @Autowired
    FtpFileMapper ftpFileMapper;

    @Override
    public int uploadFileParam(FtpFile uploadParameter) {
        return ftpFileMapper.uploadFileParam(uploadParameter);
    }

    @Override
    public List<FtpFile> queryPublicFile(int page) throws Exception{
        return ftpFileMapper.queryPublicFile(page);
    }
    @Override
    public List<FtpFile> queryPrivateFile(int page) throws Exception{
        return ftpFileMapper.queryPrivateFile(page);
    }
}
