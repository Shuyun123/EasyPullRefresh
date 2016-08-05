package net.anumbrella.easypullrefresh.ViewHolder;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import net.anumbrella.easypullrefresh.Model.Bean.ImageDataModel;
import net.anumbrella.easypullrefresh.R;
import net.anumbrella.pullrefresh.Adapter.BaseViewHolder;

/**
 * author：anumbrella
 * Date:16/8/1 上午12:03
 */
public class ImageViewHolder extends BaseViewHolder<ImageDataModel> {


    private  TextView title;
    private TextView date;
    private SimpleDraweeView image;

    public ImageViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_recyclerview);
        title = $(R.id.image_title);
        date = $(R.id.iamge_date);
        image = $(R.id.recycle_imageview);


    }

    @Override
    public void setData(ImageDataModel data) {
        super.setData(data);
        date.setText(data.getDate());
        title.setText(data.getTitle());
        image.setImageURI(Uri.parse(data.getImage()));

    }
}
