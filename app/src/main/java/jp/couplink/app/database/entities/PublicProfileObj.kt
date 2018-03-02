package jp.couplink.app.database.entities

import com.google.gson.annotations.SerializedName
import jp.couplink.app.public_profile.model.CommonPoint
import jp.couplink.app.public_profile.model.DetailInforObj

/**
 * Created by BinhTran on 1/29/2018.
 */
class PublicProfileObj {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("name")
    val name: String? = null

    @SerializedName("gender")
    val gender: String? = null

    @SerializedName("age")
    val age: Int = 0

    @SerializedName("residence_prefecture")
    val residence_prefecture: String? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("last_active_at")
    val last_active_at: String? = null

    @SerializedName("has_participated_event")
    val has_participated_event: Boolean = false

    @SerializedName("same_event_with_me")
    val same_event_with_me: Boolean = false

    @SerializedName("images")
    var images = ArrayList<ImagePublic>()

    @SerializedName("has_active_photo_request")
    val has_active_photo_request: Boolean = false

    @SerializedName("self_introduction")
    val self_introduction: String? = null

    @SerializedName("common_points")
    var common_points = ArrayList<CommonPoint>()

    @SerializedName("profile_requests")
    var profile_requests = ArrayList<Any>()

    @SerializedName("has_profile_request_by_me")
    val has_profile_request_by_me: Boolean = false

    @SerializedName("basic_information")
    var basic_information = ArrayList<DetailInforObj>()

    @SerializedName("work_related")
    var work_related = ArrayList<DetailInforObj>()

    @SerializedName("hobby_related")
    var hobby_related = ArrayList<DetailInforObj>()

    @SerializedName("relationship_related")
    var relationship_related = ArrayList<DetailInforObj>()

    @SerializedName("is_favorited_by_me")
    val is_favorited_by_me: Boolean = false

    @SerializedName("is_liked_by_me")
    val is_liked_by_me: Boolean = false

    @SerializedName("is_liking_me")
    val is_liking_me: Boolean = false

    @SerializedName("has_greeting_by_me")
    val has_greeting_by_me: Boolean = false

    @SerializedName("greeting_text_by_me")
    val greeting_text_by_me: String? = null

    @SerializedName("bg_file_path")
    val bg_file_path: String? = null

    @SerializedName("bg_theme")
    val bg_theme: String? = null

    @SerializedName("reported")
    val reported: Boolean = false

    @SerializedName("membership")
    val membership: Boolean = false

    @SerializedName("is_deleted")
    val is_deleted: Boolean = false
}