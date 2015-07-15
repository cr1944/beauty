package ryancheng.beauty;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Create time: 2015/7/15.
 */
@Singleton
@Component(modules = BeautyModule.class)
public interface BeautyComponent {
    Application getApplication();
    Api getApi();
    SQLiteOpenHelper getSQLiteOpenHelper();
    SqlBrite getSqlBriter();
    BriteDatabase getBriteDatabase();
}
