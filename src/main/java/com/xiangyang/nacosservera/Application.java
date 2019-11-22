package com.xiangyang.nacosservera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@EnableNacosDiscovery
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
//	@RestController
//	class EchoController {
//		@RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
//		public String echo(@PathVariable String string) {
//			return "Hello Nacos Discovery " + string;
//		}
//	}
}
