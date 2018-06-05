package test;

import com.li.pojo.News;
import com.li.pojo.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args){
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println(timestamp);
//        System.out.println(System.currentTimeMillis());
        System.out.println(19/10);

        int count=19;
        int totalpage=count%10==0?count/10:count/10+1;
        System.out.println(totalpage);

//        System.out.println(1526912646322.jpg==1526912646322.jpg);
    }


    public void test1() {
        User user=new User();
    }

    /**
     * 得到日期
     */
    public void test2() {
        Date current_date = new Date();
    //设置日期格式化样式为：yyyy-MM-dd
        SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //格式化当前日期
        String date = SimpleDateFormat.format(current_date.getTime());
    }
}
