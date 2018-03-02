package jp.couplink.app.database.entities

import java.io.Serializable

/**
 * Created by BinhTran on 12/5/2017.
 */

class CLUser : Serializable {

    var id: Int = 0
    var job: String? = null
    var body_shape_type: String? = null
    var height: String? = null
    var anuual_salary_range: String? = null
    var residence_prefecture_name: String? = null
    var has_participated_event: String? = null
    var type: String? = null
    var url: String? = null
    var age: Int = 0
    var active_status: Boolean = false
    var is_new: Boolean = false
    var participated_the_same_event_with_me: Boolean = false
    var name: String? = null
    var top_image: TopImage? = null
    var sub_images: Array<Image>? = null
    var is_liked_by_me: Boolean = false
    var is_liking_me: Boolean = false
    var common_point: Int = 0
    var hobby: String? = null
    var self_introduction: String? = null
    var bg_theme:String?=""


}
