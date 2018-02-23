package com.xiaoyezi.mvpdemo.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.xiaoyezi.mvpdemo.R;
import com.xiaoyezi.mvpdemo.data.url.UrlItem;
import com.xiaoyezi.mvpdemo.mvp.BaseActivityView;
import com.xiaoyezi.mvpdemo.mvp.main.MainContract;
import com.xiaoyezi.mvpdemo.mvp.main.MainPresenter;
import com.xiaoyezi.mvpdemo.ui.Toaster;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivityView<MainContract.Presenter> implements MainContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<UrlItem> urlItems = new ArrayList<>();
    private UrlItemAdapter urlItemAdapter;

    @BindView(R.id.et_url)
    EditText etUrl;

    @OnClick(R.id.ib_add)
    public void onClickAdd(View v) {
        String inputUrl = etUrl.getText().toString();
        Log.d(TAG, "onClick: " + inputUrl);

        presenter.saveUrl(inputUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_ble)
            Toaster.show("Not implement now!");

        return super.onOptionsItemSelected(item);
    }

    @BindView(R.id.lv_urls)
    ListView lvUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        showUrlItems(presenter.getAllUrlItems());
    }

    private void initView() {
        // ListView
        urlItemAdapter = new UrlItemAdapter(this, R.layout.url_item, urlItems, new UrlItemAdapter.OnClickListener() {

            @Override
            void onClickDelete(UrlItem urlItem) {
                presenter.deleteUrl(urlItem);
            }
        });
        lvUrls.setAdapter(urlItemAdapter);

        lvUrls.setOnItemClickListener((parent, view, position, id) -> {
            final UrlItem urlItem = urlItems.get(position);
            presenter.launchUrl(urlItem);
        });

        lvUrls.setOnItemLongClickListener((parent, view, position, id) -> {
            final UrlItem urlItem = urlItems.get(position);
            Toaster.showLong(urlItem.url);
            return true;
        });
    }

    @Override
    protected MainContract.Presenter getPresenterImpl() {
        return new MainPresenter();
    }

    @Override
    public void showUrlItems(List<UrlItem> urlItems) {
        Log.d(TAG, "showUrlItems: " + urlItems);
        this.urlItems.clear();
        this.urlItems.addAll(urlItems);
        urlItemAdapter.notifyDataSetChanged();
    }
}
