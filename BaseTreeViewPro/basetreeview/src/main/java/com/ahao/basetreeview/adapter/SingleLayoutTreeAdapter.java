package com.ahao.basetreeview.adapter;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.ahao.basetreeview.model.NodeId;
import com.ahao.basetreeview.model.TreeNode;
import com.ahao.basetreeview.util.DpUtil;
import com.ahao.basetreeview.util.TreeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class SingleLayoutTreeAdapter<T extends NodeId> extends BaseQuickAdapter<TreeNode<T>, BaseViewHolder> {

    public interface OnTreeClickedListener<T extends NodeId> {

        void onNodeClicked(View view, TreeNode<T> node, int position);

        void onLeafClicked(View view, TreeNode<T> node, int position);
    }

    private List<TreeNode<T>> dataToBind;
    private List<TreeNode<T>> allData;

    private OnTreeClickedListener onTreeClickedListener;

    public SingleLayoutTreeAdapter(int layoutResId, @Nullable final List<TreeNode<T>> dataToBind, @Nullable List<TreeNode<T>> allData) {
        super(layoutResId, dataToBind);
        this.dataToBind = dataToBind;
        this.allData = allData;

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TreeNode<T> node = dataToBind.get(position);
                if (!node.isLeaf()) {
                    List<TreeNode<T>> l = TreeUtils.getNodeChildren(node);

                    if (node.isExpand()) {
                        dataToBind.removeAll(l);
                        node.setExpand(false);
                        notifyItemRangeRemoved(position + 1, l.size());
                    } else {
                        dataToBind.addAll(position + 1, l);
                        node.setExpand(true);
                        notifyItemRangeInserted(position + 1, l.size());
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