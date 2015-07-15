package ryancheng.beauty;

import android.app.Application;

public class BeautyApp extends Application {
    private BeautyComponent beautyComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        beautyComponent = DaggerBeautyComponent.builder()
                .beautyModule(new BeautyModule(this))
                .build();
    }

    public BeautyComponent getBeautyComponent() {
        return beautyComponent;
    }
}
