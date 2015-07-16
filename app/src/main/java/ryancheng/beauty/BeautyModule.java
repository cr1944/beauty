package ryancheng.beauty;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Create time: 2015/7/15.
 */
@Module
public class BeautyModule {
    private Application application;

    public BeautyModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    SQLiteOpenHelper provideOpenHelper(Application application) {
        return new DbOpenHelper(application);
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return SqlBrite.create();
    }

    @Provides
    @Singleton
    BriteDatabase provideBriteDatabase(SqlBrite sqlBrite, SQLiteOpenHelper sqLiteOpenHelper) {
        return sqlBrite.wrapDatabaseHelper(sqLiteOpenHelper);
    }

    @Provides
    @Singleton
    Api provideApi() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.HOST)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(Api.class);
    }
}
