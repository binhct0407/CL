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

public class FavoriteLikeAdapter extends RecyclerView.Adapter<FavoriteLikeAdapter.ViewHolder> {
    Context context;
    private List<UserObject> userObjects;


    public FavoriteLikeAdapter(Context context, ArrayList<UserObject> userObjects) {
        this.context = context;
        this.userObjects = userObjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_like_layout, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserObject userObject = userObjects.get(position);

        if (userObject != null) {
            holder.bindView(userObject);
        }
    }


    //add data
    public void add(UserObject userObject) {
        userObjects.add(userObject);
        notifyDataSetChanged();
    }

    //Clear data from adapter
    public void clear() {
        userObjects.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userObjects == null ? 0 : userObjects.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.favorite_imgMain)
        ImageView receive_imgMain;
        @BindView(R.id.item_favorite_like_txtName)
        TextView txtStatus;
        @BindView(R.id.item_favorite_like_txtUserInfo)
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


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindView(UserObject userObject) {
            ArrayList<ImageTargetUser> receive_like_sub_img_list = new ArrayList<>();
            Glide.with(receive_imgMain.getContext())
                    .load(userObject.getTarget_user().getImages()[0].getOriginal())
                    .into(receive_imgMain);
            txtStatus.setText(userObject.getTarget_user().getStatus());
            txtUserInfo.setText(Integer.toString(userObject.getTarget_user().getAge()));
            txtMessage.setText(userObject.getTarget_user().getStatus());
            txtNotes.setText(userObject.getTarget_user().getJob());
            txthobby.setText(userObject.getTarget_user().getJob());
            txtLikeWhen.setText(userObject.getCreated_at());
            for (int i = 0; i < userObject.getTarget_user().getImages().length; i++) {
                receive_like_sub_img_list.add(userObject.getTarget_user().getImages()[i]);
            }
            ReceiveLikeSubImageAdapter subImageAdapter = new ReceiveLikeSubImageAdapter(receive_like_sub_img_list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            receive_sub_list.setLayoutManager(layoutManager);
            receive_sub_list.setAdapter(subImageAdapter);


        }


    }
}
