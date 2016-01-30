package pom2.poly.com.trythemoviedbapi.MovieAPI.MovieIDResult;

/**
 * Created by User on 30/1/2016.
 */
import java.util.HashMap;
import java.util.Map;

public class SpokenLanguage {

    private String iso_639_1;
    private String name;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The iso6391
     */
    public String getIso6391() {
        return iso_639_1;
    }

    /**
     *
     * @param iso6391
     * The iso_639_1
     */
    public void setIso6391(String iso6391) {
        this.iso_639_1 = iso6391;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}