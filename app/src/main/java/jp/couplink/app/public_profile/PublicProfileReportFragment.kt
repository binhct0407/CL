package jp.couplink.app.public_profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import jp.couplink.R
import kotlinx.android.synthetic.main.layout_public_proflie_report.*

/**
 * Created by BinhTran on 12/18/2017.
 */

class PublicProfileReportFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.layout_public_proflie_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        do_report.setOnClickListener(View.OnClickListener { doreport() })
    }

    fun doreport() {
        activity!!.onBackPressed()
    }


}
