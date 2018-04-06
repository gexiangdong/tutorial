package cn.devmgr.tutorial.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import cn.devmgr.tutorial.pojo.TvCharacter;

@Repository
public interface TvCharacterDao {
    
    @Select("select * from tv_character where id=#{id}")
    public TvCharacter getOneById(int id);
    
    @Select("select * from tv_character where tv_series_id=#{tvSeriesId}")
    public List<TvCharacter> getAllByTvSeriesId(int tvSeriesId);
    
    public int update(TvCharacter tvCharacter);
    public int insert(TvCharacter tvCharacter);
    
    @Delete("delete from tv_character where id=#{id}")
    public int delete(int id);
}
