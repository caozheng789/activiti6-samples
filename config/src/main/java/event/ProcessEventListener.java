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
public class ProcessEventListener implements ActivitiEventListener{


    @Override
    public void onEvent(ActivitiEvent event) {
        ActivitiEventType type = event.getType();
        if (ActivitiEventType.PROCESS_STARTED.equals(type)){
            log.info("流程启动 {} \t {}",type,event.getProcessInstanceId());
        }else if (ActivitiEventType.PROCESS_COMPLETED.equals(type)){
            log.info("流程结束 {} \t {}",type,event.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
