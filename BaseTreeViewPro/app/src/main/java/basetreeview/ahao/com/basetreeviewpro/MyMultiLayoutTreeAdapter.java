package basetreeview.ahao.com.basetreeviewpro;

import com.ahao.basetreeview.adapter.MultiLayoutTreeAdapter;
import com.ahao.basetreeview.model.TreeNode;
import com.ahao.basetreeview.util.DpUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class MyMultiLayoutTreeAdapter extends MultiLayoutTreeAdapter<File> {

    public MyMultiLayoutTreeAdapter(List<TreeNode<File>> dataToBind, List<TreeNode<File>> allData) {
        super(dataToBind, allData);
    }

    @Override
    protected void addItemTypes() {
        addItemType(-1, R.layout.view_tree_leaf);
        addItemType(0, R.layout.view_tree_level_0);
        addItemType(1, R.layout.view_tree_level_1);
    }

    @Override
    protected void convert(BaseViewHolder helper, TreeNode<File> item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case -1:
                resolveLeaf(helper, item);
                break;
            case 0:
                resolveLevel0(helper, item);
                break;
            case 1:
                resolveLevel1(helper, item);
                break;
        }



    }

    private void resolveLevel1(BaseViewHolder helper, TreeNode<File> item) {
        helper.setText(R.id.textView, item.getData().getName());
        helper.setText(R.id.id, item.getId());
        helper.setText(R.id.parentId, item.getPId());

        if (item.isExpand()) {
            helper.setImageResource(R.id.level_icon, R.drawable.tree_icon_collapse);
        } else {
            helper.setImageResource(R.id.level_icon, R.drawable.tree_icon_expand);
        }
    }

    private void resolveLevel0(BaseViewHolder helper, TreeNode<File> item) {
        helper.setText(R.id.textView, "id:" + item.getId() + " pid:" + item.getPId() + " name:" + item.getData().getName());

        if (item.isExpand()) {
            helper.setImageResource(R.id.level_icon, R.drawable.tree_icon_collapse);
        } else {
            helper.setImageResource(R.id.level_icon, R.drawable.tree_icon_expand);
        }
    }

    private void resolveLeaf(BaseViewHolder helper, TreeNode<File> item) {
        helper.setText(R.id.textView, "id:" + item.getId() + " pid:" + item.getPId() + " name:" + item.getData().getName());
    }

    @Override
    protected int getTreeNodeMargin() {
        return DpUtil.dip2px(this.mContext, 30);
    }
}
