package cn.devmgr.tutorial.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
    
    @GetMapping
    public List<TvSeries> getAll() {
        if(log.isTraceEnabled()) {
            log.trace("getAll() ");
        }
        
        return tvSeriesService.getAllTvSeries();
    }
    
    @GetMapping("/{id}")
    public TvSeries getOne(@PathVariable int id){
        if(log.isTraceEnabled()) {
            log.trace("getOne " + id);
        }
        TvSeries ts = tvSeriesService.getTvSeriesById(id);
        if(ts == null) {
            throw new ResourceNotFoundException();
        }
        return ts;
    }
    
    @PostMapping
    public TvSeries insertOne(@RequestBody TvSeries tvSeries) {
        if(log.isTraceEnabled()) {
            log.trace("这里应该写新增TvSeries到数据库的代码, 传递进来的参数是：" + tvSeries);
        }
        tvSeriesService.addTvSeries(tvSeries);
        return tvSeries;
    }
    
    /**
     * 
     * @param id
     * @param TvSeries
     * @return
     */
    @PutMapping("/{id}")
    public TvSeries updateOne(@PathVariable int id, @RequestBody TvSeries tvSeries){
        if(log.isTraceEnabled()) {
            log.trace("updateOne " + id);
        }
        TvSeries ts = tvSeriesService.getTvSeriesById(id);
        if(ts == null) {
            throw new ResourceNotFoundException();
        }
        ts.setSeasonCount(tvSeries.getSeasonCount());
        ts.setName(tvSeries.getName());
        ts.setOriginRelease(tvSeries.getOriginRelease());
        tvSeriesService.updateTvSeries(ts);
        return ts;
    }
    
    /**
     * 删除资源的例子；如果方法圣母了HttpServletRequest request参数，spring会自动把当前的request传给方法。
     * 类似声明即可得到还有 HttpServletResponse，Authentication、Locale等
     * 
     * @RequestParam(value="delete_reason", required=false) String deleteReason 表示deleteReason参数的值
     * 来自Request的参数delete_reason（等同于request.getParameter("delete_reason")，可以是URL中Querystring，
     * 也可以是form post里的值），required=false表示不是必须的。默认是required=true，required=true时，如果请求
     * 没有传递这个参数，会被返回400错误。
     * 类似的注解还有@CookieValue @RequestHeader等。
     */
    @DeleteMapping("/{id}")
    public Map<String, String> deleteOne(@PathVariable int id, HttpServletRequest request, 
                @RequestParam(value="delete_reason", required=false) String deleteReason) throws Exception{
        if(log.isTraceEnabled()) {
            log.trace("deleteOne " + id);
        }
        Map<String, String> result = new HashMap<>();
        TvSeries ts = tvSeriesService.getTvSeriesById(id);
        if(ts != null) {
            throw new ResourceNotFoundException();
        }else {
            tvSeriesService.deleteTvSeries(id, deleteReason);
            result.put("message", "#" + id + "被" + request.getRemoteAddr() + "删除(原因：" + deleteReason + ")");
        }
        return result;
    }
    
    /**
     * 给电视剧添加剧照。
     * 这是一个文件上传的例子（具体上传处理代码没有写）
     * @param id
     * @param imgFile
     * @throws Exception
     */
    @PostMapping(value="/{id}/photos", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> addPhoto(@PathVariable int id, @RequestParam("photo") MultipartFile imgFile) throws Exception{
        if(log.isTraceEnabled()) {
            log.trace("接受到文件 " + id + "收到文件：" + imgFile.getOriginalFilename());
        }
        //保存文件
        File folder = new File(uploadFolder);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = imgFile.getOriginalFilename();
        if(fileName.endsWith(".jpg")) {
            FileOutputStream fos = new FileOutputStream(new File(folder, fileName));
            IOUtils.copy(imgFile.getInputStream(), fos);
            fos.close();
            Map<String, String> result = new HashMap<>();
            result.put("photo", fileName);
            return result;
        }else {
            throw new RuntimeException("不支持的格式，仅支持jpg格式");
        }
    }
    
    /**
     * 返回某电视剧的图标
     * 这是一个返回非JSON格式（图片）格式的例子
     * @param id
     * @return
     */
    @GetMapping(value="/{id}/icon", produces=MediaType.IMAGE_JPEG_VALUE)
    public byte[] getIcon(@PathVariable int id) throws Exception{
        if(log.isTraceEnabled()) {
            log.trace("getIcon(" + id + ")");
        }
        String iconFile = "src/test/resources/icon.jpg";
        InputStream is = new FileInputStream(iconFile);
        return IOUtils.toByteArray(is);
    }

}
