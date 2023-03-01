package com.rvcabs.microservices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MobileLinksController {

	
    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }
    
    @GetMapping("/securityawareness")
    public String securityAwareness() {
        return "securityawareness";
    }
    
    @GetMapping("/termsandconditions")
    public String uploadCentreCreation() {
        return "termsandconditions";
    }
    
    @GetMapping("/privacypolicy")
    public String privacypolicy() {
        return "privacypolicy";
    }
    
    @GetMapping("/howitworks")
    public String migration() {
        return "howitworks";
    }
    
    @GetMapping("/downloadapp")
    public String downloadapp() {
        return "downloadapp";
    }
    
    
    @GetMapping("/promo")
    public String promoterms() {
        return "promo";
    }
    
    
    @GetMapping("/gift")
    public String gifterms() {
        return "gift";
    }
}
