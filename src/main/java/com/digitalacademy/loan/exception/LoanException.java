package com.digitalacademy.loan.exception;

import com.digitalacademy.loan.constant.LoanError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class LoanException extends Throwable {
    private LoanError loanError;
    private HttpStatus httpStatus = HttpStatus.OK;

    public LoanException(LoanError loanError, HttpStatus httpStatus){
        this.loanError = loanError;
        this.httpStatus = httpStatus;
    }
}
