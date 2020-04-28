package com.company.project.configurer;

import com.company.project.core.Result;
import com.company.project.core.ResultCode;
import com.company.project.core.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdvisor {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result handleException(HttpServletRequest request, Exception e) {
		Result result = new Result();
		if (e instanceof ServiceException) {//业务失败的异常，如“账号或密码错误”
			result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
			logger.info(e.getMessage());
		} else if (e instanceof NoHandlerFoundException) {
			result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
		} else {
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage());
		}
		return result;
	}
}
