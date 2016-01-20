package pom2.poly.com.trythemoviedbapi.MovieAPI;


import java.util.ArrayList;
import java.util.List;

public class Config {

    public Images images;
    public List<String> change_keys = new ArrayList<String>();

    /**
     * @return The images
     */
    public Images getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(Images images) {
        this.images = images;
    }

    /**
     * @return The changeKeys
     */
    public List<String> getChangeKeys() {
        return change_keys;
    }

    /**
     * @param changeKeys The change_keys
     */
    public void setChangeKeys(List<String> changeKeys) {
        this.change_keys = changeKeys;
    }

}