package basetreeview.ahao.com.basetreeviewpro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ahao.basetreeview.model.TreeNode;
import com.ahao.basetreeview.util.TreeDataUtils;

import java.util.ArrayList;
import java.util.List;

public class MultiLayoutTreeActivity  extends AppCompatActivity{

    RecyclerView recyclerView;

    MyMultiLayoutTreeAdapter adapter;

    private List<TreeNode<File>> allData = new ArrayList<>();

    private List<TreeNode<File>> dataToBind = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multi_layout_tree);
        initUI();
        initData();
        initEvent();
    }

    private void initEvent() {
        recyclerView.setAdapter(adapter);

        adapter.setOnTreeClickedListener(new MyMultiLayoutTreeAdapter.OnTreeClickedListener<File>() {
            @Override
            public void onNodeClicked(View view, TreeNode<File> node, int position) {
                ImageView icon = view.findViewById(R.id.level_icon);
                if(node.isExpand()){
                    icon.setImageResource(R.drawable.tree_icon_collapse);
                }else{
                    icon.setImageResource(R.drawable.tree_icon_expand);
                }

            }

            @Override
            public void onLeafClicked(View view, TreeNode<File> node, int position) {

            }
        });
    }

    private void initData() {
        dataToBind.clear();
        dataToBind.addAll(TreeDataUtils.convertDataToTreeNode(DataSource.getFiles(),1));
        adapter = new MyMultiLayoutTreeAdapter(dataToBind);
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
