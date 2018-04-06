package cn.devmgr.tutorial.service;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import cn.devmgr.tutorial.dto.TvCharacterDto;
import cn.devmgr.tutorial.dto.TvSeriesDto;
import cn.devmgr.tutorial.po.TvCharacter;
import cn.devmgr.tutorial.po.TvSeries;

public interface BeanConverter {

    /**
     * TvSeriesDto --> TvSeries  (DTO -> PO)
     * @param source
     * @return
     */
    public static TvSeries toTvSeries(TvSeriesDto source) {
        Mapper mapper = new DozerBeanMapper();  
        TvSeries dest = (TvSeries) mapper.map(source, TvSeries.class); 
        return dest;
    }
    
    /**
     * TvSeries --> TvSeriesDto (PO -> DTO)
     * @param source
     * @return
     */
    public static TvSeriesDto toTvSeriesDto(TvSeries source) {
        Mapper mapper = new DozerBeanMapper();  
        TvSeriesDto dest = (TvSeriesDto) mapper.map(source, TvSeriesDto.class); 
        return dest;
    }


    public static List<TvSeries> toTvSeries(List<TvSeriesDto> source) {
        List<TvSeries> dest = source.stream().map(item -> toTvSeries(item)).collect(Collectors.toList());
        return dest;
    }
    public static List<TvSeriesDto> toTvSeriesDto(List<TvSeries> source) {
        List<TvSeriesDto> dest = source.stream().map(item -> toTvSeriesDto(item)).collect(Collectors.toList());
        return dest;
    }
    
    /**
     * TvCharacterDto -> TvCharacter (DTO -> PO)
     * @param source
     * @return
     */
    public static TvCharacter toTvCharacter(TvCharacterDto source) {
        Mapper mapper = new DozerBeanMapper();  
        TvCharacter dest = (TvCharacter) mapper.map(source, TvCharacter.class); 
        return dest;
    }
    /**
     * TvCharacter -> TvCharacterDto (PO -> DTO)
     * @param source
     * @return
     */
    public static TvCharacterDto toTvCharacterDto(TvCharacter source) {
        Mapper mapper = new DozerBeanMapper();  
        TvCharacterDto dest = (TvCharacterDto) mapper.map(source, TvCharacterDto.class); 
        return dest;
    }


    public static List<TvCharacter> toTvCharacter(List<TvCharacterDto> source) {
        List<TvCharacter> dest = source.stream().map(item -> toTvCharacter(item)).collect(Collectors.toList());
        return dest;
    }
    public static List<TvCharacterDto> toTvCharacterDto(List<TvCharacter> source) {
        List<TvCharacterDto> dest = source.stream().map(item -> toTvCharacterDto(item)).collect(Collectors.toList());
        return dest;
    }
}
