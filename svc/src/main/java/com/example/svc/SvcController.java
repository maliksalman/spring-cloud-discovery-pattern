package com.example.svc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class SvcController {

    private final String myVal;
    private final int instanceId;
    private final RestClient loadBalancedClient;

    public SvcController(@Value("${my.val}") String myVal,
                         @Value("${instance.id:1}")  int instanceId,
                         RestClient.Builder loadBalancedClientBuilder) {
        this.myVal = myVal;
        this.instanceId = instanceId;
        this.loadBalancedClient = loadBalancedClientBuilder.build();
    }

    @GetMapping("/req")
    public ReturnVal getMyVal(@RequestHeader("my-header") String headerVal) {
        return new ReturnVal(myVal, headerVal, instanceId);
    }

    @GetMapping("/lb/{service}")
    public ReturnVal getLoadBalancedClientVal(@PathVariable("service") String serviceName) {
        return loadBalancedClient.get()
                .uri("lb://" + serviceName + "/req")
                .header("my-header", "discovery-client")
                .retrieve()
                .body(ReturnVal.class);
    }
}