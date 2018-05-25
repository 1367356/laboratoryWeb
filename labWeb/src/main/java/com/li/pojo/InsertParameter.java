package com.li.pojo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 新闻添加时用到的参数
 * 后台管理系统接收参数，向后台传递参数时，与字段名一一对应
 */
public class InsertParameter {
    private String pid;  //pid
    private String id;   //id
    private long htmlid;  //前台根据当前系统毫秒时间生成
    private String date;  //数据库中使用timestamp,默认为now()  ,post.setDateCreated(new Timestamp(System.currentTimeMillis())). 数据库sql `date_created`       DATETIME     NOT NULL,
    //        <result property="dateCreated" column="date_created"/>
    private String publisher;   //默认
    private String title;
    private String content;
    private String type;   //类型
    private String abstractText;
    private int count;   //浏览次数

    private String titleImage;  //标题图


    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getHtmlid() {
        return htmlid;
    }

    public void setHtmlid(long htmlid) {
        this.htmlid = htmlid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }


    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    @Override
    public String toString() {
        return "InsertParameter{" +
                "pid='" + pid + '\'' +
                ", id='" + id + '\'' +
                ", htmlid=" + htmlid +
                ", date='" + date + '\'' +
                ", publisher='" + publisher + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", abstractText='" + abstractText + '\'' +
                ", count=" + count +
                ", titleImage='" + titleImage + '\'' +
                '}';
    }
}
