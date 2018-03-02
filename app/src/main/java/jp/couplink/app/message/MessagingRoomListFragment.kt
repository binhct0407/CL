package jp.couplink.app.message

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.firebase.client.ChildEventListener
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError

import java.util.ArrayList
import java.util.HashMap

import jp.couplink.R
import jp.couplink.app.database.entities.FriendChat
import jp.couplink.app.mocking.PublicProfile_Mocking_Constant
import jp.couplink.app.retrofit.FirebaseAPI
import kotlinx.android.synthetic.main.layout_message_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by BinhTran on 12/5/2017.
 */

class MessagingRoomListFragment : Fragment() {
    private var imageIDs: ArrayList<String>? = null
    private var details: ArrayList<String>? = null

    var usernames: ArrayList<String> = ArrayList()
    var passwords: ArrayList<String> = ArrayList()
    internal var user = "binhtran"
    internal var pass = "test123"


    var adapter: MessageListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("aaaaa", "onCreate")


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("aaaaa", "onCreateView")
        val v = inflater.inflate(R.layout.layout_message_list, container, false)


        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.setAndroidContext(context!!)
        //setup RecyclerView+
        val layoutManager = LinearLayoutManager(context)
        fragment_messagelist_user_recyclerView.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
        fragment_messagelist_user_recyclerView.layoutManager = layoutManager

        //setup adapter
        mocking_logged()
        adapter = MessageListAdapter(imageIDs!!, usernames, details!!)

        fragment_messagelist_user_recyclerView.adapter = adapter


        fragment_messagelist_user_recyclerView.addOnItemTouchListener(RecyclerTouchListener(context, fragment_messagelist_user_recyclerView, object : RecyclerTouchListener.ClickListener {

            override fun onClick(view: View, position: Int) {

                UserDetails.chatWith = usernames[position]

                val fragment: ChatFragment
                fragment = ChatFragment()


                fragmentManager!!.beginTransaction()
                        .replace(R.id.message_main_fragment, fragment)
                        .addToBackStack(null)
                        .commit()


            }


        }))
    }

    internal fun mocking_logged() {
        imageIDs = ArrayList()
        usernames = ArrayList()
        details = ArrayList()
        passwords = ArrayList()
        val pd = ProgressDialog(context)
        pd.setMessage("Loading...")
        pd.show()
        val retrofit = Retrofit.Builder().baseUrl(FirebaseAPI.FIREBASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val firebaseAPI = retrofit.create(FirebaseAPI::class.java)
        val mFriendChats = firebaseAPI.friendChats
        mFriendChats.enqueue(object : Callback<HashMap<String, FriendChat>> {
            override fun onResponse(call: Call<HashMap<String, FriendChat>>, response: retrofit2.Response<HashMap<String, FriendChat>>) {
                val mFriendIterator = response.body()!!.keys.iterator()
                while (mFriendIterator.hasNext()) {
                    val key = mFriendIterator.next()
                    val friendChat = response.body()!![key]
                    usernames.add(key)
                    passwords.add(friendChat!!.password)
                }
                if (!valid())
                    Toast.makeText(context, "incorrect username/password", Toast.LENGTH_SHORT).show()

                for (i in usernames.indices) {
                    imageIDs!!.add(PublicProfile_Mocking_Constant.a[i % 3])
                    details!!.add(getLastMessageWith(usernames[i]))

                }
                adapter!!.notifyDataSetChanged()

                pd.dismiss()

            }

            override fun onFailure(call: Call<HashMap<String, FriendChat>>, t: Throwable) {

            }
        })


    }

    override fun onResume() {
        super.onResume()
        Log.d("aaaaa", "onResume")


    }

    override fun onPause() {
        super.onPause()
        Log.d("aaaaa", "onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("aaaaa", "onDestroyView")
    }

    override fun onStop() {
        super.onStop()
        Log.d("aaaaa", "onStop")
    }

    private fun valid(): Boolean {
        var check = false
        for (i in usernames.indices) {
            if (usernames[i] == user && passwords[i] == pass) {
                check = true
                break
            }
        }
        return check
    }

    ///get last message
    private fun getLastMessageWith(name: String): String {
        val result = ""
        val reference1 = Firebase("https://realtimemessage-42194.firebaseio.com/messages/" + "binhtran" + "_" + name)
        reference1.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val map = dataSnapshot.getValue(Map::class.java)
                val message = map["message"].toString()

                try {

                } catch (e: Exception) {
                    val pre = context!!.getSharedPreferences("message_preference", Context.MODE_PRIVATE)
                    val editor = pre.edit()
                    editor.putBoolean("check_" + name, true)
                    editor.putString(name, message)

                    editor.commit()
                }


            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {

            }

            override fun onCancelled(firebaseError: FirebaseError) {

            }
        })
        return result
    }


}
