package jp.couplink.app.message

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.firebase.client.ChildEventListener
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import jp.couplink.R
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.include_chatfragment_messagearea.*
import java.util.*


class ChatFragment : Fragment() {
    var person_pos: Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.setAndroidContext(context)
        var reference1 = Firebase("https://realtimemessage-42194.firebaseio.com/messages/" + "binhtran" + "_" + UserDetails.chatWith)
        var reference2 = Firebase("https://realtimemessage-42194.firebaseio.com/messages/" + UserDetails.chatWith + "_" + "binhtran")
        chatfragment_messageArea_edittext!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().isEmpty()) {
                    chatfragment_messageArea_sendButton!!.setTextColor(Color.parseColor("#e88994"))
                } else
                    chatfragment_messageArea_sendButton!!.setTextColor(Color.parseColor("#ABABAB"))

            }

            override fun afterTextChanged(s: Editable) {

            }
        })


        chatfragment_messageArea_sendButton!!.setOnClickListener {
            val messageText = chatfragment_messageArea_edittext!!.text.toString()

            if (messageText != "") {
                val map = HashMap<String, String>()
                map.put("message", messageText)
                map.put("user", "binhtran")
                reference1.push().setValue(map)
                reference2.push().setValue(map)
                chatfragment_messageArea_edittext!!.setText("")
                /*
                    mLastMessageListener.onLastMessageChange(person_pos, messageText);
                    setmLastMessageListener(mLastMessageListener);
                    */

            }
        }

        reference1.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val map = dataSnapshot.getValue(Map::class.java)
                val message = map["message"].toString()
                val userName = map["user"].toString()
                try {
                    val pre = context!!.getSharedPreferences("message_preference", Context.MODE_PRIVATE)
                    val editor = pre.edit()
                    editor.putBoolean("check_" + UserDetails.chatWith, true)
                    editor.putString(UserDetails.chatWith, message)
                    editor.commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }


                if (userName == "binhtran") {
                    try {
                        addMessageBox("You:-\n" + message, 1)
                    } catch (e: Exception) {

                    }

                } else {
                    try {
                        addMessageBox(UserDetails.chatWith + ":-\n" + message, 2)
                    } catch (e: Exception) {

                    }

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
        chatfragment_back.setOnClickListener(View.OnClickListener { backToFragmentMatching() })
    }


    fun backToFragmentMatching() {
        val manager = fragmentManager
        val count = manager!!.backStackEntryCount

        if (count == 0) {
            activity!!.onBackPressed()
        } else {
            manager.popBackStack()
        }

    }


    fun addMessageBox(message: String, type: Int) {
        val textView = TextView(context)
        textView.text = message

        val lp2 = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp2.weight = 1.0f

        if (type == 1) {
            lp2.gravity = Gravity.LEFT
            textView.setBackgroundResource(R.drawable.bubble_in)
        } else {
            lp2.gravity = Gravity.RIGHT
            textView.setBackgroundResource(R.drawable.bubble_out)
        }
        textView.layoutParams = lp2
        chatfragment_layout1!!.addView(textView)
        chatfragment_scrollView!!.fullScroll(View.FOCUS_DOWN)
        try {
            chatfragment_scrollView!!.post { chatfragment_scrollView!!.fullScroll(View.FOCUS_DOWN) }
        } catch (e: Exception) {

        }

    }


    fun onChatwithFriend(person_position: Int) {
        person_pos = person_position
    }
}