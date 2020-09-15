package doctor.dococean.com.doctorapp.utils;

import android.util.Log;

import doctor.dococean.com.doctorapp.BuildConfig;

/**
 * Created by nagendrasrivastava on 26/06/16.
 */
public class DocOceanLogger {
    
    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.d(tag, message);
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.e(tag, message);
    }

    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.i(tag, message);
    }

    public static void v(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.v(tag, message);
    }
}
