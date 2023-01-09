package com.BYX.controller;

import com.BYX.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @Author Bai YanXu
 * @Date 2023-01-04 - 13:08
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;
    /**
     * 文件上传操作
     * @param file 文件，只能叫file，不能更改！
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //获得原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取文件扩展名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名重复,造成文件覆盖
        String fileName = UUID.randomUUID()+suffix;
        File dir=new File(basePath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    /**
     * 文件下载，即浏览器回显图片
     * @param name
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流，读取文件
            FileInputStream inputStream=new FileInputStream(basePath+name);
            //输出流，写会浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            //记录字节数
            int len=0;
            byte[] bytes=new byte[1024];
            while ((len=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //关闭流
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
