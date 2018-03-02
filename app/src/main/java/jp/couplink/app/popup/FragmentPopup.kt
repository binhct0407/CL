package jp.couplink.app.popup


import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import jp.couplink.R
import jp.couplink.app.message.RecyclerTouchListener
import jp.couplink.app.popup.adapter.ModalButtonAdapter
import jp.couplink.app.utils.ResourceUtils
import kotlinx.android.synthetic.main.fragment_popup.*

/**
 * Created by BinhTran on 2/12/2018.
 */
class FragmentPopup : DialogFragment() {
    fun newInstance(): FragmentPopup {
        val fragment = FragmentPopup()
        val args = Bundle()

        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popup, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        /*
        var mobj = arguments!!.getParcelable<Popup>("popupdata")


        Glide.with(fragment_popup_image.context)
                .load(mobj!!.data!![2]!!.data!!.image_url.toString())
                .into(fragment_popup_image!!)
        var list_btn_url: MutableList<String> = mutableListOf()
        val size: Int = mobj!!.data!![2]!!.data!!.button!!.size
        for (i in 0..size - 1) {
            list_btn_url.add(mobj!!.data!![2].data!!.button!![i]!!.image.toString())
        }

        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        fragment_popup_list_button.setLayoutManager(layoutManager)

        val adapter = ModalButtonAdapter(list_btn_url)
        fragment_popup_list_button.adapter = adapter
        */

        var imageName: String = arguments!!.getString("typepopup_image")
        var imageBtn: String? = ""
        var btn_url: String? = ""
        try {
            imageBtn = arguments!!.getString("typepopup_button")
            btn_url = arguments!!.getString("typepopup_button_url")
        } catch (e: Exception) {

        }

        Glide.with(fragment_popup_image.context).load(ResourceUtils.getImage(activity!!, imageName)).into(fragment_popup_image)
        var list_btn_url: MutableList<String> = mutableListOf()
        if (imageBtn != null && imageBtn!!.isNotEmpty()) {
            list_btn_url.add(imageBtn)
        }


        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        fragment_popup_list_button.setLayoutManager(layoutManager)
        val adapter = ModalButtonAdapter(list_btn_url)
        fragment_popup_list_button.adapter = adapter
        fragment_popup_list_button.addOnItemTouchListener(RecyclerTouchListener(activity!!, fragment_popup_list_button, object : RecyclerTouchListener.ClickListener {

            override fun onClick(view: View, position: Int) {

                if (btn_url != null && btn_url.isNotEmpty()) {
                    var fragment: FragmentPopupTypePopupButton
                    fragment = FragmentPopupTypePopupButton()
                    var bundle: Bundle
                    bundle = Bundle()
                    bundle.putString("poup_button_url", btn_url)
                    fragment.arguments=bundle
                    fragmentManager.beginTransaction().replace(R.id.fragment_search_main, fragment).addToBackStack(null).commit()

                }

                dismiss()

            }


        }))


    }


}