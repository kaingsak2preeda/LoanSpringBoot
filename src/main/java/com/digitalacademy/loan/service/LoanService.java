package com.digitalacademy.loan.service;

import com.digitalacademy.loan.constant.LoanError;
import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private final Logger log = LogManager.getLogger(LoanService.class.getName());

    public LoanInfoModel getLoanInfoById(Long id) throws Exception, LoanException {
        log.info("Get loan info by id: {}", id);
        LoanInfoModel loanInfoModel = new LoanInfoModel();
        if(id.equals(1L)){
            loanInfoModel.setId(1L);
            loanInfoModel.setStatus("OK");
            loanInfoModel.setAccountPayable("102-222-2200");
            loanInfoModel.setAccountReceivable("102-222-2200");
            loanInfoModel.setPrincipalAmount(4400000.00);
        }else if(id.equals(2L)){
            log.info("id: {}", id);
            throw new LoanException(
                    LoanError.GET_LOAN_NOT_FOUND,
                    HttpStatus.BAD_REQUEST
            );
        }else{
            log.error("id: {}", id);
            throw new Exception("Test throw exception");
        }
        return loanInfoModel;
    }
}
