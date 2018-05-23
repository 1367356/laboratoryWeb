package com.li.pojo;

import java.sql.Timestamp;

/**
 * 新闻列表
 */
public class NewsList {
    private long htmlid;  // 新闻标识符，当点击新闻列表中的链接时，需用它查询新闻
    private String pid;   //上方导航栏 标识
    private String id;    //左方导航栏标识
    private String title;  //新闻标题
    private Timestamp date;  //新闻发布日期
    private String titleImage;  //标题图

    @Override
    public String toString() {
        return "NewsList{" +
                "htmlid=" + htmlid +
                ", pid='" + pid + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", titleImage='" + titleImage + '\'' +
                '}';
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public long getHtmlid() {
        return htmlid;
    }

    public void setHtmlid(long htmlid) {
        this.htmlid = htmlid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
