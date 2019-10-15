package com.hhdl.controller.advice;

import com.hhdl.exception.DataException;
import com.hhdl.util.ResponseModel;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DataExceptionAdvice {
	@ResponseBody
	@ExceptionHandler(DataException.class)
    public ResponseModel handleDataException(DataException ex){
		ResponseModel res=new ResponseModel<>(false,ex.getResponse().getMessage(),null);
        return res;
    } 
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseModel paramsBindException(MissingServletRequestParameterException ex){
		ResponseModel res=new ResponseModel<>(false,ex.getMessage(),null);
        return res;
    } 
	
}
