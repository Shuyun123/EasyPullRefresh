package net.anumbrella.easypullrefresh.Model.Bean;

/**
 * author：anumbrella
 * Date:16/8/1 上午12:01
 */
public class ImageDataModel {

    private String image;

    private String title;

    private String date;

    public ImageDataModel(String image, String title, String date) {
        this.image = image;
        this.title = title;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
