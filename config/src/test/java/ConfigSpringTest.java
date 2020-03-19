import delegate.HelloBean;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Administrator
 * @version V1.0.0
 * @date 2019/1/1 0001 0:23
 */
@Slf4j
@ContextConfiguration(locations = {"classpath:activiti-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfigSpringTest {
    @Rule
    @Autowired
    public ActivitiRule activitiRule;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HelloBean helloBean;

    @Test
    @Deployment(resources = {"my-process-spring.bpmn20.xml"})
    public void test() {
        //打开MDC记录日志
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");
        Task task = taskService.createTaskQuery().singleResult();
        taskService.complete(task.getId());

    }

}



