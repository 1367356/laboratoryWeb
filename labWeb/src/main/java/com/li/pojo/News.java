package com.li.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 新闻
 */
public class News implements Serializable{
    private long htmlid;  //新闻id,用于标识新闻
    private int count;   //浏览次数
    private String publisher;  //发布者
    private String title;     //标题
    private String type;     //类型
    private String abstractText;   //摘要
    private String content;    //新闻内容.html形式
    private Timestamp date;    //  发布日期

    public News(long htmlid, int count, String publisher, String title, String type, String abstractText, String content, Timestamp date) {
        this.htmlid = htmlid;
        this.count = count;
        this.publisher = publisher;
        this.title = title;
        this.type = type;
        this.abstractText = abstractText;
        this.content = content;
        this.date = date;
    }

    @Override
    public String toString() {
        return "News{" +
                "htmlid=" + htmlid +
                ", count=" + count +
                ", publisher='" + publisher + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", abstractText='" + abstractText + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }

    public long getHtmlid() {
        return htmlid;
    }

    public void setHtmlid(long htmlid) {
        this.htmlid = htmlid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
