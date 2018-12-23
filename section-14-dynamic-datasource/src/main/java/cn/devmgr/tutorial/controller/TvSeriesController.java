package cn.devmgr.tutorial.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.devmgr.tutorial.KeyValueGroup;
import cn.devmgr.tutorial.service.CustomerService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.devmgr.tutorial.pojo.TvSeries;
import cn.devmgr.tutorial.service.TvSeriesService;

/**
 * RestController的一个例子，展示了各种基本操作在RestController的实现方式。
 *
 */
@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {
    private final Log log = LogFactory.getLog(TvSeriesController.class);
    
    @Value("${tutorial.uploadFolder:target/files}") String uploadFolder;
    @Autowired TvSeriesService tvSeriesService;

    @Autowired
    CustomerService customerService;

    @Autowired
    KeyValueGroup kvg;

    @GetMapping
    public Map<String, Object> getAll() {
        if(log.isTraceEnabled()) {
            log.trace("getAll() ");
        }
        log.trace(kvg);
        log.trace(kvg.getMaps().get("categories").get(1));

        Map<String, Object> result = new HashMap<>();

        List<TvSeries> list = tvSeriesService.getAllTvSeries();
        result.put("list", list);
        result.put("customer", customerService.getCustomerById(1));
        result.put("tvseries", tvSeriesService.getTvSeriesById(1));
        return result;
    }

    /**
     * 给电视剧添加剧照。
     * 这是一个文件上传的例子（上传代码参照test.UploadFile.main）
     */
    @PostMapping(value="/{tvSeriesId}/photos", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Map<String, Object> addPhoto(@PathVariable String tvSeriesId, @RequestParam String fileId, @RequestParam("photo") MultipartFile[] imgFiles) throws Exception{
        if(log.isTraceEnabled()) {
            log.trace("接受到 " + tvSeriesId + "收到文件：" + imgFiles == null ? 0 : imgFiles.length); //.getOriginalFilename());
        }
        //保存文件
        File folder = new File(uploadFolder);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        if(imgFiles == null){
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        List<String> files = new ArrayList<>();
        for(MultipartFile imgFile : imgFiles) {
            String fileName = imgFile.getOriginalFilename();
            if (fileName.endsWith(".jpg")) {
                FileOutputStream fos = new FileOutputStream(new File(folder, fileName));
                IOUtils.copy(imgFile.getInputStream(), fos);
                fos.close();
                files.add(fileName);
            } else {
                throw new RuntimeException("不支持的格式，仅支持jpg格式");
            }
        }
        result.put("photos", files);
        result.put("tvSeriesId", tvSeriesId);
        result.put("fileId", fileId);
        return result;
    }

}
