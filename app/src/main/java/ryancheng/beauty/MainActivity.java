package ryancheng.beauty;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements Action1<List<Entity>> {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.progressbar)
    ContentLoadingProgressBar progressBar;
    BeautyComponent beautyComponent;
    private CompositeSubscription subscriptions;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BeautyApp app = (BeautyApp) getApplication();
        beautyComponent = app.getBeautyComponent();
        subscriptions = new CompositeSubscription();
        mainAdapter = new MainAdapter(this);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainAdapter);
        query();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscriptions != null)
            subscriptions.unsubscribe();
    }

    private void query() {
        progressBar.show();
        subscriptions.add(beautyComponent.getBriteDatabase()
                .createQuery(Entity.TABLE, Entity.LIST_QUERY)
                .map(Entity.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this));
        subscriptions.add(beautyComponent.getApi()
                .mvtp("10")
                .map(Entity.MAP_NET)
                .doOnNext(new Action1<List<Entity>>() {
                    @Override
                    public void call(List<Entity> entities) {
                        beautyComponent.getBriteDatabase()
                                .delete(Entity.TABLE, null, null);
                        for (Entity entity : entities) {
                            beautyComponent.getBriteDatabase()
                                    .insert(Entity.TABLE, entity.toContentValues());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this));
    }

    @Override
    public void call(List<Entity> data) {
        mainAdapter.setData(data);
        progressBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
