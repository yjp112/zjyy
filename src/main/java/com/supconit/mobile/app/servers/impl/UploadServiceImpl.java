package com.supconit.mobile.app.servers.impl;

import com.supconit.mobile.app.servers.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.awt.image.PNGImageDecoder;
import sun.misc.BASE64Decoder;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by wangwei on 2016-5-26.
 */
@Service
public class UploadServiceImpl implements UploadService{

    private transient static final Logger logger	= LoggerFactory.getLogger(UploadServiceImpl.class);


    @Override
    public String uploadImage(String serverPath,String base64,String personId,String effect) {
        if (base64 == null) {
            return null;
        }
        String url=null;
        String path=serverPath+"upload"+"\\"+personId;

        try {
            String uuuid=UUID.randomUUID().toString();
            String impagePath = path + "\\"+uuuid + "." + "png";

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(base64);//Base64解码
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;//调整异常数据
                }
            }
            OutputStream out = new FileOutputStream(impagePath);
            out.write(b);
            out.flush();
            out.close();
            if(effect!=null&&"headPic".equals(effect))
            {
                File imageFile= new File(impagePath);
                Image img= ImageIO.read(imageFile);
                int width=img.getWidth(null);
                if(width>200)
                {
                    BufferedImage image=new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
                    image.getGraphics().drawImage(img,0,0,200,200,null);
                    uuuid=UUID.randomUUID().toString();
                    String destPath=path + "\\"+uuuid + "." + "png";
                    File destFile = new File(destPath);
                    ImageIO.write(image,"png",destFile);
                    imageFile.delete();
                }
            }
            url=uuuid+ "." + "png";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
