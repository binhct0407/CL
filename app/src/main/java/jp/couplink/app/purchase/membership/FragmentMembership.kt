package jp.couplink.app.purchase.membership

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import jp.couplink.R
import jp.couplink.app.activity.MainActivity
import jp.couplink.app.message.ChatFragment
import jp.couplink.app.message.RecyclerTouchListener
import jp.couplink.app.message.UserDetails
import jp.couplink.app.purchase.listener.GetPurchaseMembershipListener
import jp.couplink.app.purchase.model.MembershipPurchaseItem
import jp.couplink.app.purchase.model.PurchaseMembership
import jp.couplink.app.setting.CoinPurchaseFragment
import kotlinx.android.synthetic.main.actionbar_membership.*
import kotlinx.android.synthetic.main.fragment_membership.*
import kotlinx.android.synthetic.main.layout_message_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by BinhTran on 2/5/2018.
 */
class FragmentMembership : Fragment() {
    companion object {

        val ACTIVITY_NUMBER: String = "activity_num"
        val LOG_TAG: String = "iabv3"

        // PRODUCT & SUBSCRIPTION IDS
        val PRODUCT_ID: String = "android.test.purchased"

        val SUBSCRIPTION_ID: String = "android.test.purchased"
        val LICENSE_KEY: String? = null; // PUT YOUR MERCHANT KEY HERE;
        // put your Google merchant id here (as stated in public profile of your Payments Merchant Center)
        // if filled library will provide protection against Freedom alike Play Market simulators
        val MERCHANT_ID: String? = null

        var bp: BillingProcessor? = null
        var readyToPurchase: Boolean = false


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_membership, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPurchase(object : GetPurchaseMembershipListener {
            override fun onSuccess(mPurchaseMembership: PurchaseMembership) {
                updateView(mPurchaseMembership)
            }
        })
        membership_actionbar_txtBack.setOnClickListener(View.OnClickListener { back() })
        if (!BillingProcessor.isIabServiceAvailable(context)) {
            showToast("In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16")
        }

        FragmentMembership.bp = BillingProcessor(context, FragmentMembership.LICENSE_KEY, FragmentMembership.MERCHANT_ID, object : BillingProcessor.IBillingHandler {
            override fun onProductPurchased(productId: String, details: TransactionDetails?) {
                showToast("onProductPurchased: " + productId)
            }

            override fun onBillingError(errorCode: Int, error: Throwable?) {
                showToast("onBillingError: " + Integer.toString(errorCode))
            }

            override fun onBillingInitialized() {
                showToast("onBillingInitialized")
                FragmentMembership.readyToPurchase = true
            }

            override fun onPurchaseHistoryRestored() {
                showToast("onPurchaseHistoryRestored")
                for (sku in FragmentMembership.bp!!.listOwnedProducts())
                    Log.d(FragmentMembership.LOG_TAG, "Owned Managed Product: " + sku)
                for (sku in FragmentMembership.bp!!.listOwnedSubscriptions())
                    Log.d(FragmentMembership.LOG_TAG, "Owned Subscription: " + sku)
            }
        })


    }


    fun purchase(mProductID: String) {
        FragmentMembership.bp!!.purchase(context as Activity?, mProductID);
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!CoinPurchaseFragment.bp!!.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data)
    }


    private fun back() {
        val manager = fragmentManager
        val count = manager!!.backStackEntryCount

        if (count == 0) {
            activity!!.onBackPressed()
        } else {
            manager.popBackStack()
        }
    }


    private fun updateView(targetObject: PurchaseMembership) {

        var mList: MutableList<String>? = mutableListOf()

        //load button_image

        for (i in 0..targetObject!!.images!!.size - 1) {
            mList!!.add(targetObject!!.images!![i])
        }
        val mariatal_manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragment_membership_imgRecyclerView.layoutManager = mariatal_manager
        val adapter = PurchaseImageAdapter(mList)
        fragment_membership_imgRecyclerView.adapter = adapter

        //load button
        var mbtnList: MutableList<MembershipPurchaseItem>? = mutableListOf()


        for (i in 0..targetObject!!.items!!.size - 1) {
            mbtnList!!.add(targetObject!!.items!![i])
        }
        val purchasebtn_layout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragment_membership_btnRecyclerView.layoutManager = purchasebtn_layout
        val btnAdapter = PurchaseButtonAdapter(mbtnList)
        fragment_membership_btnRecyclerView.adapter = btnAdapter
        fragment_membership_btnRecyclerView.addOnItemTouchListener(RecyclerTouchListener(context, fragment_membership_btnRecyclerView, object : RecyclerTouchListener.ClickListener {

            override fun onClick(view: View, position: Int) {

                //  purchase(targetObject.items!!.get(position).product_name!!)
                purchase(PRODUCT_ID)

            }


        }))

    }

    private fun getPurchase(callback: GetPurchaseMembershipListener) {
        if (MainActivity.Companion.isLoaded) {

            var mPurchase: Call<PurchaseMembership> = MainActivity.Companion.mRetrofitAPI!!.getPurchaseMemberShips()
            mPurchase.enqueue(object : Callback<PurchaseMembership> {
                override fun onResponse(call: Call<PurchaseMembership>, response: Response<PurchaseMembership>) {
                    val temp = response.body()
                    callback.onSuccess(temp!!)

                }

                override fun onFailure(call: Call<PurchaseMembership>, t: Throwable) {


                }
            })
        }

    }

}