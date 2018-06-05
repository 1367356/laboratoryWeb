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
        page=(page-1)*10;
        return ftpFileMapper.queryPublicFile(page);
    }
    @Override
    public List<FtpFile> queryPrivateFile(int page,String username) throws Exception{
        page=(page-1)*10;
        return ftpFileMapper.queryPrivateFile(page,username);
    }

    @Override
    public int uploadPrivateFileParam(FtpFile uploadParameter) {
        return ftpFileMapper.uploadPrivateFileParam(uploadParameter);
    }

    @Override
    public int uploadPublicFileParam(FtpFile uploadParameter) {
        return ftpFileMapper.uploadPublicFileParam(uploadParameter);
    }

    @Override
    public int deletePublicFile(String id) {
        return ftpFileMapper.deletePublicFile(id);
    }

    @Override
    public int deletePrivateFile(String id) {
        return ftpFileMapper.deletePrivateFile(id);
    }
}
