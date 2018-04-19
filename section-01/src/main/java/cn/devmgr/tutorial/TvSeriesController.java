package cn.devmgr.tutorial;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/**
 * RestController的一个例子，展示了各种基本操作在RestController的实现方式。
 *
 */
@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {
    private final Log log = LogFactory.getLog(TvSeriesController.class);
    
    @GetMapping
    public List<TvSeriesDto> getAll() {
        if(log.isTraceEnabled()) {
            log.trace("getAll() ");
        }
        List<TvSeriesDto> list = new ArrayList<>();
        list.add(createWestWorld());
        list.add(createPoi());
        
        return list;
    }
    
    @GetMapping("/{id}")
    public TvSeriesDto getOne(@PathVariable int id){
        if(log.isTraceEnabled()) {
            log.trace("getOne " + id);
        }
        if(id == 101) {
            return createWestWorld();
        }else if(id == 102) {
            return createPoi();
        }else {
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * @Valid 注解表示需要验证传入的参数TvSeriesDto，需要验证的field在TvSeriesDto内通过注解定义（@NotNull, @DecimalMin等）
     * @param tvSeriesDto
     * @return
     */
    @PostMapping
    public TvSeriesDto insertOne(@Valid @RequestBody TvSeriesDto tvSeriesDto) {
        if(log.isTraceEnabled()) {
            log.trace("这里应该写新增tvSeriesDto到数据库的代码, 传递进来的参数是：" + tvSeriesDto);
        }
        //TODO:在数据
        tvSeriesDto.setId(9999);
        return tvSeriesDto;
    }
    
    /**
     * 
     * @param id
     * @param tvSeriesDto
     * @return
     */
    @PutMapping("/{id}")
    public TvSeriesDto updateOne(@PathVariable int id, @RequestBody TvSeriesDto tvSeriesDto){
        if(log.isTraceEnabled()) {
            log.trace("updateOne " + id);
        }
        if(id == 101 || id == 102) {
            //TODO: 根据tvSeriesDto的内容更新数据库，更新后返回新
            return createPoi();
        }else {
            throw new ResourceNotFoundException();
        }
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
        if(id == 101) {
            //TODO: 执行删除的代码
            result.put("message", "#101被" + request.getRemoteAddr() + "删除(原因：" + deleteReason + ")");
        }else if(id == 102) {
            //不能删除这个，RuntimeException不如org.springframework.security.access.AccessDeniedException更合适
            //但此处还没到spring security，所以暂先抛出RuntimeException异常
            throw new RuntimeException("#102不能删除");
        }else {
            //不存在
            throw new ResourceNotFoundException();
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
    public void addPhoto(@PathVariable int id, @RequestParam("photo") MultipartFile imgFile) throws Exception{
        if(log.isTraceEnabled()) {
            log.trace("接受到文件 " + id + "收到文件：" + imgFile.getOriginalFilename());
        }
        //保存文件
        FileOutputStream fos = new FileOutputStream("target/" + imgFile.getOriginalFilename());
        IOUtils.copy(imgFile.getInputStream(), fos);
        fos.close();
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
    
    
    
    /**
     * 创建电视剧“Person of Interest",仅仅方便此节做展示其他方法用，以后章节把数据存储到数据库后，会删除此方法
     */
    private TvSeriesDto createPoi() {
        Calendar c = Calendar.getInstance();
        c.set(2011, Calendar.SEPTEMBER, 22, 0, 0, 0);
        return new TvSeriesDto(102, "Person of Interest", 5, c.getTime());
    }
    /**
     * 创建电视剧“West World",仅仅方便此节做展示其他方法用，以后章节把数据存储到数据库后，会删除此方法
     */
    private TvSeriesDto createWestWorld() {
        Calendar c = Calendar.getInstance();
        c.set(2016, Calendar.OCTOBER, 2, 0, 0, 0);
        return new TvSeriesDto(101, "West World", 1, c.getTime());
    }
    
}
