package com.li.pojo;

//返回json数据的模板
public class ResponseEntity<T>{
    private String status; //0,1
    private String message; //成功，失败
    private T data;

    public ResponseEntity(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
