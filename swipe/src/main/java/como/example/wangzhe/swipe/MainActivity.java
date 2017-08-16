package como.example.wangzhe.swipe;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MySwipeView mSwipeRefreshLayout;
    private ListView mListView;
    private List<String> mList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSwipeRefreshLayout = (MySwipeView) findViewById(R.id.srl);

        initData();

        mListView = (ListView) findViewById(R.id.lv);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mList);

        mListView.setAdapter(mAdapter);

        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.progress);
        // 设置下拉进度的主题颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // 开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.add(0, "我是天才" + random.nextInt(100) + "号");
                        mAdapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.this, "刷新了一条数据", Toast.LENGTH_SHORT).show();

                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1200);
            }
        });

        mSwipeRefreshLayout.setOnLoadMoreListener(new MySwipeView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });

    }

    private void loadMoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mList.clear();
                mList.addAll(DataResource.getMoreData());
                Toast.makeText(MainActivity.this, "加载了" + 20 + "条数据", Toast.LENGTH_SHORT).show();

                // 加载完数据设置为不加载状态，将加载进度收起来
                mSwipeRefreshLayout.setLoading(false);
            }
        }, 2000);
    }

    private void initData() {

        mList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            mList.add("我是天才" + i + "号");
        }

    }

    public static class DataResource {
        private static List<String> datas = new ArrayList<>();
        private static int page = 0;

        public static List<String> getData() {
            page = 0;
            datas.clear();
            for (int i = 0; i < 15; i++) {
                datas.add("我是天才" + i + "号");
            }

            return datas;
        }

        public static List<String> getMoreData() {
            page = page + 1;
            for (int i = 20 * page; i < 20 * (page + 1); i++) {
                datas.add("我是天才" + i + "号");
            }

            return datas;
        }
    }
}
