package interceptor;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;

/**
 * 执行时间
 * Created by Administrator on 2020/3/19.
 */
@Slf4j
public class DurationCommandInterceptor extends AbstractCommandInterceptor{
    @Override
    public <T> T execute(CommandConfig commandConfig, Command<T> command) {
        long start = System.currentTimeMillis();
        try {
            return this.getNext().execute(commandConfig, command);
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("{} 执行时长 {} 毫秒",command.getClass().getSimpleName(),duration);
        }
    }
}
