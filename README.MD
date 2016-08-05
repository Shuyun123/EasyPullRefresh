# EasyPullRefresh

##简介

EasyPullRefresh是一个实现了下拉刷新/上拉刷新/加载更多的android组件。现支持ListView、RecyclerView下拉刷新/加载更多/上拉刷新功能。WebView支持下拉刷新。同时支持自定义刷新样式，RecyclerView中的adapter和ViewHolder分离。

详细介绍可以看我的博客[EasyPullRefresh](http://www.anumbrella.net/2016/08/05/android%E7%AE%80%E6%98%93%E4%B8%8B%E6%8B%89%E6%A1%86%E6%9E%B6%E5%AE%9E%E7%8E%B0/)



##主要功能

1.下拉刷新、滑动到底部自动加载下页数据、上拉刷新；
2.可以方便添加自定义的Header和Footer布局视图；
3.下拉刷新、底部滑动刷新、上拉刷新都可更改样式，同时支持自定义；
4.具备item点击和长按事件，加载显示progress、错误显示ErrorView、空数据显示EmptyView。


##依赖


```groovy
compile 'net.anumbrella:easypullrefresh:1.0.0'
```



##示例
![demo](./image/EasyPullRefresh.gif)




## EasyPullRefresh使用
根据你使用的视图选择在xml中的布局文件。

如:使用扩展ListView的下拉刷新，则在xml中添加以下布局文件:

```xml
<net.anumbrella.pullrefresh.Widget.PullRefreshListView
        android:id="@+id/PullRefreshListView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

若使用扩展RecyclerView的下拉刷新，则添加:

```xml
 <net.anumbrella.pullrefresh.Widget.PullRefreshRecyclerview
        android:id="@+id/PullRefreshRecyclerview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
使用WebView的下拉刷新，则添加:
```xml
  <net.anumbrella.pullrefresh.Widget.PullRefreshWebView
        android:id="@+id/PullRefreshWebView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```


在代码中设置

```java
 mPullListView = (PullRefreshListView) findViewById(R.id.PullRefreshListView);
        //是否开启上拉刷新
        mPullListView.setPullLoadEnabled(false);
        //滑动到底部是否自动加载
        mPullListView.setScrollLoadEnabled(true);
```

同时支持错误显示，比如网络出错显示:

```java
 mPullListView.setErrorView(R.layout.error_view);
```

```java
  mPullListView.showErrorView();
```


## PullRefreshListView
PullRefreshListView是对ListView的扩展，支持下拉刷新、上拉刷新以及滑动到底部自动加载。

**自定义设置支持下拉刷新、上拉刷新以及滑动到底部自动加载样式**  
```java
void setHeaderLayout(LoadingLayout layout)
void setFooterLayout(LoadingLayout layout)
void setScrollFooterLayout(LoadingLayout layout)
```

**默认Header样式中是否显示时间、友好显示时间、显示图片、设置图片、设置背景颜色功能**  
```java
void setDisplayTime(boolean value)
void setFriendlyTime(boolean friendlyTime)
void setIconVisibility(boolean value)
void setIconImage(int imageView)
```
默认是不显示图片的，通过setIConImage(R.mipmap.ic_imgae)可以设置不同的图片。

**Progress、ErrorView设置和显示** 
```java
void setAdapterWithProgress(Adapter adapter)
void setAdapter(Adapter adapter)
void setErrorView(int layout)
void showErrorView()
void showErrorView(OnClickListener listener)
void setHeaderLoadingBgColor(int color) 
void setFooterLoadingBgColor(int color) 
```

通过监听adapter的改变来显示Progress、EmptyView。Progress显示通过setAdapterWithProgress(Adapter adapter)方法来设置，在开始进入界面时就会显示Progress，而ErrorView通过showErrorView()来调用。


## PullRefreshRecyclerview

PullRefreshRecyclerview是支持addHeaderView、 addFooterView、下拉刷新、加载更多、上拉刷新的RecyclerView。(支持`LinearLayoutManager`,`GridLayoutManager`,`StaggeredGridLayoutManager` )。它对 RecyclerView 控件进行了拓展，PullRefreshRecyclerview与PullRefreshListView支持的方法大致相同。

**自定义设置支持下拉刷新、上拉刷新以及滑动到底部自动加载样式**  
```java
void setHeaderLayout(LoadingLayout layout)
void setFooterLayout(LoadingLayout layout)
void setScrollFooterLayout(LoadingLayout layout)
```

**默认Header样式中是否显示时间、友好显示时间、显示图片、设置图片功能**  
```java
void setDisplayTime(boolean value)
void setFriendlyTime(boolean friendlyTime)
void setIconVisibility(boolean value)
void setIconImage(int imageView)
```
默认是不显示图片的，通过setIConImage(R.mipmap.ic_imgae)可以设置不同的图片。

**Progress、ErrorView设置和显示** 
```java
void setAdapterWithProgress(RecyclerAdapter adapter)
void setAdapter(RecyclerAdapter adapter)
void setErrorView(int layout)
void showErrorView()
void showErrorView(OnClickListener listener)
void setHeaderLoadingBgColor(int color) 
void setFooterLoadingBgColor(int color)  
```

RecyclerAdapter、BaseViewHolder是与PullRefreshRecyclerview配合使用的。RecyclerAdapter中也可以设置其他的RecyclerView.Adapter。

**整合了数据增删的功能**  
```java
void add(D object);
void addAll(Collection<? extends D> collection);
void addAll(D[] items);
void insert(D object, int index);
void insertAll(D[] object, int index);
void clear();
void sort(Comparator<? super D> comparator);
```

**对Header与Footer视图的实现**  
```java
void addHeaderView(View header)
void addFooterView(View footer)
```

**点击事件监听**  
```java
 /**
  * 点击事件接口
  */
 public interface OnItemClickLitener {
   void onItemClick(View view, int position);
   boolean onItemLongClick(View view, int position);
 }
```

通过adapter.setmOnItemClickLitener(listener)来设置监听事件。

BaseViewHolder\<D\>
这个ViewHolder将每个item与adapter解耦。adapter只管实例化对应ViewHolder.每个Item的view生成,findviewbyid,UI修改都由viewHolder自己管理。

 

## PullRefreshWebView
PullRefreshWebView是对WebView的扩展，只支持下拉刷新。

**自定义设置支持下拉刷新、上拉刷新样式**  
```java
void setHeaderLayout(LoadingLayout layout)
void setFooterLayout(LoadingLayout layout)
```

**默认Header样式中是否显示时间、友好显示时间、显示图片、设置图片功能**  
```java
void setDisplayTime(boolean value)
void setFriendlyTime(boolean friendlyTime)
void setIconVisibility(boolean value)
void setIconImage(int imageView)
```

## LoadingStyle(刷新样式)

默认提供了几种样式。Headr样式主要包括:DefaultHeaderLoadingLayout、IndicatorViewHeaderLoadingLayout、ProgressBarHeaderLoadingLayout、RotateHeaderLoadingLayout这几种，当然你可以自定义自己的样式。Footer样式主要括:DefaultFooterLoadingLayout、IndicatorViewFooterLoadingLayout。

通过以下方法可以指定刷新的样式：
```java
void setHeaderLayout(LoadingLayout layout)
void setFooterLayout(LoadingLayout layout)
void setScrollFooterLayout(LoadingLayout layout)
```

IndicatorViewHeaderLoadingLayout、IndicatorViewFooterLoadingLayout，主要参考[AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView)。提供了设置颜色和样式的方法，因此AVLoadingIndicatorView有多少样式就可以生成多少种刷新样式。

主要样式包括:

![AVLoadingIndicatorView](./image/AVLoadingIndicatorView.gif)

row1：BallPulse、BallGridPulse、BallClipRotate、BallClipRotatePulse

row2：SquareSpin、BallClipRotateMultiple、BallPulseRise、BallRotate

row3：CubeTransition、BallZigZag、BallZigZagDeflect、BallTrianglePath

row4：BallScale、LineScale、LineScaleParty、BallScaleMultiple

row5：BallPulseSync、BallBeat、LineScalePulseOut、LineScalePulseOutRapid

row6：BallScaleRipple、BallScaleRippleMultiple、BallSpinFadeLoader、LineSpinFadeLoader

row7：TriangleSkewSpin、Pacman、BallGridBeat、SemiCircleSpin

```java
   //设置header样式
   IndicatorViewHeaderLoadingLayout headerLoadingLayout = new IndicatorViewHeaderLoadingLayout(this);
   //设置样式类型
   headerLoadingLayout.setIndicatorStyle("BallClipRotateMultiple");
   //设置样式颜色
   headerLoadingLayout.setIndicatorColor(R.color.indicator_color);
   mPullListView.setHeaderLayout(headerLoadingLayout);
```

ProgressBarHeaderLoadingLayout，主要参考了[MaterialLoadingProgressBar](https://github.com/lsjwzh/MaterialLoadingProgressBar)同样支持自定义。


####header样式效果:

<div style="background-color:#F7F7F7">
 <ul>
   <li style="list-style-type:none;margin-top:20px;">
      <image style="margin-left:10px" src = "./image/header1.png">
      <image style="margin-left:10px" src = "./image/header2.png">
      <image style="margin-left:10px" src = "./image/header3.png">
   </li>
   <br/>
   <li style="list-style-type:none;margin-top:20px;">
      <image style="margin-left:10px" src = "./image/header4.png">
   </li>
  </ul>
</div>




####footer样式效果:

<div style="background-color:#F7F7F7">
 <ul>
   <li style="list-style-type:none;margin-top:20px;">
      <image style="margin-left:10px" src = "./image/footer1.png">
      <image style="margin-left:10px" src = "./image/footer2.png">
      
   </li>
   </ul>
</div>


##感谢:
实现的这个下拉刷新的框架，并不是自己的原创，在完成过程中是参考了很多开源的框架，并把自己认为比较好的东西借鉴了过来，从而形成我的东西。

* [LRecyclerView](https://github.com/jdsjlzx/LRecyclerView)
* [EasyRecyclerView](https://github.com/Jude95/EasyRecyclerView)     
* [materialish-progress](https://github.com/pnikosis/materialish-progress)
* [AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView)
* [MaterialLoadingProgressBar](https://github.com/lsjwzh/MaterialLoadingProgressBar) 
* [HeaderAndFooterRecyclerView](https://github.com/cundong/HeaderAndFooterRecyclerView)



###具体用法，请看demo。


##License:

```bash
               Copyright 2015 Anumbrella
                              
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 
        http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 
 
 
 







