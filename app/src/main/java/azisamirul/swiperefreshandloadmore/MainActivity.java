package azisamirul.swiperefreshandloadmore;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private LoadMoreListView lvItem;
    private SwipeRefreshLayout swipeMain;
    private LinkedList<String> list;
    private ArrayAdapter<String> adapter;
    private int maxPage = 5;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItem = (LoadMoreListView) findViewById(R.id.lv_item);
        swipeMain = (SwipeRefreshLayout) findViewById(R.id.swipe_main);
        list = new LinkedList<>();
        populateDefaultData();

        lvItem.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (currentPage < maxPage) {
                    new FakeLoadmoreAsync().execute();
                } else {
                    lvItem.onLoadMoreComplete();
                }
            }
        });
        swipeMain.setOnRefreshListener(this);
    }

    private void populateDefaultData() {
        String[] androidVersion = new String[]{
                "Not Apple", "Not Blackberry", "Cupcake", "Donut",
                "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice cream Sandwich",
                "jelly bean", "Kitkat", "Lollipop", "M Preview", "N (Coming Soon)"
        };

        for (int i = 0; i < androidVersion.length; i++) {
            list.add(androidVersion[i]);
        }

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        lvItem.setAdapter(adapter);


    }

    @Override
    public void onRefresh() {
        new FakePullRefreshAsync().execute();
    }

    private class FakeLoadmoreAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(4000);
            } catch (Exception e) {

            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            populateLoadmoreData();
            currentPage += 1;

        }
    }

    private void populateLoadmoreData(){
        String loadmoreText="Added on loadmore";
        for(int i=0;i<10;i++){
            list.addLast(loadmoreText);
        }
        adapter.notifyDataSetChanged();
        lvItem.onLoadMoreComplete();
        lvItem.setSelection(list.size()-11);
    }
private class FakePullRefreshAsync extends AsyncTask<Void,Void,Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Thread.sleep(4000);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        populatePullRefreshData();
    }
}

    private void populatePullRefreshData() {
        String swipePullRefreshText = "Added After Swipe Layout";
        for (int i = 0; i < 5; i++) {
            list.addFirst(swipePullRefreshText);

        }
        swipeMain.setRefreshing(false);
        adapter.notifyDataSetChanged();
        lvItem.setSelection(0);
    }

}
