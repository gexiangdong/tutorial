package cn.devmgr.tutorial;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.devmgr.tutorial.controller.TvSeriesController;
import cn.devmgr.tutorial.dao.TvCharacterDao;
import cn.devmgr.tutorial.dao.TvSeriesDao;
import cn.devmgr.tutorial.pojo.TvCharacter;
import cn.devmgr.tutorial.pojo.TvSeries;

/**
 * 测试web控制层和业务逻辑层，mock数据访问层的类，以避免数据库内数据差异造成的难以确定测试标准。
 * 和TvSeriesServiceTest相比，类上多了AutoConfigureMockMvc注解，这是初始化一个mvc环境用于测试
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppTests {

    /**
     * @MockBean可给当前的spring context装载一个假的bean上去替代原有的同名bean；
     * mock了dao的所有bean后，数据访问层就别接管了，从而实现测试不受具体数据库内数据值影响的结果
     */
    @MockBean
    TvSeriesDao tvSeriesDao;
    @MockBean
    TvCharacterDao tvCharacterDao;

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired private TvSeriesController tvSeriesController;

    @Test
    public void contextLoads() throws Exception {
        //这个方法虽然啥内容都没有，但如果spring-boot的配置有问题，例如需要autowire的bean不存在等，是不能被执行到这步的。
        //所以，如果没有任何测试用例时，写这么个空的也是好过没有的。如果有了其他有具体内容的测试用例，这个空测试用例就没存在的必要了。
    }

    @Test
    public void testGetAll() throws Exception {
        List<TvSeries> list = new ArrayList<>();
        TvSeries ts = new TvSeries();
        ts.setName("POI");
        list.add(ts);
        //这些桩模块的加载可参考TvSeriesServiceTest中的例子
        Mockito.when(tvSeriesDao.getAll()).thenReturn(list);

        //下面这个是相当于在启动项目后，执行 GET /tvseries，被测模块是web控制层，因为web控制层会调用业务逻辑层，所以业务逻辑层也会被测试
        //业务逻辑层调用了被mock出来的数据访问层桩模块。
        //如果想仅仅测试web控制层，（例如业务逻辑层尚未编码完毕），可以mock一个业务逻辑层的桩模块
        mockMvc.perform(MockMvcRequestBuilders.get("/tvseries")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("POI")));
        //上面这几句和字面意思一致，期望状态是200，返回值包含POI三个字面，桩模块返回的一个电视剧名字是POI，如果测试正确是包含这三个字母的。
    }
    
    @Test
    public void testAddSeries() throws Exception{
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                TvSeries ts = (TvSeries) args[0];
                Assert.assertEquals(ts.getName(), "疑犯追踪");
                ts.setId(5432);
                bitSet.set(0, true);
                return 1;
            }
        }).when(tvSeriesDao).insert(Mockito.any(TvSeries.class));
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                TvCharacter tc = (TvCharacter) args[0];
                //应该是json中传递过来的剧中角色名字
                Assert.assertEquals(tc.getName(), "芬奇");
                Assert.assertTrue(tc.getTvSeriesId() == 5432);
                bitSet.set(0, true);
                return 1;
            }
        }).when(tvCharacterDao).insert(Mockito.any(TvCharacter.class));
        
        String jsonData = "{\"name\":\"疑犯追踪\",\"seasonCount\":5,\"originRelease\":\"2011-09-22\",\"tvCharacters\":[{\"name\":\"芬奇\"}]}";
        this.mockMvc.perform(MockMvcRequestBuilders.post("/tvseries").contentType(MediaType.APPLICATION_JSON).content(jsonData))
                     .andDo(MockMvcResultHandlers.print())
                     .andExpect(MockMvcResultMatchers.status().isOk());
        
        //确保tvCharacterDao.insert被访问过
        Assert.assertTrue(bitSet.get(0));
    }
    
    @Test
    public void testFileUpload() throws Exception{
        String fileFolder = "target/files/";
        File folder = new File(fileFolder);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        // 下面这句可以设置bean里面通过@Value获得的数据
        ReflectionTestUtils.setField(tvSeriesController, "uploadFolder", folder.getAbsolutePath());
        
        InputStream is = getClass().getResourceAsStream("/testfileupload.jpg");
        if(is == null) {
            throw new RuntimeException("需要先在src/test/resources目录下放置一张jpg文件，名为testfileupload.jpg然后运行测试");
        }
        
        //模拟一个文件上传的请求
        MockMultipartFile imgFile = new MockMultipartFile("photo", "testfileupload.jpg", "image/jpeg", IOUtils.toByteArray(is));
        
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/tvseries/1/photos")
                        .file(imgFile))
                        .andExpect(MockMvcResultMatchers.status().isOk());
        
        //解析返回的JSON
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(result.andReturn().getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
       
        String fileName = (String) map.get("photo");
        File f2 = new File(folder, fileName);
        //返回的文件名，应该已经保存在filFolder文件夹下
        Assert.assertTrue(f2.exists());
    }
}
