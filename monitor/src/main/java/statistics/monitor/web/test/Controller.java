package statistics.monitor.web.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import statistics.monitor.trace.Monitor;

/**
 * Created by dy on 2018/4/14.
 */
@org.springframework.stereotype.Controller
public class Controller {

    @Monitor("controller.a")
    @RequestMapping("/test")
    @ResponseBody
    public String a() {

        return "ok";
    }


}
