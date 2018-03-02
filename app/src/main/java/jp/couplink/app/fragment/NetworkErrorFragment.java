package jp.couplink.app.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import jp.couplink.R;
import jp.couplink.app.utils.FirebaseUtils;


/**
 * A simple {@link DialogFragment} subclass.
 */
public class NetworkErrorFragment extends DialogFragment {
    private static FirebaseUtils mFirebase;

    public enum AccessCode {
        CheckInitNetwork(1),
        OnStart(2),
        GetBaseUrlFailure(3),
        OnPageStarted(4),
        OnReceivedError(5),
        OnReceivedException(6),
        GetUserDataFailure(7);
        private final int id;

        private AccessCode(final int id) {
            this.id = id;
        }

        public int getInt() {
            return this.id;
        }
    }

    public interface NetworkErrorFragmentInterface {
        public interface onClickListener {
            void onReAccessButtonClick();
        }
    }

    private NetworkErrorFragmentInterface.onClickListener mListenerOnClick;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mFirebase = new FirebaseUtils(getActivity());
        Dialog dialog = new Dialog(getActivity(), R.style.NetworkErrorDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_network_error);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setCancelable(false);

        setListener(getActivity());

        dialog.findViewById(R.id.buttonReAccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListenerOnClick.onReAccessButtonClick();
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });

        Bundle bundle = getArguments();
        StringBuilder accessCode = new StringBuilder();
        accessCode.append(getString(R.string.network_error_access_code,
                String.format("%1$04d", bundle.getInt("accessCode"))));
        String message = bundle.getString("message");
        if (message != null && !message.isEmpty()) {
            accessCode.append(" " + message);
        }
        ((TextView) dialog.findViewById(R.id.accessCode)).setText(accessCode.toString());

        Bundle params = new Bundle();
        params.putString(FirebaseUtils.Companion.getNETWORK_ERROR_PARAM_ACCESSCODE(), accessCode.toString());
        mFirebase.logEvent(FirebaseUtils.Companion.getNETWORK_ERROR(), params);

        return dialog;
    }

    private void setListener(Object target) {
        if (target instanceof NetworkErrorFragmentInterface.onClickListener) {
            mListenerOnClick = (NetworkErrorFragmentInterface.onClickListener) target;
        }
    }


}
