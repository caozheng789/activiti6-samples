package delegate;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2020/3/19.
 */
@Slf4j
public class HelloBean {

    public void sayHello(){
        System.out.println("===============");
        log.info("sayHello------------");
    }
}
