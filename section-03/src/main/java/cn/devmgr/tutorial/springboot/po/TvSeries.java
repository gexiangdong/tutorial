package cn.devmgr.tutorial.springboot.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 电视剧
 *
 */
@Entity
@Table(name = "tv_series")
public class TvSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int episodeCount;
    private Date originalRelease;    
    
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
    public int getEpisodeCount() {
        return episodeCount;
    }
    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }
    public Date getOriginalRelease() {
        return originalRelease;
    }
    public void setOriginalRelease(Date releaseDate) {
        this.originalRelease = releaseDate;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.getClass().getName()).append("{id:").append(id);
        buf.append(",name:").append(name).append(",episodeCount:").append(episodeCount);
        buf.append("}");
        return buf.toString();
    }
}
