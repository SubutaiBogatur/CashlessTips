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
import webserver.controllers.error_handling.TipsServerException;
import webserver.controllers.exposed_models.ExposedFeedback;
import webserver.controllers.exposed_models.ExposedRegisteredFn;
import webserver.controllers.exposed_models.ExposedRegisteredInn;
import webserver.controllers.exposed_models.ExposedWaiter;
import webserver.dbs.*;
import webserver.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private InnRepository innRepository;
    @Autowired
    private FnRepository fnRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private WaiterRepository waiterRepository;
    @Autowired
    private TipsRepository tipsRepository;

    private Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping("/setInnInfo")
    public ResponseEntity<Object> setInnInfo(@RequestParam(value = "inn") String inn,
                                             @RequestParam(value = "preferredTips", defaultValue = "10") Integer preferredTips,
                                             @RequestParam(value = "cardNumber", required = false) String cardNumber) throws TipsServerException {
        if (!Utils.validateInn(inn) || !Utils.validateCardNumber(cardNumber)) {
            throw TipsServerException.invalidArguments();
        }

        if (preferredTips < 0 || preferredTips > 100) {
            throw new TipsServerException("Invalid percentage for preferred tips");
        }

        if (innRepository.existsByInn(inn)) {
            // firstly delete old record
            RegisteredInn innEntity = innRepository.getByInn(inn);
            innRepository.deleteById(innEntity.getId());
        }

        // registering new
        RegisteredInn innEntity = new RegisteredInn(inn, preferredTips, cardNumber);
        innRepository.save(innEntity);

        logger.info("Succesfully added inn " + inn + " to db");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping("/getInnInfo")
    public ExposedRegisteredInn getInnInfo(@RequestParam(value = "inn") String inn) throws TipsServerException {
        if (!Utils.validateFn(inn)) {
            throw TipsServerException.invalidArguments();
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("This inn was not registered");
        }

        RegisteredInn registeredInn = innRepository.getByInn(inn);
        if (registeredInn == null) {
            throw new TipsServerException("Unknown internal server error occured");
        }

        return registeredInn.convertToExposed();
    }

    @RequestMapping("/setFnInfo")
    public ResponseEntity<Object> setFnInfo(@RequestParam(value = "inn") String inn, @RequestParam(value = "fn") String fn) throws TipsServerException {
        if (!Utils.validateInn(inn) || !Utils.validateFn(fn)) {
            throw TipsServerException.invalidArguments();
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("Such inn was not registered");
        }

        if (fnRepository.existsByFn(fn)) {
            // editing:
            RegisteredFn fnEntity = fnRepository.getByFn(fn);
            fnRepository.deleteById(fnEntity.getId());
        }

        RegisteredFn fnEntity = new RegisteredFn();
        fnEntity.setInn(inn);
        fnEntity.setFn(fn);
        fnRepository.save(fnEntity);

        logger.info("Succesfully added fn: (inn, fn): (" + inn + ", " + fn + ") to db");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping("/getFnInfo")
    public ExposedRegisteredFn getFnInfo(@RequestParam(value = "fn") String fn) throws TipsServerException {
        boolean correctInput = Utils.validateFn(fn);
        if (!correctInput) {
            throw TipsServerException.invalidArguments();
        }

        if (!fnRepository.existsByFn(fn)) {
            throw new TipsServerException("This fnn was not registered");
        }

        RegisteredFn registeredFn = fnRepository.getByFn(fn);
        if (registeredFn == null) {
            throw new TipsServerException("Unknown internal server error occured");
        }

        return registeredFn.convertToExposed();
    }

    @RequestMapping("/listFnByInn")
    public List<ExposedRegisteredFn> listFnByInn(@RequestParam(value = "inn") String inn) throws TipsServerException {
        boolean correctInput = Utils.validateInn(inn);
        if (!correctInput) {
            throw TipsServerException.invalidArguments();
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("This inn was not registered");
        }

        List<RegisteredFn> registeredFns = fnRepository.getAllByInn(inn);
        if (registeredFns == null) {
            throw new TipsServerException("Unknown null server error occurred");
        }

        return registeredFns.stream().map(RegisteredFn::convertToExposed).collect(Collectors.toList());
    }

    @RequestMapping("/setWaiter")
    public ResponseEntity<Object> setWaiter(@RequestParam(value = "inn") String inn,
                                            @RequestParam(value = "name") String name,
                                            @RequestParam(value = "cardNumber", required = false) String cardNumber) throws TipsServerException {
        if (!Utils.validateInn(inn) || !Utils.validateCardNumber(cardNumber)) {
            throw TipsServerException.invalidArguments();
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("Such inn was not registered");
        }

        if (waiterRepository.existsByInnAndName(inn, name)) {
            // editing:
            RegisteredWaiter waiterEntity = waiterRepository.getByInnAndName(inn, name);
            waiterRepository.deleteById(waiterEntity.getId());
        }

        RegisteredWaiter waiterEntity = new RegisteredWaiter(inn, name, cardNumber);
        waiterRepository.save(waiterEntity);

        logger.info("Succesfully added waiter: (inn, name, cardnumber): (" + inn + ", " + name + ", " + cardNumber + ") to db");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping("/listWaitersByInn")
    public List<ExposedWaiter> listWaitersByInn(@RequestParam(value = "inn") String inn) throws TipsServerException {
        if (!Utils.validateInn(inn)) {
            throw TipsServerException.invalidArguments();
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("This inn was not registered");
        }

        List<RegisteredWaiter> registeredWaiters = waiterRepository.getAllByInn(inn);
        if (registeredWaiters == null) {
            throw new TipsServerException("Unknown null server error occurred");
        }

        return registeredWaiters.stream().map(RegisteredWaiter::convertToExposed).collect(Collectors.toList());
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
    public ResponseEntity<Object> payTips(@RequestParam(value = "inn") String inn,
                                          @RequestParam(value = "amount") Long amount,
                                          @RequestParam(value = "rate", required = false) Integer rate,
                                          @RequestParam(value = "comment", required = false) String comment,
                                          @RequestParam(value = "waiter_id", required = false) Integer waiter_id) throws TipsServerException {
        if (!Utils.validateInn(inn) || rate != null && !Utils.validateRate(rate)) {
            throw TipsServerException.invalidArguments();
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("Inn is not registered in our system");
        }

        PaidTips tipsEntity = new PaidTips(inn, amount, waiter_id, rate, comment);
        tipsRepository.save(tipsEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/listFeedbackByInn")
    public List<ExposedFeedback> listFeedbackByInn(@RequestParam(value = "inn") String inn) throws TipsServerException {
        if (!Utils.validateInn(inn)) {
            throw TipsServerException.invalidArguments();
        }

        if (!innRepository.existsByInn(inn)) {
            throw new TipsServerException("This inn was not registered");
        }

        List<PaidTips> paidTips = tipsRepository.getAllByInn(inn);
        if (paidTips == null) {
            throw new TipsServerException("Unknown null server error occurred");
        }

        return paidTips.stream().map(PaidTips::convertToExposedFeedback).collect(Collectors.toList());
    }

}
