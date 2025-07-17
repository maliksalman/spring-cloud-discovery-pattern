package com.example.svc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SvcController {

    private final String myVal;
    private final int instanceId;

    public SvcController(@Value("${my.val}") String myVal, @Value("${instance.id:1}")  int instanceId) {
        this.myVal = myVal;
        this.instanceId = instanceId;
    }

    @GetMapping("/req")
    public ReturnVal getMyVal(@RequestHeader("my-header") String headerVal) {
        return new ReturnVal(myVal, headerVal, instanceId);
    }

	record ReturnVal(
		String val,
		String header,
        int instanceId
	) { }
}