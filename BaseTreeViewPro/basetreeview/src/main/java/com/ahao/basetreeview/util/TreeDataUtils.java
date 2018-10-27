package com.ahao.basetreeview.util;

import com.ahao.basetreeview.model.NodeId;
import com.ahao.basetreeview.model.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeDataUtils {

    public static <T extends NodeId> List<TreeNode<T>> convertDataToTreeNode(List<T> datas, int maxViewType) {
        List<TreeNode<T>> nodes = new ArrayList<>();
        Map<String, TreeNode<T>> map = new HashMap();

        for (NodeId nodeId : datas) {
            TreeNode treeNode = new TreeNode(nodeId, maxViewType);
            nodes.add(treeNode);
            map.put(nodeId.getId(), treeNode);
        }

        Iterator<TreeNode<T>> iterator = nodes.iterator();
        while(iterator.hasNext()){
            TreeNode<T> treeNode = iterator.next();
            String pId = treeNode.getPId();
            TreeNode<T> parentNode = map.get(pId);
            if (parentNode != null) {
                parentNode.getChildren().add(treeNode);
                treeNode.setParent(parentNode);
                iterator.remove();
            }
        }
        return nodes;
    }

    public static <T extends NodeId> List<TreeNode<T>> convertDataToTreeNode(List<T> datas) {
        return convertDataToTreeNode(datas, -1);
    }

    public static <T extends NodeId> List<TreeNode<T>> getNodeChildren(TreeNode<T> node) {
        List<TreeNode<T>> result = new ArrayList<>();
        getRNodeChildren(result, node);
        return result;
    }

    private static <T extends NodeId> void getRNodeChildren(List<TreeNode<T>> result, TreeNode<T> node) {
        List<TreeNode<T>> children = node.getChildren();
        for (TreeNode n : children) {
            result.add(n);
            if (n.isExpand() && !n.isLeaf()) {
                getRNodeChildren(result, n);
            }
        }
    }

}