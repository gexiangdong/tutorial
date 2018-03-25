package cn.devmgr.tutorial.springboot.dto;

import java.util.Date;
import java.util.List;

/**
 * 电视剧
 *
 */
public class TvSeriesDto {
    private Integer id;
    private String name;
    private int episodeCount;
    private Date originalRelease;
    private List<TvCharacterDto> tvCharacters;
    
    
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
    public List<TvCharacterDto> getTvCharacters() {
        return tvCharacters;
    }
    public void setTvCharacters(List<TvCharacterDto> tvCharacters) {
        this.tvCharacters = tvCharacters;
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
