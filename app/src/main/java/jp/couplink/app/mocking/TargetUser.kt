package jp.couplink.app.mocking

import jp.couplink.app.like.model.ImageTargetUser

/**
 * Created by BinhTran on 2/2/2018.
 */
class TargetUser {
    var id: Int = 0
    var name: String? = null
    var gender: String? = null
    var status: String? = null
    var images: Array<ImageTargetUser>? = null
    var self_introduction: String? = ""
    var same_event_with_me: Boolean = false
    var has_participated_event: Boolean = false
    var is_liked_by_me: Boolean = false
    var is_sent_greeting_by_me: Boolean = false
    var is_liking_me: Boolean = false
    var reported: Boolean = false
    var is_deleted: Boolean = false
    var age: Int = 0
    var residence_prefecture_name: String? = null
    var job: String? = null
    var bg_file_path: String? = null
    var bg_theme: String? = null
}