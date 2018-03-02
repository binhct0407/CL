package jp.couplink.app.purchase.coin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.couplink.R
import jp.couplink.app.activity.MainActivity
import jp.couplink.app.purchase.listener.GetPurchaseCoinListener
import jp.couplink.app.purchase.membership.PurchaseCoinButtonAdapter
import jp.couplink.app.purchase.membership.PurchaseImageAdapter
import jp.couplink.app.purchase.model.CoinPurchaseItem
import jp.couplink.app.purchase.model.PurchaseCoin
import kotlinx.android.synthetic.main.fragment_coin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by BinhTran on 2/5/2018.
 */
class FragmentCoinPage : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_coin, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPurchase(object : GetPurchaseCoinListener {
            override fun onSuccess(mPurchaseCoin: PurchaseCoin) {
                updateView(mPurchaseCoin)
            }
        })

    }


    private fun updateView(targetObject: PurchaseCoin) {

        var mList: MutableList<String>? = mutableListOf()

        //load button_image

        for (i in 0..targetObject!!.images!!.size - 1) {
            mList!!.add(targetObject!!.images!![i])
        }
        val mariatal_manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragment_coin_imgRecyclerView.layoutManager = mariatal_manager
        val adapter = PurchaseImageAdapter(mList)
        fragment_coin_imgRecyclerView.adapter = adapter

        //load button
        var mbtnList: MutableList<CoinPurchaseItem>? = mutableListOf()


        for (i in 0..targetObject!!.items!!.size - 1) {
            mbtnList!!.add(targetObject!!.items!![i])
        }
        val purchasebtn_layout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragment_coin_btnRecyclerView.layoutManager = purchasebtn_layout
        val btnAdapter = PurchaseCoinButtonAdapter(mbtnList)
        fragment_coin_btnRecyclerView.adapter = btnAdapter

    }

    private fun getPurchase(callback: GetPurchaseCoinListener) {
        if (MainActivity.Companion.isLoaded) {


            var mPurchase: Call<PurchaseCoin> = MainActivity.Companion.mRetrofitAPI!!.getPurchaseCoins()
            mPurchase.enqueue(object : Callback<PurchaseCoin> {
                override fun onResponse(call: Call<PurchaseCoin>, response: Response<PurchaseCoin>) {
                    val temp = response.body()
                    callback.onSuccess(temp!!)

                }

                override fun onFailure(call: Call<PurchaseCoin>, t: Throwable) {


                }
            })
        }

    }

}