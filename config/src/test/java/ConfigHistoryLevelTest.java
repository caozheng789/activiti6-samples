import com.google.common.collect.Maps;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Administrator on 2020/3/18.
 */
public class ConfigHistoryLevelTest {

    private static final Logger log = LoggerFactory.getLogger(ConfigDBTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.historylevel.cfg.xml");

    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void test() {
        //启动流程
        startProcessInstance();
        //修改变量
        changeVariable();
        //提交表单 task
        submitTaskFormData();
        //输出历史内容

        //输出历史活动
        showHistoryActivity();
        //输出历史变量
        showHistoryVariable();
        //输出历史详情
        showHistoryDetail();
        //输出历史表单
        showHistoryTask();
    }

    private void showHistoryDetail() {
        List<HistoricDetail> historicDetails = activitiRule
                .getHistoryService()
                .createHistoricDetailQuery()
                .formProperties()
                .listPage(0, 100);
        for (HistoricDetail historicDetail:historicDetails) {
            log.info("historicDetail ={}",historicDetail);
        }
        log.info("historicDetails.size() = {}",historicDetails.size());
    }

    private void showHistoryTask() {
        List<HistoricTaskInstance> historicTaskInstances = activitiRule
                .getHistoryService()
                .createHistoricTaskInstanceQuery()
                .listPage(0, 100);
        for (HistoricTaskInstance historicTI:historicTaskInstances ) {
            log.info("历史表单 = {}",historicTI);
        }
        log.info("historicTaskInstances.size = {}",historicTaskInstances.size());
    }

    private void showHistoryVariable() {
        List<HistoricVariableInstance> historicVariableInstances = activitiRule
                .getHistoryService()
                .createHistoricVariableInstanceQuery()
                .listPage(0, 100);
        for (HistoricVariableInstance hvi:historicVariableInstances) {
            log.info("历史变量 = {}",hvi);
        }
        log.info("历史变量长度 = {}",historicVariableInstances.size());
    }

    private void showHistoryActivity() {
        List<HistoricActivityInstance> historicActivityInstances = activitiRule
                .getHistoryService()
                .createHistoricActivityInstanceQuery()
                .listPage(0, 100);
        for (HistoricActivityInstance historicAI:historicActivityInstances) {
            log.info("历史活动 = {}",historicAI);
        }
        log.info("historicActivityInstances.size = {}",historicActivityInstances.size());
    }

    private void submitTaskFormData() {
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        Map<String, String> properties = Maps.newHashMap();
        properties.put("formKey1","valuef1");
        properties.put("formKey2","valuef2");
        activitiRule.getFormService().submitTaskFormData(task.getId(),properties);
    }

    private void changeVariable() {
        List<Execution> executions = activitiRule.getRuntimeService()
                .createExecutionQuery().listPage(0, 100);
        for (Execution ex:executions) {
            log.info("ex ={}",ex);
        }
        log.info("ex.size = {}",executions.size());
        String id = executions.iterator().next().getId();
        activitiRule.getRuntimeService().setVariable(id,"keyStart1","value1_");
    }

    private void startProcessInstance() {
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("keyStart1","value1");
        map.put("keyStart2","value2");
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
    }
}
