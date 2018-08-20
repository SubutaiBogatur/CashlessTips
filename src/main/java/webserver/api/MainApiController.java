package webserver.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webserver.utils.Utils;

import static webserver.api.TemplateResponse.*;

@RestController
@RequestMapping("/api")
public class MainApiController {

    @RequestMapping("/registerInn")
    public BaseResponse registerInn(@RequestParam(value = "inn") Long inn) {
        boolean correctInput = Utils.validateInn(inn);
        if (!correctInput) {
            return new BaseResponse(INVALID_ARGUMENTS);
        }

        System.out.println("Adding to db!!!!");

        // TODO: @atukallo: add to db

        return new BaseResponse(SUCCESS);
    }

}
