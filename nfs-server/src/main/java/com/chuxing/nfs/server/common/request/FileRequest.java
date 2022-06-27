package com.chuxing.nfs.server.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @date 2022/6/27 15:53
 * @author huangchenguang
 * @desc FileRequest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {

    /**
     * @date 2022/6/27 15:53
     * @author huangchenguang
     * @desc path
     */
    private String path;

    /**
     * @date 2022/6/27 15:53
     * @author huangchenguang
     * @desc file name
     */
    private String fileName;

    /**
     * @date 2022/6/27 16:58
     * @author huangchenguang
     * @desc file
     */
    private MultipartFile file;

}
