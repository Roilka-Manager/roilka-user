package com.roilka.www.api.controller.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roilka.www.utils.redis.RedisUtils;

@RestController
public class HelloController {

	@Autowired
	private RedisTemplate redisTemplate; 
	
	
	@RequestMapping("/")
	public String index() {
		return "Hello world!";
	}
	@RequestMapping("/set/{name}/{num}")
	public String set(@PathVariable("name") String name,@PathVariable("num") String num) {
		/*RedisUtils redisUtils = new RedisUtils();
		redisUtils.set("test", num);*/
		redisTemplate.opsForValue().set(name, num);
		return "Hello world!";
	}
	
	@RequestMapping("/get/{name}")
	public Object get(@PathVariable("name") String name) {
//		RedisUtils redisUtils = new RedisUtils();
		return redisTemplate.opsForValue().get(name);
	}
}
