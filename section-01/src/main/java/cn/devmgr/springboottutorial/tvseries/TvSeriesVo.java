package cn.devmgr.springboottutorial.tvseries;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TvSeriesVo {
    private Integer id;
    private String name;
    private int episodeCount;
    
    //如果想用long型的timestamp表示日期，可用： @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date originalRelease;
    
    public TvSeriesVo() { 
    }
    
    public TvSeriesVo(int id, String name, int episodeCount, Date originalRelease) {
        this.id = id;
        this.name = name;
        this.episodeCount = episodeCount;
        this.originalRelease = originalRelease;
    }
    
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
    public void setOriginalRelease(Date originalRelease) {
        this.originalRelease = originalRelease;
    }
    
}
