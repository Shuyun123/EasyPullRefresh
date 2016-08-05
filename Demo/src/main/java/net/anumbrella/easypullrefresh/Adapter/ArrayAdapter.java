package net.anumbrella.easypullrefresh.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import net.anumbrella.easypullrefresh.Model.Bean.ImageDataModel;
import net.anumbrella.easypullrefresh.ViewHolder.ImageViewHolder;
import net.anumbrella.pullrefresh.Adapter.BaseViewHolder;
import net.anumbrella.pullrefresh.Adapter.RecyclerAdapter;

/**
 * author：anumbrella
 * Date:16/8/1 上午12:00
 */
public class ArrayAdapter extends RecyclerAdapter<ImageDataModel>{

    public ArrayAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(parent);
    }
}
