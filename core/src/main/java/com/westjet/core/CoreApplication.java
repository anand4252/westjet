package com.westjet.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ComponentScan("com.westjet")
@Service
public class CoreApplication {

	public double generateRandom(){
		final double random = Math.random();
		return random;

	}

}
