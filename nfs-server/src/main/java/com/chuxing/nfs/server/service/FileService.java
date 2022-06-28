package com.chuxing.nfs.server.service;

import com.chuxing.nfs.server.config.NfsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Resource
    private SchemeServer schemeServer;

    /**
     * @date 2022/6/27 15:30
     * @author huangchenguang
     * @desc list file
     */
    public List<String> ls(String path) {
        if (StringUtils.isBlank(path)) {
            throw new RuntimeException("path is blank");
        }
        String localPath = String.format("%s%s", config.getLocalStore(), path);
        File file = new File(localPath);
        if (file.exists()) {
            String[] result = Optional.ofNullable(file.list()).orElse(new String[0]);
            return Lists.list(result);
        } else {
            throw new RuntimeException(String.format("ls: %s: No such file or directory", localPath));
        }
    }

    /**
     * @date 2022/6/27 15:52
     * @author huangchenguang
     * @desc open file
     */
    public long get(String path, String fileName, OutputStream os) throws Exception {
        String localPath = String.format("%s%s/%s", config.getLocalStore(), path, fileName);
        File file = new File(localPath);
        if (file.exists()) {
            BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()));
            byte[] buff = new byte[1024];
            int i;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
            return file.length();
        } else {
            throw new RuntimeException(String.format("get: %s: No such file or directory", localPath));
        }
    }

    /**
     * @date 2022/6/27 16:19
     * @author huangchenguang
     * @desc create dir
     */
    public boolean mkdir(String path) {
        if (StringUtils.isBlank(path)) {
            throw new RuntimeException("path is blank");
        }
        File file = new File(String.format("%s%s", config.getLocalStore(), path));
        boolean result = file.mkdir();
        if (result) {
            schemeServer.add(path);
        }
        return result;
    }

    /**
     * @date 2022/6/27 16:25
     * @author huangchenguang
     * @desc create file
     */
    public boolean put(String path, String fileName, MultipartFile file) throws Exception {
        File localFile = new File(String.format("%s%s/%s", config.getLocalStore(), path, fileName));
        file.transferTo(localFile);
        schemeServer.add(String.format("%s/%s", path, fileName));
        return true;
    }

    /**
     * @date 2022/6/27 15:52
     * @author huangchenguang
     * @desc delete file
     */
    public boolean delete(String path, String fileName) {
        String localPath = String.format("%s%s/%s", config.getLocalStore(), path, fileName);
        File file = new File(localPath);
        if (file.exists()) {
            boolean result = file.delete();
            if (result) {
                schemeServer.delete(String.format("%s/%s", path, fileName));
            }
            return result;
        } else {
            throw new RuntimeException(String.format("delete: %s: No such file or directory", localPath));
        }
    }

}
