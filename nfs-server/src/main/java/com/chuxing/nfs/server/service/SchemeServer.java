package com.chuxing.nfs.server.service;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

/**
 * @date 2022/6/28 11:02
 * @author huangchenguang
 * @desc scheme server
 */
@Slf4j
@Service
public class SchemeServer {

    /**
     * @date 2022/6/28 11:46
     * @author huangchenguang
     * @desc file tree node
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TreeNode {

        /**
         * @date 2022/6/28 11:43
         * @author huangchenguang
         * @desc node
         */
        private String node;

        /**
         * @date 2022/6/28 11:43
         * @author huangchenguang
         * @desc child
         */
        private ConcurrentMap<String, TreeNode> child;

        /**
         * @date 2022/6/28 14:49
         * @author huangchenguang
         * @desc constructor
         */
        public TreeNode(String node) {
            this.node = node;
            this.child = Maps.newConcurrentMap();
        }

    }

    /**
     * @date 2022/6/28 14:52
     * @author huangchenguang
     * @desc root map
     */
    private TreeNode root;

    /**
     * @date 2022/6/28 14:52
     * @author huangchenguang
     * @desc init
     */
    @PostConstruct
    private void init() {
        root = new TreeNode("");
    }

    /**
     * @date 2022/6/28 14:51
     * @author huangchenguang
     * @desc add data to map
     */
    public boolean add(String path) {
        String[] nodes = path.split("/");
        String key = StringUtils.join(Arrays.copyOf(nodes, nodes.length - 1), "/");
        String value = nodes[nodes.length - 1];

        TreeNode current = searchTree(key);
        current.getChild().put(value, new TreeNode(value));
        return true;
    }

    /**
     * @date 2022/6/28 14:52
     * @author huangchenguang
     * @desc remove data to map
     */
    public boolean delete(String path) {
        String[] nodes = path.split("/");
        String key = StringUtils.join(Arrays.copyOf(nodes, nodes.length - 1), "/");
        String value = nodes[nodes.length - 1];

        TreeNode current = searchTree(key);
        current.getChild().remove(value);
        return false;
    }

    /**
     * @date 2022/6/28 14:51
     * @author huangchenguang
     * @desc search node tree
     */
    private TreeNode searchTree(String key) {
        String[] keys = key.split("/");
        TreeNode current = root;
        for (int i = 0; i < keys.length; i++) {
            current = current.getChild().get(keys[i]);
        }
        return current;
    }

}
