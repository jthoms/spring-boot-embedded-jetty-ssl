package sample.jetty.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sample.jetty.service.HelloWorldService;

@Controller
public class SampleController {

	@Autowired
	private HelloWorldService helloWorldService;

	@RequestMapping("/home")
	@ResponseBody
	public String helloWorld() {
		return this.helloWorldService.getHelloMessage();
	}
}
