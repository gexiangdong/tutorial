package cn.devmgr.tutorial;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import cn.devmgr.tutorial.dao.TvCharacterDao;
import cn.devmgr.tutorial.dao.TvSeriesDao;
import cn.devmgr.tutorial.pojo.TvSeries;
import cn.devmgr.tutorial.service.TvSeriesService;

/**
 * 此测试用例是测试业务逻辑层TvSeriesService的例子，业务逻辑层下的数据访问层通过MockBean加载的桩模块实现。
 * 避免了测试过程中对数据库内数据的依赖，造成很难写成功失败标准的状况
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TvSeriesServiceTest {
    /**
     * 实现一个单元测试用的桩模块
     * @MockBean可给当前的spring context装载一个假的bean上去替代原有的同名bean；
     * mock了dao的所有bean后，数据访问层就别接管了，从而实现测试不受具体数据库内数据值影响的结果
     */
    @MockBean TvSeriesDao tvSeriesDao;
    @MockBean TvCharacterDao tvCharacterDao;

    @Autowired TvSeriesService tvSeriesService;
    
    @Test
    public void testGetAllWithoutMockit() {
        List<TvSeries> list = tvSeriesService.getAllTvSeries();
        //这里的测试结果依赖连接数据库内的记录，很难写一个判断是否成功的条件，甚至无法执行
        //下面的testGetAll()方法，使用了mock出来的dao作为桩模块，避免了这一情形
        Assert.assertTrue(list.size() >= 0);
    }
    
    @Test
    public void testGetAll() {
        //设置一个TvSeries List
        List<TvSeries> list = new ArrayList<>();
        TvSeries ts = new TvSeries();
        ts.setName("POI");
        list.add(ts);
        
        //下面这个语句是告诉mock出来tvSeriesDao当执行getAll方法时，返回上面创建的那个list
        Mockito.when(tvSeriesDao.getAll()).thenReturn(list);
        
        //测试tvSeriesService的getAllTvSeries()方法，获得返回值
        List<TvSeries> result = tvSeriesService.getAllTvSeries();
        
        //判断返回值是否和最初做的那个list相同，应该是相同的。
        Assert.assertTrue(result.size() == list.size());
        Assert.assertTrue("POI".equals(result.get(0).getName()));   
    }
    
    @Test
    public void testGetOne() {
        //根据不同的传入参数，被mock的bean返回不同的数据的例子
        String newName = "Person Of Interest";
        BitSet mockExecuted = new BitSet();
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                TvSeries bean = (TvSeries) args[0];
                //传入的值，应该和下面调用tvSeriesService.updateTvSeries()方法时的参数中的值相同
                Assert.assertEquals(newName, bean.getName());
                mockExecuted.set(0);
                return 1;
            }
        }).when(tvSeriesDao).update(any(TvSeries.class));
        
        TvSeries ts = new TvSeries();
        ts.setName(newName);
        ts.setId(111);
        
        tvSeriesService.updateTvSeries(ts);
        Assert.assertTrue(mockExecuted.get(0));
    }
}
