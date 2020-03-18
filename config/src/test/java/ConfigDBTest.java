import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2020/3/18.
 */
public class ConfigDBTest {

    private static final Logger log = LoggerFactory.getLogger(ConfigDBTest.class);

    /**
     * 默认链接
     */
    @Test
    public void testConfig1(){
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResourceDefault();

        log.info("引擎配置 :{}",engineConfiguration);

        ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
        log.info("获取流程引擎 {}",processEngine.getName());
        processEngine.close();
    }


    /**
     * 指定链接
     */
    @Test
    public void testConfig2(){
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.druid.cfg.xml");

        log.info("引擎配置 :{}",engineConfiguration);

        ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
        log.info("获取流程引擎 {}",processEngine.getName());
        processEngine.close();
    }
}
