package jp.couplink.app.like_thankyou

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.WindowManager

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import org.json.JSONArray

import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

/**
 * Created by janisharali on 21/08/16.
 */
class Utils {

    private val TAG = "Utils"


    companion object {
        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }

        fun getDisplaySize(windowManager: WindowManager): Point {
            try {
                if (Build.VERSION.SDK_INT > 16) {
                    val display = windowManager.defaultDisplay
                    val displayMetrics = DisplayMetrics()
                    display.getMetrics(displayMetrics)
                    return Point(displayMetrics.widthPixels, displayMetrics.heightPixels)
                } else {
                    return Point(0, 0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return Point(0, 0)
            }

        }
    }


}
