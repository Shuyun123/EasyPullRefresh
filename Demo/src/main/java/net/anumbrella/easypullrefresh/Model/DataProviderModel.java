package net.anumbrella.easypullrefresh.Model;

import net.anumbrella.easypullrefresh.Model.Bean.ImageDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * author：anumbrella
 * Date:16/8/4 下午4:46
 */
public class DataProviderModel {

    public static List<ImageDataModel> getImageListData() {
        ArrayList<ImageDataModel> datas = new ArrayList<>();
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/07/29/2e9e9860c62a7aab!600x600.jpg",
                "千与千寻", "2016-07-29 16:29:19"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/07/20/c53626b361b4c580!600x600.jpg",
                "大鱼海棠", "2016-07-20 16:20:08"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/06/28/93435348ddefa060!600x600.jpg",
                "执手相依", "2016-06-28 16:55:13"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/05/18/5542e7943f87d663!600x600.jpg",
                "海贼王路飞", "2016-05-18 16:45:29"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/05/17/5f8d6f1ff6983abd!600x600.jpg",
                "阿狸的美好生活", "2016-05-17 17:11:32"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/05/17/9aac4e9b91f16fcb!600x600.jpg",
                "韩国插画", "2016-05-17 16:10:38"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/04/26/b772983392671dcc!600x600.jpg",
                "打伞的她", "2016-04-25 15:38:00"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/04/26/a490a76ddd330818!600x600.jpg",
                "日本插画", "2016-04-26 15:38:04"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/04/12/7d8c347388d89205!600x600.jpg",
                "古风系意境图片", "2016-04-12 13:34:27"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/04/26/464ce257e6fa75b8!600x600.jpg",
                "二次元动漫图片", "2016-04-26 11:31:54"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/04/04/e63b991871bfca65!600x600.jpg",
                "萌萌哒，唯美的动漫图", "2016-04-04 14:02:29"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/03/07/50978043f025de4e!600x600.jpg",
                "城巷少女", "2016-03-07 16:56:32"));
        datas.add(new ImageDataModel("http://img.woyaogexing.com/2016/08/03/7a55f956628aa5a8!600x600.jpg",
                "动漫女生图片", "2016-08-03 16:41:54"));

        return datas;
    }


}
