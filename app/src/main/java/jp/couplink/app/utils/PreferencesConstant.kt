package jp.couplink.app.utils

/**
 * Created by BinhTran on 12/8/2017.
 */

class PreferencesConstant {
    companion object {
        fun getPreferenceName(position: Int): String {
            var result = ""
            when (position) {
                0 -> result = "YEAR_OLD"
                1 -> result = "LOCATION"
                2 -> result = "JOB"
                3 -> result = "HEIGHT_PREFERENCE"
                4 -> result = "BODY_SHAPE_TYPE"
                5 -> result = "HOLIDAY_TYPE"
                6 -> result = "SMOKING_TYPE"
                7 -> result = "DRINKING_TYPE"
                8 -> result = "IMAGE_HISTOREY"
                9 -> result = "LAST_LOGIN_TYPE"
            }

            return result
        }
    }
}
