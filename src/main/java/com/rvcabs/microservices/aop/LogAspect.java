package com.rvcabs.microservices.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvcabs.microservices.entity.ApplicationUserEntity;
import com.rvcabs.microservices.exception.UnAuthorizedException;
import com.rvcabs.microservices.repository.ApplicationUserRepository;
import com.rvcabs.microservices.security.HttpServletRequestWritableWrapper;
import com.rvcabs.microservices.service.ApplicationService;
import com.rvcabs.microservices.utilities.Utilities;

@Aspect
@Component
public class LogAspect {
	@Autowired
	ApplicationService applicationService;

	@Autowired
	ApplicationUserRepository applicationUserRepository;

	/*@Autowired
	CustomerRepository customerRepository;*/

	@Around("@annotation(logExecution)")
	public Object LogExecution(ProceedingJoinPoint joinPoint, LogExecution logExecution) throws Throwable {

		// Intercepts called class and method name
		final String className = joinPoint.getSignature().getDeclaringTypeName();
		final String methodName = joinPoint.getSignature().getName();

		Object result = null;
		LoggerFactory.getLogger(className).info("Will execute method {}.", methodName);
		final StopWatch stopWatch = new StopWatch();

		try {
			// Starts timer
			stopWatch.start();

			result = joinPoint.proceed();

			// Stops timer
			stopWatch.stop();

			LoggerFactory.getLogger(className).info("Method {} executed within {} miliseconds.",
					methodName, stopWatch.getTotalTimeMillis());
		} catch (Exception ex) {
			if (logExecution.logException()) {
				LoggerFactory.getLogger(className).error("Exception was raised while trying to execute method " + methodName, ex);
			}
			// Throws original exception regardless anything
			throw ex;
		}

		return result;
	}

	@Around("@annotation(logRequest)")
	public Object LogRequest(ProceedingJoinPoint joinPoint, LogRequest logRequest) throws Throwable {

		// Intercepts called class and method name
		final String className = joinPoint.getSignature().getDeclaringTypeName();
		final Logger logger = LoggerFactory.getLogger(className);

		// Intercepts HTTP/HTTPS request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		// Logs info	
		logger.info("[{}][{}][{}][{}][{}]",
				request.getHeader("X-Request-ID"), request.getRemoteHost(), request.getHeader("X-Forwarded-For"),
				request.getHeader("X-Forwarded-Host"), request.getHeader("X-Forwarded-Proto"));
		// Allows called method to execute and return it's result, if any

		return joinPoint.proceed();
	}

	@Around("@annotation(headerRequest)")
	public Object headerRequest(ProceedingJoinPoint joinPoint, HeaderRequest headerRequest) throws Throwable {

		final String updatedBy = "updatedBy";
		final String createdBy = "createdBy";
		// Intercepts HTTP/HTTPS request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		if(request instanceof HttpServletRequestWritableWrapper){
			HttpServletRequestWritableWrapper wrapper=(HttpServletRequestWritableWrapper)request;
			String body=wrapper.getBody();
			actualObj = mapper.readTree(body);
		}else{
			ContentCachingRequestWrapper contentCachingRequestWrapper = ((ContentCachingRequestWrapper) request);
			byte[] contentAsByteArray = contentCachingRequestWrapper.getContentAsByteArray();
			String bodyContent = new String(contentAsByteArray);
			actualObj = mapper.readTree(bodyContent);
		}

		String clientIdHeader = request.getHeader("clientId");
		String clientSecretHeader = request.getHeader("clientSecret");


		if (actualObj.get(updatedBy) == null && actualObj.get(createdBy) == null) {
			throw new UnAuthorizedException("invlaid updatedBy or createdBy");
		}
		if (Utilities.isEmpty(clientIdHeader) || Utilities.isEmpty(clientSecretHeader)) {
			throw new UnAuthorizedException("invlaid clientId or clientSecret");
		}
		JsonNode accountIdReq = actualObj.get(updatedBy) == null ? actualObj.get(createdBy) : actualObj.get(updatedBy);

		ApplicationUserEntity applicationUserEntity = applicationUserRepository.findByAccountId(accountIdReq.textValue());

		if (applicationUserEntity == null) {
			throw new UnAuthorizedException();
		}

		final JsonNode customerId = actualObj.get("customerId");
	/*	if (!(applicationUserEntity.getUserType().equalsIgnoreCase("SUPER_ADMIN"))
				&& customerId != null
				&& !customerRepository.existsByCustomerId(customerId.textValue())) {

					throw new UnAuthorizedException("Invalid customerId");

		}
		if (!customerRepository.existsByClientIdAndClientSecret(clientIdHeader, clientSecretHeader)) {
			throw new UnAuthorizedException();
		}*/

		return joinPoint.proceed();
	}
}