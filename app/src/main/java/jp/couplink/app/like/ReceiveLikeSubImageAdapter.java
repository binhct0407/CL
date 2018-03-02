package jp.couplink.app.like;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.couplink.R;
import jp.couplink.app.like.model.ImageTargetUser;
import jp.couplink.app.utils.GlideImageConstant;
import jp.couplink.app.utils.RoundedCornersTransformation;

/**
 * Created by BinhTran on 11/28/2017.
 */

public class
ReceiveLikeSubImageAdapter extends RecyclerView.Adapter<ReceiveLikeSubImageAdapter.ViewHolder> {
    ArrayList<ImageTargetUser> imgIds;

    public ReceiveLikeSubImageAdapter(ArrayList<ImageTargetUser> imgIds) {
        this.imgIds = imgIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_like_sub_image_layout, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(imgIds.get(position).getOriginal());
    }

    @Override
    public int getItemCount() {
        return imgIds == null ? 0 : imgIds.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.receive_like_sub_image)
        BootstrapThumbnail receive_like_sub_image;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindView(String id) {
            Glide.with(itemView.getContext())
                    .load(id)
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(itemView.getContext(), GlideImageConstant.INSTANCE.getSCorner(), GlideImageConstant.INSTANCE.getSMargin())))
                    .into(receive_like_sub_image);


        }
    }
}
