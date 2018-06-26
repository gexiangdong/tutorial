package cn.devmgr.tutorial.springmvc;

import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 * 基本的POJO，而且使用Bean Validation注解进行校验数据
 *
 */
public class TvSeries {
    @Null private Integer id;
    @NotNull private String name;
    
    @NumberFormat(pattern="#,##0")
    @DecimalMin("1") private int seasonCount;
    
    //@Past表示只接受过去的时间，比当前时间还晚的被认为不合格
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Past private Date originRelease;
    
    public TvSeries() { 
    }
    
    public TvSeries(int id, String name, int seasonCount, Date originRelease) {
        this.id = id;
        this.name = name;
        this.seasonCount = seasonCount;
        this.originRelease = originRelease;
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

    
}
