package jp.couplink.app.utils;

import android.app.Application;

import com.growthpush.GrowthPush;
import com.growthpush.model.Environment;

import jp.couplink.BuildConfig;

/**
 * Created by smatsuda on 16/04/20.
 */
public class CouplinkApplication extends Application {


    @Override
    public void onCreate() {
        /** Called when the Application-class is first created. */
        super.onCreate();
        GrowthPush.getInstance().initialize(this, "PksT3Kr86frzuPMB", "qYtLoJKlpIjWQ5dIpDjK9x4X5WBzvBaQ",
                BuildConfig.DEBUG ? Environment.development : Environment.production);
        GrowthPush.getInstance().requestRegistrationId("804987520589");

    }


}
