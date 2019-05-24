package cn.devmgr.tutorial.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import cn.devmgr.tutorial.po.TvCharacter;
import org.springframework.stereotype.Repository;

//@RepositoryDefinition(domainClass = TvCharacter.class, idClass = Integer.class)
@Repository
public interface TvCharacterDao extends JpaRepository<TvCharacter, Integer>{
    
    public List<TvCharacter> getAllByTvSeriesId(int tvSeriesId);
    
}
