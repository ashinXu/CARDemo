package como.example.wangzhe.myrv;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private PeopleAdapter mAdapter;
    private int mLastVisibleItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }


    private void initView() {

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sl);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread() {
                    @Override
                    public void run() {

                        mList.add(0, "这是主演啊");
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    //加载更多数据
                    //mSwipeRefreshLayout.setRefreshing(true);
                    getMoreData();

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.v("ashin", "onScrolled = ");
                mLastVisibleItemPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
                Log.v("ashin", "mLastVisibleItemPosition = " + mLastVisibleItemPosition);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PeopleAdapter(this, mList);
        mAdapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener() {
            @Override
            public void onCLick(int position) {
                Toast.makeText(MainActivity.this, "item点击", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getMoreData() {

        for (int i = 0; i < 5; i++) {
            mList.add("这是最新路人" + i + "号");
        }


        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);

    }


    private void initData() {

        mList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            mList.add("这是路人" + i + "号");
        }


    }
}
