package com.yash.demo.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Test {
	@Value("${a}")
	public static String a;

}
