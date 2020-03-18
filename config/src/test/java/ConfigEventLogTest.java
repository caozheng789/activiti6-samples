import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Administrator on 2020/3/18.
 */
public class ConfigEventLogTest {

    private static final Logger log = LoggerFactory.getLogger(ConfigEventLogTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.eventlog.cfg.xml");

    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void test() {

        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        activitiRule.getTaskService().complete(task.getId());

        List<EventLogEntry> eventLogEntries = activitiRule.getManagementService()
                .getEventLogEntriesByProcessInstanceId(
                        processInstance.getProcessInstanceId());
        for (EventLogEntry eventLog:eventLogEntries) {
            log.info("eventLog.type = {},eventLog.data = {} ",eventLog.getType()
            ,new String(eventLog.getData()) );
        }
        log.info("eventLog.size = {} " , eventLogEntries.size());
    }
}
