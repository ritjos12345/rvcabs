
   package com.rvcabs.microservices;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class RVCabsApplication {

	private static String cacheHost;
	private static int cachePort;

	@Value("${cache.host:localhost}")
	public void setCacheHost(String propertyValue) {
		cacheHost = propertyValue;
	}

	@Value("${cache.port:10334}")
	public void setCachePort(int propertyValue) {
		cachePort = propertyValue;
	}

	private static ClientCache cache = null;
			//start locator --name=locator1 --bind-address=127.0.0.1 --port=10334 --http-service-port=0 --classpath=/home/ganapathy/softwares/geode/test.jar
	//start server --name=server1 --locators=192.168.0.23:127.0.0.1[10334] --classpath=/home/ganapathy/softwares/geode/test.jar --J=-Dgemfire.QueryService.allowUntrustedMethodInvocation=true

	private static HashMap<String,PrivateKey> privateKeys = new HashMap<>();

	


	public static String keystorePath;

	public static String pfxPath;

	public static String keystorePasswrd;

	public static String pfxPassword;

	public static String HMAC_VALUE;

	public static List<String> hmacApisList;

    @Value("${hmac.exclude-hmac}")
    public void setHmacEnableApi(String propertyValue) {
        String[] hmacApiArray = propertyValue.split(",");
        hmacApisList = new ArrayList<>(hmacApiArray.length);
        Collections.addAll(hmacApisList, hmacApiArray);
    }

	

	public static void main(String[] args) {
		SpringApplication.run(RVCabsApplication.class, args);
	}
	


}

