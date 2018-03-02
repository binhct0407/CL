package jp.couplink.app.retrofit

import jp.couplink.app.database.entities.Data
import jp.couplink.app.like_thankyou.LikeUnreadObj
import jp.couplink.app.message.model.RoomMatchedObj
import jp.couplink.app.message.model.RoomMessagingObj
import jp.couplink.app.mocking.DataTargetUser
import jp.couplink.app.mocking.UserObject
import jp.couplink.app.popup.model.MatchedObj
import jp.couplink.app.popup.model.Popup
import jp.couplink.app.public_profile.model.FavoriteUserObj
import jp.couplink.app.public_profile.model.LikeResponse
import jp.couplink.app.public_profile.model.LikeUserObj
import jp.couplink.app.purchase.model.PurchaseCoin
import jp.couplink.app.purchase.model.PurchaseMembership
import jp.couplink.app.retrofit.model.AuthObject
import jp.couplink.app.retrofit.model.DataPublic
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by BinhTran on 2/2/2018.
 */
interface RetrofitAPI {
    @GET("/api/v2/auth")
    fun getAuthToken(@Header("Cookie") sessionIdAndToken: String): Call<AuthObject>

    @GET("/api/v2/users")
    fun searchUser(
            @QueryMap options: Map<String, String>): Call<Data>

    @GET("/api/v2/users")
    fun getUsers(): Call<Data>

    @POST("/api/v2/likes/")
    fun likeToId(@Body mLikeUserObj: LikeUserObj)

            : Call<LikeResponse>


    @GET("api/v2/likes/receive")
    fun getLikeReceives(): Call<List<UserObject>>


    @GET("/api/v2/users")
    fun getLikeReceivesWithFilter(
            @QueryMap options: Map<String, String>): Call<List<UserObject>>


    @GET("api/v2/likes/sent")
    fun getLikeSents(@QueryMap options: Map<String, String>): Call<DataTargetUser>


    @POST("/api/v2/favorites/")
    fun favoritetoID(@Body mFavorite: FavoriteUserObj)

            : Call<Any>

    @DELETE("/api/v2/favorites/{id}")
    fun deleteFavorite(@Path("id") id: String)

            : Call<Any>

    @GET("api/v2/favorites")
    fun getFavorites(): Call<DataTargetUser>

    @GET("/api/v2/users/{id}")
    fun getProfileDetail(@Path("id") id: String): Call<DataPublic>

    @GET("api/v2/purchases/memberships/android")
    fun getPurchaseMemberShips(): Call<PurchaseMembership>

    @GET("api/v2/purchases/coins/android")
    fun getPurchaseCoins(): Call<PurchaseCoin>

    @GET("api/v2/rooms/matched")
    fun getMatchedRoom(): Call<RoomMatchedObj>

    @GET("api/v2/rooms/matched/")
    fun getMatchingRoomList(@QueryMap options: Map<String, String>): Call<RoomMatchedObj>

    @GET("api/v2/rooms/messaging")
    fun getMessagingRoom(): Call<RoomMessagingObj>

    @GET("api/v2/info-dummy")
    fun getPopupList(): Call<Popup>

    @GET("api/v2/likes/receive_unread")
    fun getLikeUnreadResource(): Call<LikeUnreadObj>

    @GET("/api/v2/matchings/")
    fun getMatchedUnread(): Call<MatchedObj>


}