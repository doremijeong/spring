package com.spring.tiles;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	/*
	 * sitemesh - filter : request url
	 * tiles - viewResolver : page url
	 * 
	 * 그래서 tiles는 return 되는 url을 읽는다.
	 */
	
	@RequestMapping("/test.do")
	public String test() {
		return "test";
	}
	
	/*
	 * Tiles를 사용(header,left,footer포함)
	 */
	
	@RequestMapping("/testPage.do")
	public String testPage(@RequestParam(defaultValue="true")boolean tiles, 
						   @RequestParam(defaultValue="true")boolean footer) {
		
		String url = "test";
		
		if(tiles) url+=".page";
		
		else if(!footer) url+=".no";
		
		return url;
	}
	
	@RequestMapping("/testNo.do")
	public String testNo() {
		return "test.no";
	}
}
