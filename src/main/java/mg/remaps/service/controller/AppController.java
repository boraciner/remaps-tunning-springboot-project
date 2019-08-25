package mg.remaps.service.controller;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import mg.remaps.service.entity.Authority;
import mg.remaps.service.entity.User;
import mg.remaps.service.repository.UserRepository;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
@Controller
public class AppController {
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsService userService;
    
    Logger logger = LoggerFactory.getLogger(AppController.class);

	//$2a$04$NSbpKI/QYQ/5l4tf/lg8yeVaHs8.N0Q9njOpACLuyuTv.jPYiVaSe
	@PostMapping("/page-register")
	public String processForm(@ModelAttribute(value="user") User user, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("gelen deger : " + user.getPassword());
		System.out.println("gelen deger : " + user.getUsername());
		
		try{
			userService.loadUserByUsername(user.getUsername());
		}catch(UsernameNotFoundException e) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			Authority auth = new Authority();
			auth.setAuthority("ROLE_USER");
			user.setEnabled(true);
			user.setAuthority(new HashSet<Authority>(Arrays.asList(auth)));
			userRepository.save(new User(user.getUsername(), user.getPassword(), true, new HashSet<Authority>(Arrays.asList(auth))));


			
			UserDetails registeredUser = userService.loadUserByUsername(user.getUsername());
	        Authentication authority = new UsernamePasswordAuthenticationToken(registeredUser, null, registeredUser.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authority);
	        model.addAttribute("username", user.getUsername());
	        
	        
		}
		
		
		return "index";
	}
	@GetMapping("/page-register")
	public String showForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		
		return "page-register";
	}
	
	
	@GetMapping("/")
	public String showMainPage(ModelMap model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    
		if(auth != null) {
			String name = auth.getName();
			model.addAttribute("username", name);
		}
		
		
		User user = new User();
		model.addAttribute("user", user);
		
		return "index";
	}
	
	
	
	
}
