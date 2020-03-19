import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 2020/3/18.
 */
public class ConfigJobTest {

    private static final Logger log = LoggerFactory.getLogger(ConfigJobTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.job.cfg.xml");

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void test() throws InterruptedException {
        log.info("start");
        List<Job> jobs = activitiRule.getManagementService()
                .createTimerJobQuery()
                .listPage(0, 100);
        for (Job job:jobs) {
            log.info("定时任务 {}, 默认重复次数 {}",job,job.getRetries());
        }
        log.info("jobs.size {}" ,jobs.size());
        Thread.sleep(1000*100);
        log.info("end");




    }
}
