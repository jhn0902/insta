package com.spring.insta.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

@Controller
public class ImageController {

    @GetMapping(path = "/image/{fileName}")
    public void setImageFileById(HttpServletResponse response, @PathVariable("fileName") String fileName)
            throws IOException {

        StringBuilder sb = new StringBuilder("file:/home/ec2-user/app/project/outsta/upload/");
        sb.append(fileName);
        URL fileUrl = new URL(sb.toString());
        IOUtils.copy(fileUrl.openStream(), response.getOutputStream());
    }
}
