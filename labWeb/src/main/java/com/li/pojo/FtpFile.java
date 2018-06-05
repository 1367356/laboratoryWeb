package com.li.pojo;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * ftp上传参数
 */
public class FtpFile {

    private String id;    //134352334.avi 相当于别名，用于删除时定位文件
    private String downloadLink;   //下载链接
    private String uploadUser;   //上传用户名 ，通过安全管理器获取
    private String description;  //文件描述
    private String filename; //文件名称后台获取
    private String date;  //上传时间，后台获取

    @Override
    public String toString() {
        return "FtpFile{" +
                "id='" + id + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                ", uploadUser='" + uploadUser + '\'' +
                ", description='" + description + '\'' +
                ", filename='" + filename + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
