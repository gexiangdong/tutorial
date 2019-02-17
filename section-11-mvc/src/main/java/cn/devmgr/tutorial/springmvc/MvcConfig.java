package cn.devmgr.tutorial.springmvc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(MvcConfig.class);


    @Override
    public void addFormatters(final FormatterRegistry registry) {
        logger.trace("addFormatters");
        // 这里设置的 DateFormatter 会影响到向controller提交的日期的格式
        // thymeleaf 中用 {{ }}写的日期的转换格式
        // 这里设置的比在pojo里的@DateTimeFormat 优先级高
        // 这里设置和在 application.yml 中 spring.mvc.date-format 一样的效果
        // 建议不用
        // 也可设置其他类型的转换器；包含自定义类型
//         registry.addFormatter(new DateFormatter());
    }


//    @Bean
//    public DateFormatter dateFormatter() {
//        return new DateFormatter();
//    }

    public class DateFormatter implements Formatter<Date> {

        @Override
        public Date parse(final String text, final Locale locale) throws ParseException {
            logger.trace("parse {} {}", text, locale);
            final SimpleDateFormat dateFormat = createDateFormat(locale);
            return dateFormat.parse(text);
        }

        @Override
        public String print(final Date object, final Locale locale) {
            logger.trace("print {} {}", object, locale);
            final SimpleDateFormat dateFormat = createDateFormat(locale);
            return dateFormat.format(object);
        }

        private SimpleDateFormat createDateFormat(final Locale locale) {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d yyyy hh:mm:ss a", locale);
            dateFormat.setLenient(false);
            return dateFormat;
        }

    }
}