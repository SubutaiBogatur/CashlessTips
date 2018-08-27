package webserver.controllers.error_handling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomErrorController implements ErrorController {

    private Logger logger = LoggerFactory.getLogger(CustomErrorController.class);;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String handleError() {
        logger.info("Displaying error message");

        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/dunnoWhatIsIt";
    }

}
