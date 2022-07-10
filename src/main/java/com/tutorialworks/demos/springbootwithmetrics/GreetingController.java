package com.tutorialworks.demos.springbootwithmetrics;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

import org.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;


//@Service
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    private static final String usertemplate = "Welcome, %s!";
    private final AtomicLong userCounter = new AtomicLong();

    private final MeterRegistry registry;

    /**
     * We inject the MeterRegistry into this class
     */
    public GreetingController(MeterRegistry registry) {
        this.registry = registry;
    }

    /**
     * The @Timed annotation adds timing support, so we can see how long
     * it takes to execute in Prometheus
     * percentiles
     */
    
    @GetMapping("/")
    public String fun() {
    	return "Welcome";
    }
    
    
//    @Scheduled(fixedRate=500)
    @GetMapping("/greeting")
    @Timed(value = "greeting.time",
    	description = "Time taken to return greeting",
//    	histogram=true,
    	percentiles = {0.5, 0.90})
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    	registry.counter("greeting.total","name",name).increment();  // Custom metrics
    	
    	String path="/greeting";
    	
    	JSONObject obj=new JSONObject();
    	Greeting greet=new Greeting(counter.incrementAndGet(),String.format(template, name));
		obj.put("greet",greet.getContent());
		obj.put("id",greet.getId());
		LocalDate date=LocalDate.now();
		obj.put("Date", date);
		obj.put("Time", LocalTime.now());
//		System.out.print(obj);
		obj.put("path", path);
		return obj.toString();
    }
    
    @GetMapping("/user")
    @Timed(value="userAdded.time")
    public String addUser(@RequestParam(value="name") String name) {
    	registry.gauge("Num of User", userCounter);
    	
    	String path="/user";
    	
    	JSONObject obj=new JSONObject();
		User user=new User(userCounter.incrementAndGet(),String.format(usertemplate, name));
		obj.put("greet",user.getName());
		obj.put("id",user.getId());
		LocalDate date=LocalDate.now();
		obj.put("Date", date);
		obj.put("Time", LocalTime.now());
		obj.put("path", path);
//		System.out.print(obj);
		
		return obj.toString();
    }

}
