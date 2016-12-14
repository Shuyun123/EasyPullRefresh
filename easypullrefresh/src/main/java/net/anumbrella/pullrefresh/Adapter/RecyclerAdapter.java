package net.anumbrella.pullrefresh.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author：anumbrella
 * Date:16/7/19 上午10:30
 */
public abstract class RecyclerAdapter<D> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<D> mContents;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    private Context mContext;
    private OnItemClickLitener mOnItemClickLitener;
    private RecyclerView.AdapterDataObserver mDataObserver;

    private RecyclerView.Adapter<BaseViewHolder> mInnerAdapter;

    private final Object mLock = new Object();

    private static boolean userInnerAdapter = false;

    /**
     * 点击事件接口
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        boolean onItemLongClick(View view, int position);
    }

    public RecyclerAdapter(Context context) {
        init(context, new ArrayList<D>());
    }


    public RecyclerAdapter(Context context, D[] objects) {
        init(context, Arrays.asList(objects));
    }


    public RecyclerAdapter(Context context, List<D> objects) {
        init(context, objects);
    }


    public RecyclerAdapter(Context context, RecyclerView.Adapter adapter) {
        init(context, adapter);

    }


    /**
     * 初始化,由外部adapter传人
     *
     * @param context
     * @param innerAdapter
     */
    private void init(Context context, RecyclerView.Adapter innerAdapter) {
        mContext = context;
        userInnerAdapter = true;
        setAdapter(innerAdapter);

    }


    /**
     * 初始化
     *
     * @param context
     * @param datas
     */
    private void init(Context context, List<D> datas) {
        mContext = context;
        mContents = datas;
    }


    private void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            if (!(adapter instanceof RecyclerView.Adapter))
                throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
        }
        if (mInnerAdapter != null) {
            notifyItemRangeRemoved(getHeaderViewsCount(), mInnerAdapter.getItemCount());
            mInnerAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        this.mInnerAdapter = adapter;
        mInnerAdapter.registerAdapterDataObserver(mDataObserver);
        notifyItemRangeInserted(getHeaderViewsCount(), mInnerAdapter.getItemCount());
    }

    /**
     * 添加头部视图
     *
     * @param header
     */
    public void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        mHeaderViews.add(header);
        this.notifyDataSetChanged();
    }

    /**
     * 添加底部视图
     *
     * @param footer
     */
    public void addFooterView(View footer) {
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }
        mFooterViews.add(footer);
        this.notifyDataSetChanged();
    }


    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public Context getContext() {
        return mContext;
    }


    public View getFooterView(int index) {
        return getFooterViewsCount() > 0 && index < getFooterViewsCount() ? mFooterViews.get(index) : null;
    }


    public View getFooterView(boolean bottom) {
        if (bottom) {
            if (getFooterViewsCount() > 0) {
                return mFooterViews.get(getFooterViewsCount() - 1);
            } else {
                return null;
            }
        }
        return null;
    }


    public int getFooterViewsCount() {
        return mFooterViews.size();
    }


    /**
     * 获取实际的数据大小
     *
     * @return
     */
    public int getCount() {
        if (!userInnerAdapter) {
            return mContents.size();
        }
        return 0;
    }

    public View getHeaderView(int index) {
        return getHeaderViewsCount() > 0 && index < getHeaderViewsCount() ? mHeaderViews.get(index) : null;
    }


    /**
     * 隐藏头部和底部的所有视图
     */
    public void hideAllHeaderAndFooterView() {
        for (View view : mHeaderViews) {
            view.setVisibility(View.INVISIBLE);
        }

        for (View view : mFooterViews) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示头部和底部的所有视图
     */
    public void displayAllHeaderAndFooterView() {
        for (View view : mHeaderViews) {
            view.setVisibility(View.VISIBLE);
        }

        for (View view : mFooterViews) {
            view.setVisibility(View.VISIBLE);
        }
    }



    public void removeHeaderView(View view) {
        int position = mHeaderViews.indexOf(view);
        mHeaderViews.remove(view);
        this.notifyItemRemoved(position);
    }


    public void removeFooterView(View view) {
        int position = mFooterViews.indexOf(view);
        mFooterViews.remove(view);
        this.notifyItemRemoved(position);
    }

    /**
     * 添加单个数据
     *
     * @param object
     */
    public void add(D object) {
        if (!userInnerAdapter) {
            if (object != null) {
                synchronized (mLock) {
                    mContents.add(object);
                }
            }
            if (mDataObserver != null) mDataObserver.onItemRangeInserted(getCount() + 1, 1);
            notifyItemInserted(mHeaderViews.size() + getCount() + 1);
        }
    }


    /**
     * 添加多个数据
     *
     * @param collection 数据为collection
     */
    public void addAll(Collection<? extends D> collection) {
        if (!userInnerAdapter) {
            if (collection != null && collection.size() != 0) {
                synchronized (mLock) {
                    mContents.addAll(collection);
                }
            }
            int dataCount = collection == null ? 0 : collection.size();
            if (mDataObserver != null)
                mDataObserver.onItemRangeInserted(getCount() - dataCount + 1, dataCount);
            notifyItemRangeInserted(mHeaderViews.size() + getCount() - dataCount + 1, dataCount);
        }
    }

    /**
     * 添加多个数据
     *
     * @param items 数据为数组
     */
    public void addAll(D[] items) {
        if (!userInnerAdapter) {
            if (items != null && items.length != 0) {
                synchronized (mLock) {
                    Collections.addAll(mContents, items);
                }
            }
            int dataCount = items == null ? 0 : items.length;
            if (mDataObserver != null)
                mDataObserver.onItemRangeInserted(getCount() - dataCount + 1, dataCount);
            notifyItemRangeInserted(mHeaderViews.size() + getCount() - dataCount + 1, dataCount);
        }
    }

    /**
     * 在某个位置插入单个数据
     *
     * @param object
     * @param index
     */
    public void insert(D object, int index) {
        if (!userInnerAdapter) {
            synchronized (mLock) {
                mContents.add(index, object);
            }
            if (mDataObserver != null) mDataObserver.onItemRangeInserted(index, 1);
            notifyItemInserted(mHeaderViews.size() + index + 1);
        }
    }

    /**
     * 在某个位置插入多个数据
     *
     * @param object 数据为数组
     * @param index
     */
    public void insertAll(D[] object, int index) {
        if (!userInnerAdapter) {
            synchronized (mLock) {
                mContents.addAll(index, Arrays.asList(object));
            }
            int dataCount = object == null ? 0 : object.length;
            if (mDataObserver != null) mDataObserver.onItemRangeInserted(index + 1, dataCount);
            notifyItemRangeInserted(mHeaderViews.size() + index + 1, dataCount);
        }
    }

    /**
     * 在某个位置插入多个数据
     *
     * @param object 数据为collection
     * @param index
     */
    public void insertAll(Collection<? extends D> object, int index) {
        if (!userInnerAdapter) {
            synchronized (mLock) {
                mContents.addAll(index, object);
            }
            int dataCount = object == null ? 0 : object.size();
            if (mDataObserver != null) mDataObserver.onItemRangeInserted(index + 1, dataCount);
            notifyItemRangeInserted(mHeaderViews.size() + index + 1, dataCount);
        }
    }


    /**
     * 删除某个数据
     *
     * @param object 具体数据
     */
    public void remove(D object) {
        if (!userInnerAdapter) {
            int position = mContents.indexOf(object);
            synchronized (mLock) {
                if (mContents.remove(object)) {
                    if (mDataObserver != null) mDataObserver.onItemRangeRemoved(position, 1);
                    notifyItemRemoved(mHeaderViews.size() + position);

                }
            }
        }
    }

    /**
     * 删除某个位置的数据
     *
     * @param position
     */
    public void remove(int position) {
        if (!userInnerAdapter) {
            synchronized (mLock) {
                mContents.remove(position);
            }
            if (mDataObserver != null) mDataObserver.onItemRangeRemoved(position, 1);
            notifyItemRemoved(mHeaderViews.size() + position);
        }
    }


    /**
     * 清除所有数据
     */
    public void clear() {
        if (!userInnerAdapter) {
            int count = mContents.size();
            synchronized (mLock) {
                mContents.clear();
            }
            if (mDataObserver != null) mDataObserver.onItemRangeRemoved(0, count);
            notifyItemRangeRemoved(mHeaderViews.size(), count);
        }
    }

    /**
     * 对数据进行排序
     *
     * @param comparator
     */
    public void sort(Comparator<? super D> comparator) {
        if (!userInnerAdapter) {
            synchronized (mLock) {
                Collections.sort(mContents, comparator);
            }
            notifyDataSetChanged();
        }
    }


    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if (userInnerAdapter) {
            mDataObserver = observer;
        }
        super.registerAdapterDataObserver(observer);
    }


    public boolean isHeader(int position) {
        return position >= 0 && position < mHeaderViews.size();
    }


    public boolean isFooter(int position) {
        return position >= mHeaderViews.size() + getCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (userInnerAdapter) {
            mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        } else {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeader(position) || isFooter(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }
    }


    private View createHeaderAndFooterView(ViewGroup parent, int viewType) {
        for (View headerView : mHeaderViews) {
            if (headerView.hashCode() == viewType) {
                FrameLayout view = new FrameLayout(getContext());
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams() != null) {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                } else {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                view.addView(headerView);
                return view;
            }
        }

        for (View footerView : mFooterViews) {
            if (footerView.hashCode() == viewType) {
                FrameLayout view = new FrameLayout(getContext());
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams() != null) {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                } else {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                view.addView(footerView);
                return view;
            }
        }
        return null;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createHeaderAndFooterView(parent, viewType);
        if (view != null) {
            return new StateViewHolder(view);
        }
        final BaseViewHolder viewHolder = OnCreateViewHolder(parent, viewType);

        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(v, viewHolder.getAdapterPosition() - mHeaderViews.size());
                }
            });


            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemClickLitener.onItemLongClick(v, viewHolder.getAdapterPosition() - mHeaderViews.size());
                }
            });
        }

        if (userInnerAdapter) {
            mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
        return viewHolder;
    }


    /**
     * 创建视图，交给子类实现方法
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.itemView.setId(position);
        if (mHeaderViews.size() != 0 && position < mHeaderViews.size()) {
            return;
        }

        int i = position - mHeaderViews.size() - mContents.size();
        if (mFooterViews.size() != 0 && i >= 0) {
            return;
        }
        if (userInnerAdapter) {
            if (position >= mHeaderViews.size() && position < mHeaderViews.size() + mInnerAdapter.getItemCount()) {
                mInnerAdapter.onBindViewHolder(holder, position - mHeaderViews.size());
            }
        } else {
            OnBindViewHolder(holder, position - mHeaderViews.size());
        }

    }

    public void OnBindViewHolder(BaseViewHolder holder, final int position) {
        holder.setData(getItem(position));
    }

    public D getItem(int position) {
        if (mContents != null && mContents.size() > 0) {
            return mContents.get(position);
        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {

        if (mHeaderViews.size() != 0) {
            if (position < mHeaderViews.size()) return mHeaderViews.get(position).hashCode();
        }
        if (mFooterViews.size() != 0) {
            int i = position - mHeaderViews.size() - mContents.size();
            if (i >= 0) {
                return mFooterViews.get(i).hashCode();
            }
        }
        return getViewType(position - mHeaderViews.size());

    }

    public int getViewType(int position) {
        return 0;
    }

    public int getPosition(D item) {
        if (!userInnerAdapter) {
            return mContents.indexOf(item);
        }
        return -1;
    }


    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取所有的item大小，包括header、contents和footer
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mInnerAdapter != null && userInnerAdapter) {
            return getHeaderViewsCount() + getFooterViewsCount() + mInnerAdapter.getItemCount();
        } else {
            return getHeaderViewsCount() + getFooterViewsCount() + mContents.size();
        }
    }


    private class StateViewHolder extends BaseViewHolder {
        public StateViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
