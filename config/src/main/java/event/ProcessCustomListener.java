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
public class ProcessCustomListener implements ActivitiEventListener{


    @Override
    public void onEvent(ActivitiEvent event) {
        ActivitiEventType type = event.getType();
        if (ActivitiEventType.CUSTOM.equals(type)){
            log.info("自定义事件监听 {} \t {}",type,event.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
