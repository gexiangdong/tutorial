package cn.devmgr.tutorial.po;

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
    private int seasonCount;
    private Date originRelease;    
    
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

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.getClass().getName()).append("{id:").append(id);
        buf.append(",name:").append(name).append(",seasonCount:").append(seasonCount);
        buf.append("}");
        return buf.toString();
    }
}
