package ryancheng.beauty;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import rx.Observable;

public interface Api {
    @GET("/txapi/mvtp/meinv")
    @Headers("apikey: " + Config.API_KEY)
    Observable<Map<String, Object>> mvtp(@Query("num") String num);
}
