package com.ahao.basetreeview.util;

import com.ahao.basetreeview.model.NodeId;
import com.ahao.basetreeview.model.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class TreeDataUtils {

    private static <T extends NodeId> List<TreeNode<T>> convertDataToTreeNode(List<T> datas, int maxViewType) {
        List<TreeNode<T>> nodes = new ArrayList<>();
        if (!ListUtil.isEmpty(datas)) {
            for (NodeId nodeId : datas) {
                nodes.add(new TreeNode(nodeId,maxViewType));
            }

            for (int i = 0; i < nodes.size(); i++) {
                TreeNode n = nodes.get(i);
                for (int j = i + 1; j < nodes.size(); j++) {
                    TreeNode m = nodes.get(j);
                    if (n.getPId().equals(m.getId())) {
                        n.setParent(m);
                        m.getChildren().add(n);
                    } else if (n.getId().equals(m.getPId())) {
                        n.getChildren().add(m);
                        m.setParent(n);
                    }
                }
            }
        }
        return nodes;
    }

    private static <T extends NodeId> List<TreeNode<T>> getRootNodes(List<TreeNode<T>> nodes) {
        List<TreeNode<T>> roots = new ArrayList<>();
        if (!ListUtil.isEmpty(nodes)) {
            for (TreeNode node : nodes) {
                if (node.isRoot()) {
                    roots.add(node);
                }
            }
        }
        return roots;
    }

    public static <T extends NodeId> List<TreeNode<T>> getSortedNodes(List<T> datas, int defaultExpandLevel, int maxViewType) {
        List<TreeNode<T>> result = new ArrayList<>();
        List<TreeNode<T>> treeNodes = convertDataToTreeNode(datas,maxViewType);
        List<TreeNode<T>> rootNodes = getRootNodes(treeNodes);

        int currentLevel = 0;
        for (TreeNode node : rootNodes) {
            addNode(result, node, defaultExpandLevel, currentLevel);
        }

        return result;
    }

    public static <T extends NodeId> List<TreeNode<T>> getSortedNodes(List<T> datas, int maxViewType) {
        return getSortedNodes(datas,-1,maxViewType);
    }

    public static <T extends NodeId> List<TreeNode<T>> getSortedNodes(List<T> datas) {
        return getSortedNodes(datas,-1,-1);
    }

    private static <T extends NodeId> void addNode(List<TreeNode<T>> result, TreeNode node, int defaultExpandLevel, int currentLevel) {
        node.setLevel(currentLevel);
        result.add(node);

        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }

        if (!node.isLeaf()) {
            List<TreeNode> children = node.getChildren();
            for (TreeNode n : children) {
                addNode(result, n, defaultExpandLevel, currentLevel + 1);
            }
        }
    }

    public static <T extends NodeId> List<TreeNode<T>> filterNode(List<TreeNode<T>> nodes){
        List<TreeNode<T>> result = new ArrayList<>();
        if(!ListUtil.isEmpty(nodes)){
            for(TreeNode node : nodes){
                if(node.isRoot() || node.isParentExpand()){
                    result.add(node);
                }
            }
        }
        return result;
    }

    public static <T extends NodeId> List<TreeNode<T>> getNodeChildren(TreeNode<T> node){
        List<TreeNode<T>> result = new ArrayList<>();
        getRNodeChildren(result,node);
        return result;
    }

    private static <T extends NodeId> void getRNodeChildren(List<TreeNode<T>> result, TreeNode<T> node) {
        List<TreeNode<T>> children = node.getChildren();
        for(TreeNode n :  children){
            result.add(n);
            if(n.isExpand() && !n.isLeaf()){
                getRNodeChildren(result,n);
            }
        }
    }

}
