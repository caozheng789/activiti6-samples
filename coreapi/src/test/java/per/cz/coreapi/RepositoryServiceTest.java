package per.cz.coreapi;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.*;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2020/3/19.
 */
@Slf4j
public class RepositoryServiceTest {

    @Rule
    public ActivitiRule activitiRule =  new ActivitiRule();


    @Test
    public void testRepository(){
        RepositoryService repositoryService = activitiRule.getRepositoryService();

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.name("测试资源部署,添加两个流程文件1")
                .addClasspathResource("process/my-process.bpmn20.xml")
                .addClasspathResource("process/second_approve.bpmn20.xml");

        //模拟二次部署
        DeploymentBuilder deploymentBuilder1 = repositoryService.createDeployment();
        deploymentBuilder1.name("测试资源部署,添加两个流程文件2")
                .addClasspathResource("process/my-process.bpmn20.xml")
                .addClasspathResource("process/second_approve.bpmn20.xml");
        deploymentBuilder1.deploy();

        //部署
        Deployment deploy = deploymentBuilder.deploy();
        log.info("deploy = {}",deploy);
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();

        //查询部署的流程信息
//        Deployment deployment = deploymentQuery.deploymentId(deploy.getId()).singleResult();
        List<Deployment> deployments = deploymentQuery.orderByDeploymenTime().asc().listPage(0, 100);
        for (Deployment deployment:deployments) {
            log.info("deployment = {}",deployment);
        }
        log.info("deployments.size() = {}",deployments.size());

//        List<ProcessDefinition> processDefinitions = repositoryService
//                .createProcessDefinitionQuery()
//                .deploymentId(deployment.getId()).listPage(0, 100);

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().listPage(0, 100);

        for (ProcessDefinition definition : processDefinitions) {
            log.info("definition = {} , name = {} ,version = {}, key ={} ,id = {}",definition
                    ,definition.getName()
                    ,definition.getVersion()
                    ,definition.getKey()
                    ,definition.getId());
        }

    }

    /**
     * 挂起，启动
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = {"process/my-process.bpmn20.xml"})
    public void testSuspend(){
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition definition = repositoryService
                .createProcessDefinitionQuery().singleResult();
        log.info("processDefinition.id={}", definition.getId());
        //暂停任务  对流程定义文件进行挂起，暂停
        repositoryService.suspendProcessDefinitionById(definition.getId());

        try {
            log.info("启动");
            //先暂停再启动肯定会失败，抛异常
            activitiRule.getRuntimeService().startProcessInstanceById(definition.getId());
            log.info("启动成功");
        } catch (Exception e) {
            log.info("启动失败");
        }
        //激活任务
        repositoryService.activateProcessDefinitionById(definition.getId());
        log.info("启动");
        //先激活再启动就成功啦。
        activitiRule.getRuntimeService().startProcessInstanceById(definition.getId());
        log.info("启动成功");
    }


    /**
     * 指定用户组
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = {"process/my-process.bpmn20.xml"})
    public void testCandidateStarter(){
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition definition = repositoryService
                .createProcessDefinitionQuery().singleResult();
        log.info("processDefinition.id={}", definition.getId());

        //添加
        repositoryService.addCandidateStarterUser(definition.getId(),"userA");
        repositoryService.addCandidateStarterGroup(definition.getId(),"groupM");

        //获取
        List<IdentityLink> identityLinks = repositoryService.getIdentityLinksForProcessDefinition(definition.getId());
        for (IdentityLink ids : identityLinks) {
            log.info("ids = {}" ,ids );
        }

        //删除
        repositoryService.deleteCandidateStarterGroup(definition.getId(),"groupM");
        repositoryService.deleteCandidateStarterUser(definition.getId(),"userA");

    }



}
