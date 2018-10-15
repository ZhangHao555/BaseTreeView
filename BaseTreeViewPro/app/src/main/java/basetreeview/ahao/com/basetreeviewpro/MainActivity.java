package basetreeview.ahao.com.basetreeviewpro;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ahao.basetreeview.TreeNode;
import com.ahao.basetreeview.util.TreeUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "TreeActivity";
    RecyclerView recyclerView;

    MySingleLayoutTreeAdapter adapter;

    private List<TreeNode<File>> allData = new ArrayList<>();

    private List<TreeNode<File>> dataToBind = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initUI();
        initData();
        initEvent();
    }

    private void initEvent() {
        recyclerView.setAdapter(adapter);

        adapter.setOnTreeClickedListener(new MySingleLayoutTreeAdapter.OnTreeClickedListener<File>() {
            @Override
            public void onNodeClicked(View view, TreeNode<File> node, int position) {


            }

            @Override
            public void onLeafClicked(View view, TreeNode<File> node, int position) {

            }
        });
    }

    private void initData() {
        allData = TreeUtils.getSortedNodes(DataSource.getFiles(),-1,2);
        dataToBind.addAll(TreeUtils.filterNode(allData));
        adapter = new MySingleLayoutTreeAdapter(R.layout.view_tree_level_0,dataToBind,allData);
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
