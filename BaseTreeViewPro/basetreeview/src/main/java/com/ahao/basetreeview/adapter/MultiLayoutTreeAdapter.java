package com.ahao.basetreeview.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.ahao.basetreeview.model.NodeId;
import com.ahao.basetreeview.model.TreeNode;
import com.ahao.basetreeview.util.DpUtil;
import com.ahao.basetreeview.util.TreeDataUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class MultiLayoutTreeAdapter<T extends NodeId> extends BaseMultiItemQuickAdapter<TreeNode<T>, BaseViewHolder> {

    public interface OnTreeClickedListener<T extends NodeId> {

        void onNodeClicked(View view, TreeNode<T> node, int position);

        void onLeafClicked(View view, TreeNode<T> node, int position);
    }

    private OnTreeClickedListener onTreeClickedListener;

    public MultiLayoutTreeAdapter(final List<TreeNode<T>> dataToBind) {
        super(dataToBind);
        addItemTypes();

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TreeNode<T> node = dataToBind.get(position);
                if (!node.isLeaf()) {
                    List<TreeNode<T>> children = TreeDataUtils.getNodeChildren(node);

                    if (node.isExpand()) {
                        dataToBind.removeAll(children);
                        node.setExpand(false);
                        notifyItemRangeRemoved(position + 1, children.size());
                    } else {
                        dataToBind.addAll(position + 1, children);
                        node.setExpand(true);
                        notifyItemRangeInserted(position + 1, children.size());
                    }

                    if (onTreeClickedListener != null) {
                        onTreeClickedListener.onNodeClicked(view, node, position);
                    }
                } else {
                    if (onTreeClickedListener != null) {
                        onTreeClickedListener.onLeafClicked(view, node, position);
                    }
                }

            }
        });
    }

    protected void addItemTypes() {

    }

    @Override
    protected void convert(BaseViewHolder helper, TreeNode<T> item) {
        int level = item.getLevel();
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) helper.itemView.getLayoutParams();
        layoutParams.leftMargin = getTreeNodeMargin() * level;
    }

    public void setOnTreeClickedListener(OnTreeClickedListener onTreeClickedListener) {
        this.onTreeClickedListener = onTreeClickedListener;
    }

    protected int getTreeNodeMargin() {
        return DpUtil.dip2px(this.mContext, 10);
    }
}
