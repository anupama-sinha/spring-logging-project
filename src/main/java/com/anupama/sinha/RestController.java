package com.anupama.sinha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    Logger log = LoggerFactory.getLogger(SpringLoggingProjectApplication.class);

    @GetMapping("/log")
    String logMessage(){
        log.info("INFO : Hi Anupama, Logging a message here...");
        log.error("ERROR : Hi Anupama, Logging a message here...");
        log.debug("DEBUG : Hi Anupama, Logging a message here...");
        log.trace("TRACE : Hi Anupama, Logging a message here...");
        log.warn("WARN : Hi Anupama, Logging a message here...");
        return "Message logged in Console";
    }
}
