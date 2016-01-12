package pom2.poly.com.tryretrofit2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/1/2016.
 */
public class StackOverflowQuestions {

    private List<Item> items = new ArrayList<Item>();
    private Boolean hasMore;
    private Integer quotaMax;
    private Integer quotaRemaining;

    /**
     *
     * @return
     * The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     *
     * @param items
     * The items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     *
     * @return
     * The hasMore
     */
    public Boolean getHasMore() {
        return hasMore;
    }

    /**
     *
     * @param hasMore
     * The has_more
     */
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    /**
     *
     * @return
     * The quotaMax
     */
    public Integer getQuotaMax() {
        return quotaMax;
    }

    /**
     *
     * @param quotaMax
     * The quota_max
     */
    public void setQuotaMax(Integer quotaMax) {
        this.quotaMax = quotaMax;
    }

    /**
     *
     * @return
     * The quotaRemaining
     */
    public Integer getQuotaRemaining() {
        return quotaRemaining;
    }

    /**
     *
     * @param quotaRemaining
     * The quota_remaining
     */
    public void setQuotaRemaining(Integer quotaRemaining) {
        this.quotaRemaining = quotaRemaining;
    }

}
