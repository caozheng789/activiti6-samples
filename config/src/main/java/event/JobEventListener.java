package event;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;

/**
 * 流程引擎监听
 *
 * Created by Administrator on 2020/3/18.
 */
@Slf4j
public class JobEventListener implements ActivitiEventListener{


    @Override
    public void onEvent(ActivitiEvent event) {
        ActivitiEventType type = event.getType();
        String name = type.name();

        if (name.startsWith("TIMER") || name.startsWith("JOB")){
            log.info("监听job {} \t {}",type,event.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
