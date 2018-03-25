package cn.devmgr.tutorial.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import cn.devmgr.tutorial.springboot.po.TvCharacter;

@RepositoryDefinition(domainClass = TvCharacter.class, idClass = Integer.class) 
public interface TvCharacterDao extends JpaRepository<TvCharacter, Integer>{
    
    public List<TvCharacter> getAllByTvSeriesId(int tvSeriesId);
    
}
