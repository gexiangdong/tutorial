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
import cn.devmgr.tutorial.dto.TvCharacterDto;
import cn.devmgr.tutorial.dto.TvSeriesDto;
import cn.devmgr.tutorial.po.TvCharacter;
import cn.devmgr.tutorial.po.TvSeries;

@Service
public class TvSeriesService {
    private final Log log = LogFactory.getLog(TvSeriesService.class);
    
    @Autowired private TvSeriesDao seriesDao;
    @Autowired private TvCharacterDao characterDao;

    @Transactional(readOnly=true)
    public List<TvSeriesDto> getAllTvSeries(){
        if(log.isTraceEnabled()) {
            log.trace("getAllTvSeries started   ");
        }
        List<TvSeries> list = seriesDao.findAll();
        return BeanConverter.toTvSeriesDto(list);
    }
    
    @Transactional(readOnly=true)
    public TvSeriesDto getTvSeriesById(int tvSeriesId) {
        if(log.isTraceEnabled()) {
            log.trace("getTvSeriesById started for " + tvSeriesId);
        }
        TvSeries series = seriesDao.getOne(tvSeriesId);
        if(series == null) {
            return null;
        }
        TvSeriesDto seriesDto = BeanConverter.toTvSeriesDto(series);
        List<TvCharacter> list = characterDao.getAllByTvSeriesId(tvSeriesId);
        seriesDto.setTvCharacters(BeanConverter.toTvCharacterDto(list));
        return seriesDto;
    }
    
    public TvSeriesDto updateTvSeries(TvSeriesDto tvSeriesDto) {
        if(log.isTraceEnabled()) {
            log.trace("updateTvSeries started for " + tvSeriesDto);
        }
        TvSeries tvSeries = BeanConverter.toTvSeries(tvSeriesDto);
        seriesDao.save(tvSeries);
        return BeanConverter.toTvSeriesDto(tvSeries);
    }
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public TvSeriesDto addTvSeries(TvSeriesDto tvSeriesDto) {
        if(log.isTraceEnabled()) {
            log.trace("addTvSeries started for " + tvSeriesDto);
        }
        TvSeries tvSeries = BeanConverter.toTvSeries(tvSeriesDto);
        seriesDao.save(tvSeries);
        if(tvSeries.getId() == null) {
            throw new RuntimeException("cannot got primary key id");
        }
        if(tvSeriesDto.getTvCharacters() != null) {
            for(TvCharacterDto tc : tvSeriesDto.getTvCharacters()) {
                tc.setTvSeriesId(tvSeries.getId().intValue());
                characterDao.save(BeanConverter.toTvCharacter(tc));
            }
        }
        return BeanConverter.toTvSeriesDto(tvSeries);
    }
    
    public TvCharacterDto updateTvCharacter(TvCharacterDto tvCharacterDto) {
        TvCharacter result = characterDao.save(BeanConverter.toTvCharacter(tvCharacterDto));
        return BeanConverter.toTvCharacterDto(result);
    }
    
    public TvCharacterDto addTvCharacter(TvCharacterDto tvCharacterDto) {
        TvCharacter result = characterDao.save(BeanConverter.toTvCharacter(tvCharacterDto));
        return BeanConverter.toTvCharacterDto(result);
    }

    public boolean deleteTvSeries(int id, String reason) {
        int rows = seriesDao.logicDelete(id, reason);
        return (rows == 1);
    }
    
    public List<TvCharacterDto> getTvCharacterByTvSeriesId(int tvSeriesId) throws Exception{
        List<TvCharacter> list = characterDao.getAllByTvSeriesId(tvSeriesId);
        return BeanConverter.toTvCharacterDto(list);
    }
    
}
