package jp.couplink.app.like;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.couplink.R;
import jp.couplink.app.like.model.ImageTargetUser;
import jp.couplink.app.mocking.UserObject;

/**
 * Created by BinhTran on 11/28/2017.
 */

public class ReceiveLikeAdapter extends RecyclerView.Adapter<ReceiveLikeAdapter.MyViewHolder> {
    Context context;
    private List<UserObject> userObjects;
    static ArrayList<ImageTargetUser> receive_like_sub_img_list = new ArrayList<>();

    public ReceiveLikeAdapter(Context context, ArrayList<UserObject> userObjects) {
        this.context = context;
        this.userObjects = userObjects;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_like_layout, null));
    }


    //Bind Data
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserObject userObject = userObjects.get(position);

        if (userObject != null) {
            holder.bindView(userObject);
        }
    }

    @Override
    public int getItemCount() {
        return userObjects == null ? 0 : userObjects.size();
    }

    //Add data to adapter
    public void add(UserObject userObject) {
        userObjects.add(userObject);
        notifyDataSetChanged();
    }

    //Clear data from adapter
    public void clear() {
        userObjects.clear();
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.receive_imgMain)
        ImageView receive_imgMain;
        @BindView(R.id.txtStatus)
        TextView txtStatus;
        @BindView(R.id.txtUserInfo)
        TextView txtUserInfo;
        @BindView(R.id.txtMessage)
        TextView txtMessage;
        @BindView(R.id.txtNotes)
        TextView txtNotes;
        @BindView(R.id.txthobby)
        TextView txthobby;
        @BindView(R.id.like_when)
        TextView txtLikeWhen;
        @BindView(R.id.receive_sub_list)
        RecyclerView receive_sub_list;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindView(UserObject userObject) {


            Glide.with(receive_imgMain.getContext())
                    .load(userObject.getTarget_user().getImages()[0].getOriginal())
                    .into(receive_imgMain);
            txtStatus.setText(userObject.getTarget_user().getStatus());
            txtUserInfo.setText(userObject.getTarget_user().getAge());
            txtMessage.setText(userObject.getTarget_user().getStatus());
            txtNotes.setText(userObject.getTarget_user().getJob());
            txthobby.setText(userObject.getTarget_user().getJob());
            txtLikeWhen.setText(userObject.getTarget_user().getName());
            for (int i=0;i<userObject.getTarget_user().getImages().length;i++)
            {
                receive_like_sub_img_list.add(userObject.getTarget_user().getImages()[i]);
            }
            ReceiveLikeSubImageAdapter subImageAdapter = new ReceiveLikeSubImageAdapter(receive_like_sub_img_list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            receive_sub_list.setLayoutManager(layoutManager);
            receive_sub_list.setAdapter(subImageAdapter);

        }


    }
}
