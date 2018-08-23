package webserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webserver.controllers.response_utils.BaseResponse;
import webserver.controllers.response_utils.TipsServerException;
import webserver.dbs.*;
import webserver.utils.Utils;

import javax.rmi.CORBA.Util;
import javax.xml.ws.Response;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static webserver.controllers.response_utils.TemplateResponse.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private InnRepository innRepository;
    @Autowired
    private FnRepository fnRepository;
    @Autowired
    private ReceiptRepository receiptRepository;

    private Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping("/registerInn")
    public ResponseEntity<Object> registerInn(@RequestParam(value = "inn") String inn, @RequestParam(value = "preferredTips", defaultValue = "10") Integer preferredTips) throws TipsServerException {
        boolean correctInput = Utils.validateInn(inn);
        if (!correctInput) {
            throw TipsServerException.invalidArguments();
        }

        if (preferredTips < 0 || preferredTips > 100) {
            throw new TipsServerException("Invalid percentage for preferred tips");
        }

        if (innRepository.existsByInn(inn)) {
            throw new TipsServerException("Such inn was already registered");
        }

        RegisteredInn innEntity = new RegisteredInn();
        innEntity.setInn(inn);
        innEntity.setPreferredTips(preferredTips);
        innRepository.save(innEntity);

        logger.info("Succesfully added inn " + inn + " to db");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping("/registerFn")
    public ResponseEntity<Object> registerFn(@RequestParam(value = "inn") String inn, @RequestParam(value = "fn") String fn) throws TipsServerException {
        boolean correctInput = Utils.validateInn(inn) && Utils.validateFn(fn);
        if (!correctInput) {
            throw TipsServerException.invalidArguments();
        }

        if (fnRepository.existsByFn(fn)) {
            throw new TipsServerException("Such fnn already exists");
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("Such inn was not registered");
        }

        RegisteredFn kktEntity = new RegisteredFn();
        kktEntity.setInn(inn);
        kktEntity.setFn(fn);
        fnRepository.save(kktEntity);

        logger.info("Succesfully added kkt: (inn, kkt): (" + inn + ", " + fn + ") to db");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping("/getFnInfo")
    public RegisteredFn getFnInfo(@RequestParam(value = "fn") String fn) throws TipsServerException {
        boolean correctInput = Utils.validateFn(fn);
        if (!correctInput) {
            throw TipsServerException.invalidArguments();
        }

        if (!fnRepository.existsByFn(fn)) {
            throw new TipsServerException("This fnn was not registered");
        }

        return fnRepository.getByFn(fn);
    }

    @RequestMapping("/registerReceipt")
    public ResponseEntity<Object> registerReceipt(@RequestParam(value = "receiptTime") Integer receiptTime,
                                                  @RequestParam(value = "sum") Long sum,
                                                  @RequestParam(value = "fn") String fn,
                                                  @RequestParam(value = "fd") String fd,
                                                  @RequestParam(value = "fp") String fp,
                                                  @RequestParam(value = "n") String n) throws TipsServerException {
        Long millisecondsFromEpoch = receiptTime * 1000L;
        if (millisecondsFromEpoch > System.currentTimeMillis()) {
            throw new TipsServerException("Is it a receipt from future? We don't like that");
        }

        if (!Utils.validateFn(fn) || !Utils.validateFd(fd) || !Utils.validateFp(fp) || !Utils.validateN(n)) {
            throw TipsServerException.invalidArguments();
        }

        if (!fnRepository.existsByFn(fn)) {
            throw new TipsServerException("Unfortunately this fn (fiscal driver) is not registered in our system");
        }

        Date receiptDate = new Date(receiptTime * 1000L);
        ScannedReceipt receipt = new ScannedReceipt(receiptDate, sum, fn, fd, fp, n);
        receiptRepository.save(receipt);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping("/payTips")
    public ResponseEntity<Object> payTips(@RequestParam(value = "inn") String inn, @RequestParam(value = "amount") Long amount) throws TipsServerException {
        if (!Utils.validateInn(inn)) {
            throw TipsServerException.invalidArguments();
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("Inn is not registered in our system");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getPreferredTipsRate", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Integer> getPreferredTipsRate(@RequestParam(value = "inn") String inn) throws TipsServerException {
        if (!Utils.validateInn(inn)) {
            throw TipsServerException.invalidArguments();
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("Inn is not registered in our system");
        }

        RegisteredInn registeredInn = innRepository.getByInn(inn);
        Integer preferredTips = registeredInn.getPreferredTips();

        return Collections.singletonMap("preferred_tips", preferredTips);
    }

}
