package jp.couplink.app.utils

import android.content.Context

/**
 * Created by sev_user on 11/28/2017.
 */

class ResourceUtils {
    companion object {
        fun getResourceId(context: Context, name: String, type: String): Int {
            try {
                return context.resources.getIdentifier(name, type, context.packageName)
            } catch (e: Exception) {
                e.printStackTrace()
                return -1
            }

        }

        fun getImage(context: Context, imageName: String): Int {

            return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName())
        }
    }


}
