package com.digitalacademy.loan.controller;

import com.digitalacademy.loan.constant.LoanError;
import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import com.digitalacademy.loan.service.LoanService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {
    @Mock
    LoanService loanService;

    @InjectMocks
    LoanController loanController;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        LoanController loanController = new LoanController(loanService);
        mvc = MockMvcBuilders.standaloneSetup(loanController)
                .build();
    }

    @DisplayName("Test get loan info by id equals 1 should return loan information")
    @Test
    void testGetLoanInFoByIdEqual1Test () throws Exception, LoanException {
        //Arrange
        Long reqParams = 1L;
        LoanInfoModel loanInfoModel = new LoanInfoModel();
        loanInfoModel.setId(1L);
        loanInfoModel.setStatus("OK");
        loanInfoModel.setAccountPayable("102-222-2200");
        loanInfoModel.setAccountReceivable("102-222-2200");
        loanInfoModel.setPrincipalAmount(4400000.00);

        when(loanService.getLoanInfoById(reqParams)).thenReturn(loanInfoModel);

        MvcResult mvcResult = mvc.perform(get("/loan/info/"+reqParams))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //Act
        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));
        JSONObject data = new JSONObject(resp.getString("data"));

        //Assert
        assertEquals("0", status.get("code").toString());
        assertEquals("success", status.get("message").toString());
        assertEquals(1, data.get("id"));
        assertEquals("OK", data.get("status"));
        assertEquals("102-222-2200", data.get("account_payable"));
        assertEquals("102-222-2200", data.get("account_receivable"));
        assertEquals(4400000, data.get("principal_amount"));

        verify(loanService, times(1)).getLoanInfoById(reqParams);

    }

    @DisplayName("Test get loan info by id equals 2 should return loan info not found")
    @Test
    void testGetLoanInFoByIdEqual2Test () throws Exception, LoanException {
        //Arrange
        Long reqParams = 2L;

        when(loanService.getLoanInfoById(reqParams)).thenThrow(
                new LoanException(LoanError.GET_LOAN_NOT_FOUND, HttpStatus.BAD_REQUEST));

        MvcResult mvcResult = mvc.perform(get("/loan/info/"+reqParams))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //Act
        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));

        //Assert
        assertEquals("LOAN4002", status.get("code").toString());
        assertEquals("Loan information not found", status.get("message").toString());
    }

    @DisplayName("Test get loan info by id equals 3 should throws Exception: test throws new Exception")
    @Test
    void testGetLoanInFoByIdEqual3Test () throws Exception, LoanException {
        //Arrange
        Long reqParams = 3L;

        when(loanService.getLoanInfoById(reqParams)).thenThrow(
                new Exception("Test throw exception")
        );

        MvcResult mvcResult = mvc.perform(get("/loan/info/"+reqParams))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //Act
        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));

        //Assert
        assertEquals("LOAN4001", status.get("code").toString());
        assertEquals("Can not get loan information", status.get("message").toString());

    }

}
