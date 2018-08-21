package webserver.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webserver.dbs.KktRepository;
import webserver.dbs.RegisteredInn;
import webserver.dbs.InnRepository;
import webserver.dbs.RegisteredKkt;
import webserver.utils.Utils;

import static webserver.api.TemplateResponse.*;

@RestController
@RequestMapping("/api")
public class MainApiController {

    @Autowired
    private InnRepository innRepository;
    @Autowired
    private KktRepository kktRepository;

    private Logger logger = LoggerFactory.getLogger(MainApiController.class);

    @RequestMapping("/registerInn")
    public BaseResponse registerInn(@RequestParam(value = "inn") String inn) {
        boolean correctInput = Utils.validateInn(inn);
        if (!correctInput) {
            return new BaseResponse(INVALID_ARGUMENTS);
        }

        if (innRepository.existsByInn(inn)) {
            return new BaseResponse(ALREADY_PRESENT);
        }

        RegisteredInn innEntity = new RegisteredInn();
        innEntity.setInn(inn);
        innRepository.save(innEntity);

        logger.info("Succesfully added inn " + inn + " to db");
        return new BaseResponse(SUCCESS);
    }

    @RequestMapping("/registerKkt")
    public BaseResponse registerInn(@RequestParam(value = "inn") String inn, @RequestParam(value = "kkt") String kkt) {
        boolean correctInput = Utils.validateInn(inn) && Utils.validateKkt(kkt);
        if (!correctInput) {
            return new BaseResponse(INVALID_ARGUMENTS);
        }

        if (kktRepository.existsByKkt(kkt)) {
            return new BaseResponse(ALREADY_PRESENT);
        }

        if (!innRepository.existsByInn(inn)) {
            return new BaseResponse("Such inn was not registered");
        }

        RegisteredKkt kktEntity = new RegisteredKkt();
        kktEntity.setInn(inn);
        kktEntity.setKkt(kkt);
        kktRepository.save(kktEntity);

        logger.info("Succesfully added kkt: (inn, kkt): (" + inn + ", " + kkt + ") to db");
        return new BaseResponse(SUCCESS);
    }

}
