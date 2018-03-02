package jp.couplink.app.fragment.dialog

import android.support.v4.app.DialogFragment

/**
 * ローディング中のプログレスダイアログ
 * Created by yusukemizukoshi on 2017/02/22.
 */
class LoadingDialogFragment : DialogFragment() {
    companion object {
        val instance: ProgressDialogFragment
            get() = ProgressDialogFragment.getInstance("ロード中...")
    }
}
