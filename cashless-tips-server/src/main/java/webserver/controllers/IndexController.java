package webserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping("/index")
    public String index() {
        // defaultly index.html is returned
        // this class is not obligatory, because our ErrorHandler also returns index.html, but it seems like better codestyle
        return "index";
    }
}
