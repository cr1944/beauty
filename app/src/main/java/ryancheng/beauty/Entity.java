package ryancheng.beauty;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

/**
 * Create time: 2015/7/15.
 */
public class Entity implements Parcelable {
    public static final String TABLE = "mvtp";

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String PIC_URL = "picUrl";
    public static final String DESCRIPTION = "description";
    public static final String URL = "url";

    public static final String CREATE_TABLE = ""
            + "CREATE TABLE " + TABLE + "("
            + ID + " INTEGER NOT NULL PRIMARY KEY,"
            + TITLE + " TEXT NOT NULL,"
            + PIC_URL + " TEXT NOT NULL,"
            + DESCRIPTION + " TEXT,"
            + URL + " TEXT"
            + ")";

    public static final String LIST_QUERY = "SELECT * FROM "
            + TABLE;

    public static Func1<SqlBrite.Query, List<Entity>> MAP = new Func1<SqlBrite.Query, List<Entity>>() {
        @Override public List<Entity> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Entity> values = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    Entity entity = new Entity();
                    entity.id = Db.getLong(cursor, ID);
                    entity.title = Db.getString(cursor, TITLE);
                    entity.picUrl = Db.getString(cursor, PIC_URL);
                    entity.description = Db.getString(cursor, DESCRIPTION);
                    entity.url = Db.getString(cursor, URL);
                    values.add(entity);
                }
                return values;
            } finally {
                cursor.close();
            }
        }
    };

    public static Func1<Map<String, String>, List<Entity>> MAP_NET = new Func1<Map<String, String>, List<Entity>>() {
        @Override
        public List<Entity> call(Map<String, String> map) {
            List<Entity> values = new ArrayList<>();
            for (String key : map.keySet()) {
                if (!key.equals("code") && !key.equals("msg")) {
                    String value = map.get(key);
                    values.add(new Gson().fromJson(value, Entity.class));
                }
            }
            return values;
        }
    };

    public long id;
    public String title;
    public String picUrl;
    public String description;
    public String url;

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(PIC_URL, picUrl);
        values.put(DESCRIPTION, description);
        values.put(URL, url);
        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.picUrl);
        dest.writeString(this.description);
        dest.writeString(this.url);
    }

    public Entity() {
    }

    protected Entity(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.picUrl = in.readString();
        this.description = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>() {
        public Entity createFromParcel(Parcel source) {
            return new Entity(source);
        }

        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };
}
