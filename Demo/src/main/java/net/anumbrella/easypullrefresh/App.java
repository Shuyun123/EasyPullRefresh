package net.anumbrella.easypullrefresh;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * author：anumbrella
 * Date:16/8/4 下午5:25
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
