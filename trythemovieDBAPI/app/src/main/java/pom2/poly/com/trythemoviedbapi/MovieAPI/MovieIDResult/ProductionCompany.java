package pom2.poly.com.trythemoviedbapi.MovieAPI.MovieIDResult;

/**
 * Created by User on 30/1/2016.
 */

import java.util.HashMap;
import java.util.Map;

public class ProductionCompany {

    private String name;
    private Integer id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
