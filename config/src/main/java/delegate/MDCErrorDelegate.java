package delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2020/3/18.
 */
public class MDCErrorDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(MDCErrorDelegate.class);
    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info("run MDCErrorDelegate ");

        throw new RuntimeException("only test");
    }
}
