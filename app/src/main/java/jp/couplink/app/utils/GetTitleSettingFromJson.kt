package jp.couplink.app.utils

import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by BinhTran on 12/7/2017.
 */

class GetTitleSettingFromJson {
    companion object {
        fun getTitle(sourceJson: String, titleType: String): ArrayList<String> {
            val temp = ArrayList<String>()
            val jsonResponse: JSONObject
            try {

                jsonResponse = JSONObject(sourceJson)
                val movies = jsonResponse.getJSONArray(titleType)
                for (i in 0 until movies.length()) {
                    val movie = movies.getJSONObject(i)
                    temp.add(movie.get("name").toString())

                }


            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            return temp

        }

    }


}
