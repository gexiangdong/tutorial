package cn.devmgr.tutorial.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.devmgr.tutorial.dao.TvCharacterDao;
import cn.devmgr.tutorial.dao.TvSeriesDao;
import cn.devmgr.tutorial.pojo.TvCharacter;
import cn.devmgr.tutorial.pojo.TvSeries;

@Service
public class TvSeriesService {
    private final Log log = LogFactory.getLog(TvSeriesService.class);
    
    @Autowired private TvSeriesDao seriesDao;
    @Autowired private TvCharacterDao characterDao;

    @Transactional(readOnly=true)
    public List<TvSeries> getAllTvSeries(){
        try {
            Thread.sleep(10);
        }catch(Exception e) {
            
        }
        if(log.isTraceEnabled()) {
            log.trace("getAllTvSeries started   ");
        }
        List<TvSeries> list = seriesDao.getAll();

        return list;
    }
    
    @Transactional(readOnly=true)
    public TvSeries getTvSeriesById(int tvSeriesId) {
        if(log.isTraceEnabled()) {
            log.trace("getTvSeriesById started for " + tvSeriesId);
        }
        TvSeries series = seriesDao.getOneById(tvSeriesId);
        if(series != null) {
            series.setTvCharacters(characterDao.getAllByTvSeriesId(tvSeriesId));
        }
        return series;
    }
    
    public TvSeries updateTvSeries(TvSeries tvSeries) {
        if(log.isTraceEnabled()) {
            log.trace("updateTvSeries started for " + tvSeries);
        }
        seriesDao.update(tvSeries);
        return tvSeries;
    }
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public TvSeries addTvSeries(TvSeries tvSeries) {
        if(log.isTraceEnabled()) {
            log.trace("addTvSeries started for " + tvSeries);
        }
        seriesDao.insert(tvSeries);
        if(tvSeries.getId() == null) {
            throw new RuntimeException("cannot got primary key id");
        }
        if(tvSeries.getTvCharacters() != null) {
            for(TvCharacter tc : tvSeries.getTvCharacters()) {
                tc.setTvSeriesId(tvSeries.getId().intValue());
                characterDao.insert(tc);
            }
        }
        return tvSeries;
    }
    
    public TvCharacter updateTvCharacter(TvCharacter tvCharacter) {
        characterDao.update(tvCharacter);
        return tvCharacter;
    }
    
    public TvCharacter addTvCharacter(TvCharacter tvCharacter) {
        characterDao.insert(tvCharacter);
        return tvCharacter;
    }

    public boolean deleteTvSeries(int id, String reason) {
        seriesDao.logicDelete(id, reason);
        return true;
    }
}
