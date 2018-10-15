package com.ahao.basetreeview;
import android.view.View;

import com.ahao.basetreeview.util.DpUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class MultiLayoutTreeAdapter<T extends NodeId> extends BaseMultiItemQuickAdapter<TreeNode<T>, BaseViewHolder> {

    public interface OnTreeClickedListener<T extends NodeId> {

        void onNodeClicked(View view, TreeNode<T> node, int position);

        void onLeafClicked(View view, TreeNode<T> node, int position);
    }

    private List<TreeNode<T>> dataToBind;
    private List<TreeNode<T>> allData;

    private OnTreeClickedListener onTreeClickedListener;

    public MultiLayoutTreeAdapter(final List<TreeNode<T>> dataToBind, final List<TreeNode<T>> allData) {
        super(dataToBind);

        this.dataToBind = dataToBind;
        this.allData = allData;

        addItemTypes();

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TreeNode<T> node = dataToBind.get(position);
                if (!node.isLeaf()) {
                    List<TreeNode<T>> l = TreeUtils.getNodeChildren(node);

                    if(node.isExpand()){
                        dataToBind.removeAll(l);
                        node.setExpand(false);
                        notifyItemRangeRemoved(position,l.size());
                    }else{
                        dataToBind.addAll(position + 1,l);
                        node.setExpand(true);
                        notifyItemRangeInserted(position,l.size());
                    }
                    node.setExpand(!node.isExpand());

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
        helper.itemView.setPadding(DpUtil.dip2px(helper.itemView.getContext(), 30) * level, 0, 0, 0);
    }

    public void setOnTreeClickedListener(OnTreeClickedListener onTreeClickedListener) {
        this.onTreeClickedListener = onTreeClickedListener;
    }
}
