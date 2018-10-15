package com.ahao.basetreeview.model;

import android.util.Log;

import com.ahao.basetreeview.util.ListUtil;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.ArrayList;
import java.util.List;

public class TreeNode<T extends NodeId> implements MultiItemEntity {
    private final static String TAG = "TreeNode";

    private T data;

    private int level;

    private boolean isExpand;

    private TreeNode<T> parent;

    private List<TreeNode<T>> children = new ArrayList<>();

    private int maxViewType ;

    public TreeNode(T data,int maxViewType) {
        this.data = data;
    }

    public String getId() {
        if (data == null) {
            Log.e(TAG, "getId: data is null");
            return "";
        }
        return data.getId();
    }

    public String getPId() {
        if (data == null) {
            Log.e(TAG, "getParentId: data is null");
            return "";
        }
        return data.getPId();
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isParentExpand() {
        if (parent == null) {
            return false;
        }
        return parent.isExpand();
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
        if (!isExpand) {
            for (TreeNode node : children) {
                node.setExpand(false);
            }
        }
    }

    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return ListUtil.isEmpty(children);
    }



    @Override
    public int getItemType() {
        if(isLeaf()){
            return -1;
        }
        int level = getLevel();
        return level >= maxViewType ? maxViewType : level;
    }
}
