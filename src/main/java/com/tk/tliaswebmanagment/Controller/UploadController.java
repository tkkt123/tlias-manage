package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.anno.Log;
import com.tk.tliaswebmanagment.pojo.Result;
import com.tk.tliaswebmanagment.utils.MinioOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadController {

    @Autowired
    MinioOSSOperator ossOperator;

    @Log
    @RequestMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String url=ossOperator.uploadFile(file);
        return Result.success(url);
    }
}
