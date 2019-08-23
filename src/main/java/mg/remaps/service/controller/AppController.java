package mg.remaps.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	@GetMapping("/")
	public String menu() {
		return "index";
	}
	
	@GetMapping("/contact-1")
	public String menuindex() {
		return "contact-1";
	}
	
	@GetMapping("/page-login")
	public String pagehogin() {
		return "page-login";
	}
}
