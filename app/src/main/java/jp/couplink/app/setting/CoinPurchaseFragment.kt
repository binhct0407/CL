package jp.couplink.app.setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails

import jp.couplink.R
import kotlinx.android.synthetic.main.fragment_coin_purchase.*

/**
 * Created by BinhTran on 1/4/2018.
 */

class CoinPurchaseFragment : Fragment() {
    companion object {

        val ACTIVITY_NUMBER: String = "activity_num"
        val LOG_TAG: String = "iabv3"

        // PRODUCT & SUBSCRIPTION IDS
        val PRODUCT_ID: String = "membership_vol4.1"

        val SUBSCRIPTION_ID: String = "android.test.purchased"
        val LICENSE_KEY: String? = null; // PUT YOUR MERCHANT KEY HERE;
        // put your Google merchant id here (as stated in public profile of your Payments Merchant Center)
        // if filled library will provide protection against Freedom alike Play Market simulators
        val MERCHANT_ID: String? = null

        var bp: BillingProcessor? = null
        var readyToPurchase: Boolean = false


    }
    // SAMPLE APP CONSTANTS


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coin_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coin_purchase_actionbar_txtBack = view.findViewById<TextView>(R.id.coin_purchase_actionbar_txtBack)

        coin_purchase_actionbar_txtBack.setOnClickListener {
            val manager = fragmentManager
            val count = manager!!.backStackEntryCount

            if (count == 0) {
                activity!!.onBackPressed()
            } else {
                manager.popBackStack()
            }
        }
        if (!BillingProcessor.isIabServiceAvailable(context)) {
            showToast("In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16")
        }

        bp = BillingProcessor(context, LICENSE_KEY, MERCHANT_ID, object : BillingProcessor.IBillingHandler {
            override fun onProductPurchased(productId: String, details: TransactionDetails?) {
                showToast("onProductPurchased: " + productId)
            }

            override fun onBillingError(errorCode: Int, error: Throwable?) {
                showToast("onBillingError: " + Integer.toString(errorCode))
            }

            override fun onBillingInitialized() {
                showToast("onBillingInitialized")
                readyToPurchase = true
            }

            override fun onPurchaseHistoryRestored() {
                showToast("onPurchaseHistoryRestored")
                for (sku in bp!!.listOwnedProducts())
                    Log.d(LOG_TAG, "Owned Managed Product: " + sku)
                for (sku in bp!!.listOwnedSubscriptions())
                    Log.d(LOG_TAG, "Owned Subscription: " + sku)
            }
        })

        btn_coin_100.setOnClickListener(View.OnClickListener { purchase() })

    }

    fun purchase() {
        bp!!.purchase(context as Activity?, PRODUCT_ID);
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!bp!!.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data)
    }

}
