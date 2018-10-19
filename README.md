# BaseTreeView
simple and quick way to create a tree view in android


![image](https://github.com/ZhangHao555/BaseTreeView/blob/master/BaseTreeViewPro/basetreeview.gif)   

## BaseTreeView使用

### BaseTreeView是什么？
BaseTreeView 是基于recyclerView和BRVAH，用于快速构造一个树形控件的工具。

### 使用方式
本工具支持 单布局（只有一个item布局文件），多布局（多个item布局文件）   
使用方式与recyclerView一样

### 特色
##### 只需要传入一个带有id，parentId的数据结构，即可自动构建成树，并高效地显示。

1、添加仓库地址
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
2、添加依赖
```
   implementation 'com.github.ZhangHao555:BaseTreeView:v1.2'
```
本项目是基于 appcompat 26.1.0编写，如果有冲突，可以使用强制依赖，例如
```
    implementation ('com.android.support:appcompat-v7:28.0.0'){
        force = true
    }
```
#### 单布局

1、model需要实现 NodeId id接口, 例如定义一个File
```
public class File implements NodeId {

    private String id;

    private String parentId;

    private String name;


    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPId() {
        return parentId;
    }

    public File( String name,String id, String parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
```


2、继承 SingleLayoutTreeAdapter
```
public class MySingleLayoutTreeAdapter extends SingleLayoutTreeAdapter<File> {

    public MySingleLayoutTreeAdapter(int layoutResId, @Nullable List<TreeNode<File>> dataToBind, @Nullable List<TreeNode<File>> allData) {
        super(layoutResId, dataToBind, allData);
    }

    @Override
    protected void convert(BaseViewHolder helper, TreeNode<File> item) {
        super.convert(helper,item);
        helper.setText(R.id.textView, item.getId() + ":" + item.getData().getName() + " level=" + item.getLevel());
        if (item.isLeaf()) {
            helper.setImageResource(R.id.level_icon, R.drawable.video);
        } else {
            if (item.isExpand()) {
                helper.setImageResource(R.id.level_icon, R.drawable.tree_icon_collapse);
            } else {
                helper.setImageResource(R.id.level_icon, R.drawable.tree_icon_expand);
            }
        }

    }

    @Override
    protected int getTreeNodeMargin() {
        return  DpUtil.dip2px(this.mContext, 30);
    }
}

```

3、声明并构造数据源、适配器
```

    private List<TreeNode<File>> allData = new ArrayList<>();

    private List<TreeNode<File>> dataToBind = new ArrayList<>();
    
    MySingleLayoutTreeAdapter adapter;
    
     private void initData() {
        allData = TreeDataUtils.getSortedNodes(DataSource.getFiles());
        dataToBind.addAll(TreeDataUtils.filterNode(allData));
        adapter = new MySingleLayoutTreeAdapter(R.layout.view_tree_level_0,dataToBind,allData);
    }
    
```

4、设置适配器
```
    private void initEvent() {
        recyclerView.setAdapter(adapter);

        adapter.setOnTreeClickedListener(new MySingleLayoutTreeAdapter.OnTreeClickedListener<File>() {
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
```

5、完整代码
```
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

public class SingleLayoutTreeActivity extends AppCompatActivity {

    private final static String TAG = "TreeActivity";
    RecyclerView recyclerView;

    MySingleLayoutTreeAdapter adapter;

    private List<TreeNode<File>> allData = new ArrayList<>();

    private List<TreeNode<File>> dataToBind = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_layout_tree);
        initUI();
        initData();
        initEvent();
    }

    private void initEvent() {
        recyclerView.setAdapter(adapter);

        adapter.setOnTreeClickedListener(new MySingleLayoutTreeAdapter.OnTreeClickedListener<File>() {
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
        allData = TreeDataUtils.getSortedNodes(DataSource.getFiles());
        dataToBind.addAll(TreeDataUtils.filterNode(allData));
        adapter = new MySingleLayoutTreeAdapter(R.layout.view_tree_level_0,dataToBind,allData);
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}

```


### 多布局
1、基本与单布局一致，只是构造数据的时候需要传入maxViewType。maxViewType表示获取node的最大层级，叶子结点的maxViewType 默认为-1。 根结点为0。   

如果maxViewType传入1，表示为要为层级为 -1，0，1的结点设置布局。  
如果传入2，表示为要为层级为 -1，0，1，2的结点设置布局。
```
 private void initData() {
        allData = TreeDataUtils.getSortedNodes(DataSource.getFiles(),1);
        dataToBind.addAll(TreeDataUtils.filterNode(allData));
        adapter = new MyMultiLayoutTreeAdapter(dataToBind,allData);
    }
```

2、adapter继承MultiLayoutTreeAdapter,必须要复写addItemTypes方法，添加布局。
```
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

    //如果构造数据源时，传入1，则需要为 -1，0，1添加布局
    //如果构造数据源时，传入2，则需要为-1，-0，1，2添加布局

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
        helper.setText(R.id.textView, "name:" + item.getData().getName());
        helper.setText(R.id.id, "id:" + item.getId());
        helper.setText(R.id.parentId, "pid:" + item.getPId());

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

```

构造数据时，传入的maxViewType，代表了最多可以添加几个布局。

##### 项目地址 
https://github.com/ZhangHao555/BaseTreeView

