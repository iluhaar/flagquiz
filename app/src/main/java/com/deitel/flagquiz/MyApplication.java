package com.deitel.flagquiz;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

public class MyApplication extends Application {
    public static final String TAG = "MyApplication";
    private static String defaultLanguage;

    @Override
    public void onCreate() {
        super.onCreate();

        defaultLanguage = Locale.getDefault().getLanguage();
        Log.d(TAG, "Initiated with language " + defaultLanguage);

        final String locale = getBaseContext().getResources().getConfiguration().locale.toString();
        Log.d(TAG, "baseContext.resources.configuration.locale is " + locale);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        final String lang = newConfig.locale.getLanguage();
        if (!lang.equals(defaultLanguage)) {
            defaultLanguage = lang;
            Log.d(TAG, "Language changed to " + defaultLanguage);
        } else {
            Log.d(TAG, "Language was not changed");
        }
    }


    public static void reloadApplication(Context ctx, Class startingActivity) {
        Intent intent = new Intent(ctx, startingActivity);
        int pendingIntentId = 123456;

        PendingIntent mPendingIntent = PendingIntent.getActivity(ctx, pendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        assert mgr != null;
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    public static void updateLanguage(Context ctx, String lang)
    {
        //Configuration cfg = new Configuration();
        Configuration cfg = ctx.getResources().getConfiguration();

        if (!TextUtils.isEmpty(lang)) {
            cfg.locale = new Locale(lang);
            Log.d(TAG, "Changing language to " + lang);
        } else {
            cfg.locale = Locale.getDefault();
            Log.d(TAG, "Language was not changed");
        }

        ctx.getResources().updateConfiguration(cfg, null);
    }
}
