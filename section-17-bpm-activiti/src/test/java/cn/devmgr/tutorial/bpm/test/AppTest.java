package cn.devmgr.tutorial.bpm.test;


import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class AppTest {
    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Autowired(required = false)
    TaskService taskService;

    @Autowired(required = false)
    IdentityService identityService;

    @Autowired(required = false)
    RuntimeService runtimeService;

    @Autowired(required = false)
    HistoryService historyService;

    @Autowired(required = false)
    FormService formService;

    @Test
    public void listTasks() {
        // 1. 查寻/设置 用户和组
        logger.trace("query and create managers group and user waytt if they are not exist.");
        String groupId = "hosts";
        String userId = "dolores";
        //查询有没有ID为 managers 的组
        List<Group> hostGroups = identityService.createGroupQuery().groupId(groupId).list();
        Group hostGroup;
        if(hostGroups == null || hostGroups.size() == 0) {
            // 没有，增加一个
            hostGroup = identityService.newGroup(groupId); //参数是groupId
            hostGroup.setName("接待员");
            hostGroup.setType("business-role");
            identityService.saveGroup(hostGroup);
        }else{
            hostGroup = hostGroups.get(0);
        }

        List<User> users = identityService.createUserQuery().userId(userId).list();
        User userDolores;
        if(users == null || users.size() == 0) {
            userDolores = identityService.newUser(userId);
            userDolores.setFirstName("Dolores");
            userDolores.setLastName("Abernathy");
            userDolores.setPassword("none");
            userDolores.setEmail("dolores@westworld.com");
            identityService.saveUser(userDolores);
            identityService.createMembership(userDolores.getId(), hostGroup.getId());
        }else{
            userDolores = users.get(0);
        }


        logger.trace("listTasks");
        List<Task> tasks = taskService.createTaskQuery().list();

        if(tasks == null || tasks.size() == 0){
            logger.trace("donot found any tasks; add one");

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("applicantName", "Bernard Lowe");
            variables.put("email", "bernard@westworld.com");
            variables.put("phoneNumber", "123456789");
            runtimeService.startProcessInstanceByKey("hireProcess", variables);
        }else{
            for(Task t : tasks){
                logger.trace("  task {}, {}, {}, {}, {}", t.getCategory(), t.getClaimTime(),
                        t.getName(), t.getId(), t.getFormKey());


                TaskFormData taskFormData = formService.getTaskFormData(t.getId());
                List<FormProperty> formProperties = taskFormData.getFormProperties();
                for (FormProperty property : formProperties) {
                    logger.trace("  FormData property  id: {}, name: {}, type: {} value: {}", property.getId(), property.getName(), property.getType(), property.getValue());
                }

            }
        }

        //query again
        tasks = taskService.createTaskQuery().list();
        assertTrue(tasks.size() > 0);
        for(int i=0; i<tasks.size() && i < 3; i++) {
            //认领几个任务（最多3个）
            Task task = tasks.get(i);
            //认领此任务
            taskService.claim(task.getId(), userDolores.getId());
        }

        //查看dolores用户当前的任务列表
        List<Task> tasksOfDolores = taskService.createTaskQuery().taskAssignee(userDolores.getId()).list();
        for (Task dt : tasksOfDolores) {
            logger.trace("Dolores's task: {}, {}, {}", dt.getId(), dt.getCreateTime(), dt.getProcessVariables());
            // 设置此任务为完成状态
            Map<String, Object> vars = new HashMap<>();
            vars.put("telephoneInterviewOutcome", true);
            vars.put("techOk", false);
            vars.put("financialOk", true);
            taskService.complete(dt.getId(), vars);

            // 查询这个任务对应的流程的最终状态
            HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(dt.getProcessInstanceId()).singleResult();
            logger.trace("HistoricProcessInstance {}, {}, {}, {}", hpi.getName(), hpi.getEndActivityId(), hpi.getStartTime(), hpi.getEndTime());
        }


    }

}
