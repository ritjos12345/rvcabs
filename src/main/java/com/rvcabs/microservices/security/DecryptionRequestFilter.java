package com.rvcabs.microservices.security;

public class DecryptionRequestFilter {//extends OncePerRequestFilter {
	/*
	 * 
	 * @Value("${hmac.value:secret}") private String hmacSecret;
	 * 
	 * @Value("${hmac.enabled:true}") private boolean hmacEnabled;
	 * 
	 * @Override protected void doFilterInternal(HttpServletRequest request,
	 * HttpServletResponse response, FilterChain filterChain) throws
	 * ServletException, IOException {
	 * 
	 * List<String> hmacApisList = AdminApplication.hmacApisList;
	 * 
	 * if (hmacEnabled && !hmacApisList.contains(request.getServletPath())) { String
	 * data = request.getReader().lines().collect(Collectors.joining()); if
	 * ((HttpMethod.GET.matches(request.getMethod())) ||
	 * (HttpMethod.POST.matches(request.getMethod()))) {
	 * 
	 * final String keySentFromRequest = request.getHeader(Constants.HMAC_PARAM); if
	 * (HttpMethod.GET.matches(request.getMethod())) { //we will check the path
	 * value for encryption in case of get data = request.getServletPath();
	 * 
	 * if (!request.getParameterMap().isEmpty()) { //preparing the parameter append
	 * case for get path StringBuilder sb = new StringBuilder("?"); final
	 * Set<Map.Entry<String, String[]>> entries =
	 * request.getParameterMap().entrySet(); for (Map.Entry<String, String[]> entry
	 * : entries) {
	 * sb.append(entry.getKey()).append("=").append(entry.getValue()[0]).append("&")
	 * ; } if (sb.length() > 2) { sb.replace(sb.length() - 1, sb.length(), ""); }
	 * data = data.concat(sb.toString());
	 * 
	 * }
	 * 
	 * } logRequestHeaders(request); logger.info("Request " + Constants.HMAC_PARAM +
	 * ": " + keySentFromRequest); final String appGenerated =
	 * Utilities.generateHashKey(data, hmacSecret);
	 * logger.info("generateHashKey Data : " + data + ", hmacSecret: " +
	 * hmacSecret); logger.info("App Generated " + Constants.HMAC_PARAM + ": " +
	 * appGenerated); if (keySentFromRequest == null ||
	 * !keySentFromRequest.equalsIgnoreCase(appGenerated)) {
	 * logger.warn("Hmac Keywords not matching for the request".concat(data)); throw
	 * new UnAuthorizedException("You Are Not Authorized"); }
	 * 
	 * } if (!(request instanceof HttpServletRequest) || !(response instanceof
	 * HttpServletResponse)) { throw new
	 * ServletException("DecryptionRequestFilter just supports HTTP requests"); } }
	 * filterChain.doFilter(request, response); }
	 * 
	 * private void logRequestHeaders(HttpServletRequest request) {
	 * 
	 * Enumeration headerNames = request.getHeaderNames(); while
	 * (headerNames.hasMoreElements()) { String key = (String)
	 * headerNames.nextElement(); String value = request.getHeader(key);
	 * logger.info("Request Headres*********************\nKEY: "+key+"\nVALUE:"+
	 * value+"\n"); } }
	 * 
	 * 
	 * 
	 */}
