package com.digitalacademy.loan.service;

import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanServiceTest {
    @InjectMocks
    LoanService loanService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanService = new LoanService();
    }

    @DisplayName("Test get loan info by id should return loan information")
    @Test
    void testGetLoanInFoByIdEqual1Test () throws Exception, LoanException {
        //Arrange+Act
        LoanInfoModel resp = loanService.getLoanInfoById(1L);

        //Assert
        assertEquals("1",resp.getId().toString());
        assertEquals("OK",resp.getStatus());
        assertEquals("102-222-2200",resp.getAccountPayable());
        assertEquals("102-222-2200",resp.getAccountReceivable());
        assertEquals(4400000.00,resp.getPrincipalAmount());
    }

    @DisplayName("Test get loan info by id should return loan info not found")
    @Test
    void testGetLoanInFoByIdEqual2Test () throws Exception, LoanException {
        //Arrange
        Long reqParams = 2L;

        //Act
        LoanException thrown = assertThrows(LoanException.class,
                ()-> loanService.getLoanInfoById(reqParams)
        , "Expected LoanInfoById(reqParams) to throws, but it didn't");

        //Assert
        assertEquals(400, thrown.getHttpStatus().value());
        assertEquals("LOAN4002", thrown.getLoanError().getCode());
        assertEquals("Loan information not found", thrown.getLoanError().getMessage());
    }
    @DisplayName("Test get loan info by id should throws Exception: test throws new Exception")
    @Test
    void testGetLoanInFoByIdEqual3Test () throws Exception, LoanException {
        //Arrange
        Long reqParams = 3L;

        //Act
        Exception thrown = assertThrows(Exception.class,
                ()-> loanService.getLoanInfoById(reqParams)
                , "Expected LoanInfoById(reqParams) to throws, but it didn't");

        //Assert
        assertEquals("Test throw exception", thrown.getMessage());
    }
}

