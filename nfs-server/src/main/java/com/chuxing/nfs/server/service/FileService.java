package com.chuxing.nfs.server.service;

import com.chuxing.nfs.server.config.NfsConfig;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

/**
 * @date 2022/6/24 15:37
 * @author huangchenguang
 * @desc nfs server
 */
@Slf4j
@Service
public class FileService {

    @Resource
    private NfsConfig config;

    /**
     * @date 2022/6/27 15:30
     * @author huangchenguang
     * @desc list file
     */
    public List<String> ls(String path) {
        File file = new File(String.format("%s/%s", config.getLocalStore(), path));
        String[] result = Optional.ofNullable(file.list()).orElse(new String[0]);
        return Lists.list(result);
    }

    /**
     * @date 2022/6/27 15:52
     * @author huangchenguang
     * @desc open file
     */
    public long get(String path, String fileName, OutputStream os) throws Exception {
        File file = new File(String.format("%s/%s/%s", config.getLocalStore(), path, fileName));
        BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()));
        byte[] buff = new byte[1024];
        int i;
        while ((i = bis.read(buff)) != -1) {
            os.write(buff, 0, i);
            os.flush();
        }
        return file.length();
    }

    /**
     * @date 2022/6/27 16:19
     * @author huangchenguang
     * @desc create dir
     */
    public boolean mkdir(String path) {
        File file = new File(String.format("%s/%s", config.getLocalStore(), path));
        return file.mkdir();
    }

    /**
     * @date 2022/6/27 16:25
     * @author huangchenguang
     * @desc create file
     */
    public boolean put(String path, String fileName, MultipartFile file) throws Exception {
        File localFile = new File(String.format("%s/%s/%s", config.getLocalStore(), path, fileName));
        file.transferTo(localFile);
        return true;
    }

}