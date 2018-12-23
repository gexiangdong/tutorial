package cn.devmgr.tutorial.pojo;

import java.util.Date;
import java.util.List;

/**
 * 电视剧
 *
 */
public class TvSeries {
    private Integer id;
    private String name;
    private int seasonCount;
    private Date originRelease;
    private List<TvCharacter> tvCharacters;
    
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSeasonCount() {
        return seasonCount;
    }
    public void setSeasonCount(int seasonCount) {
        this.seasonCount = seasonCount;
    }
    public Date getOriginRelease() {
        return originRelease;
    }
    public void setOriginRelease(Date originRelease) {
        this.originRelease = originRelease;
    }
    public List<TvCharacter> getTvCharacters() {
        return tvCharacters;
    }
    public void setTvCharacters(List<TvCharacter> tvCharacters) {
        this.tvCharacters = tvCharacters;
    }
    
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.getClass().getName()).append("{id:").append(id);
        buf.append(",name:").append(name).append(",seasonCount:").append(seasonCount);
        buf.append("}");
        return buf.toString();
    }
}
