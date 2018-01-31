package com.supconit.jobschedule.mock;

public class HelloJob {
	public String sayHello(String name) {
		System.out.println("*************************************"+("Hello " + name));
		return "Hello " + name;
	}
}
