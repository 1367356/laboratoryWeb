package com.li.utils;

import com.li.controller.ManageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @program: laboratoryWeb
 * @author: Yafei Li
 * @create: 2018-06-28 10:21
 **/
public class ImageUtil {

    static Logger logger = LogManager.getLogger(ImageUtil.class);
    //base64字符串转化成图片
    public static String GenerateImage(String imgStr,String path)  //返回图片地址

    {
        int i1 = imgStr.indexOf(",");
        String trueImgStr = imgStr.substring(i1+1);
        logger.debug(trueImgStr);

        int i2=imgStr.indexOf("/");
        int i3 = imgStr.indexOf(";");

        String format = imgStr.substring(i2+1, i3);
        logger.debug(format);

        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(trueImgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }

            String id=System.currentTimeMillis()+"."+format;

            // 组装返回url，以便于ckeditor定位图片
            String fileUrl = "/displayImage/"+id;

            //生成jpeg图片
            String imgFilePath = path+id;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return fileUrl;
        }
        catch (Exception e)
        {
            return "";
        }
    }

}
