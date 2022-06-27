package com.chuxing.nfs.server.controller;

import com.chuxing.nfs.server.common.request.FileRequest;
import com.chuxing.nfs.server.common.response.Result;
import com.chuxing.nfs.server.service.FileService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @date 2022/6/27 15:24
 * @author huangchenguang
 * @desc file controller
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * @date 2022/6/27 15:28
     * @author huangchenguang
     * @desc list file
     */
    @RequestMapping("/ls")
    public Result<List<String>> ls(@RequestBody FileRequest request) {
        return Result.success(fileService.ls(request.getPath()));
    }

    /**
     * @date 2022/6/27 15:28
     * @author huangchenguang
     * @desc open file
     */
    @SneakyThrows
    @RequestMapping("/get")
    public Result<Void> get(HttpServletResponse response, @RequestBody FileRequest request) {
        long size = fileService.get(request.getPath(), request.getFileName(), response.getOutputStream());
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) size);
        response.setHeader("Content-Disposition", "attachment;filename=" + request.getFileName());
        return Result.success(null);
    }

    /**
     * @date 2022/6/27 16:19
     * @author huangchenguang
     * @desc create dir
     */
    @RequestMapping("/mkdir")
    private Result<Boolean> mkdir(@RequestBody FileRequest request) {
        return Result.success(fileService.mkdir(request.getPath()));
    }

    /**
     * @date 2022/6/27 16:19
     * @author huangchenguang
     * @desc create file
     */
    @SneakyThrows
    @RequestMapping("/put")
    private Result<Boolean> put(@RequestParam("path") String path, @RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile file) {
        return Result.success(fileService.put(path, fileName, file));
    }

    /**
     * @date 2022/6/27 16:19
     * @author huangchenguang
     * @desc delete file
     */
    @RequestMapping("/delete")
    private Result<Boolean> delete(@RequestBody FileRequest request) {
        return Result.success(fileService.delete(request.getPath(), request.getFileName()));
    }

}
