package cn.devmgr.tutorial.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import cn.devmgr.tutorial.po.TvSeries;

@RepositoryDefinition(domainClass = TvSeries.class, idClass = Integer.class) 
public interface TvSeriesDao extends JpaRepository<TvSeries,Integer>{
    
    @Modifying
    @Query(value="update tv_series set status=-1, reason=?2 where id=?1", nativeQuery = true)
    public int logicDelete(int id, String reason);
    
}
