package jp.couplink.app.message

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.couplink.R
import kotlinx.android.synthetic.main.layout_event_list_fragment.*

/**
 * Created by BinhTran on 12/5/2017.
 */

class EventListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("aaaaa", "onCreateView")
        return inflater.inflate(R.layout.layout_event_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_find_event.setOnClickListener(View.OnClickListener { find_event_firsttime() })
    }

    fun find_event_firsttime() {

        val fragment: FragmentFindEvent
        fragment = FragmentFindEvent()


        fragmentManager!!.beginTransaction()
                .replace(R.id.message_main_fragment, fragment)
                .addToBackStack(null)
                .commit()
    }


}
