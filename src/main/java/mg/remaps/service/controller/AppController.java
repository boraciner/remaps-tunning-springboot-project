package mg.remaps.service.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.client.RestTemplate;

import mg.remaps.service.entity.Authority;
import mg.remaps.service.entity.CarRestResponse;
import mg.remaps.service.entity.MGUserDetails;
import mg.remaps.service.entity.User;
import mg.remaps.service.repository.MGUserRepository;
import mg.remaps.service.repository.UserRepository;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Controller
public class AppController {

	final String apiKey = "43d27aa3752be4e1512e474febf3f661";
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MGUserRepository mgUserRepository;

	@Autowired
	UserDetailsService userService;

	@Autowired
	RestTemplate restTemplate;

	
	Logger logger = LoggerFactory.getLogger(AppController.class);

	@PostMapping("/page_register_select_brand")
	public String processPageRegister(@ModelAttribute(value = "mguserdetails") MGUserDetails mguser, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		

		ResponseEntity<List<CarRestResponse>> brandResponse = restTemplate.exchange(
				"http://api.carecusoft.com/tr/v1/tuning/brands?key=" + apiKey, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CarRestResponse>>() {
				});
		List<CarRestResponse> brands = brandResponse.getBody();

		
		
		model.addAttribute("mguserdetails", mguser);
		model.addAttribute("brands", brands);
		return "page-register-select-brand";
	}

	
	@PostMapping("/page_register_select_model")
	public String processPageCarModel(@ModelAttribute(value = "mguserdetails") MGUserDetails mguser, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		
		ResponseEntity<List<CarRestResponse>> modelResponse = restTemplate.exchange(
				"https://api.carecusoft.com/tr/v1/tuning/models/"+mguser.getCarBrand()+"?key=" + apiKey, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CarRestResponse>>() {
				});
		List<CarRestResponse> models = modelResponse.getBody();
		
		
		model.addAttribute("mguserdetails", mguser);
		model.addAttribute("models", models);
		return "page-register-select-model";
	}
	
	
	
	@PostMapping("/page_register_select_year")
	public String processPageCarYear(@ModelAttribute(value = "mguserdetails") MGUserDetails mguser, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<List<CarRestResponse>> modelResponse = restTemplate.exchange(
				"https://api.carecusoft.com/tr/v1/tuning/years/"+mguser.getCarModel()+"?key=" + apiKey, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CarRestResponse>>() {
				});
		List<CarRestResponse> years = modelResponse.getBody();

		model.addAttribute("mguserdetails", mguser);
		model.addAttribute("years", years);
		return "page-register-select-year";
	}
	

	@PostMapping("/page_register_select_engine")
	public String processPageCarEngine(@ModelAttribute(value = "mguserdetails") MGUserDetails mguser, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		ResponseEntity<List<CarRestResponse>> modelResponse = restTemplate.exchange(
				"https://api.carecusoft.com/tr/v1/tuning/engines/"+mguser.getCarYear()+"?key=" + apiKey, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CarRestResponse>>() {
				});
		List<CarRestResponse> engines = modelResponse.getBody();

		
		
		
		model.addAttribute("mguserdetails", mguser);
		model.addAttribute("engines", engines);
		return "page-register-select-engine";
	}
	
	@PostMapping("/page-register")
	public String processForm(@ModelAttribute(value = "mguserdetails") MGUserDetails mguser, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			userService.loadUserByUsername(mguser.getUsername());
		} catch (UsernameNotFoundException e) {
			User user = new User();
			user.setUsername(mguser.getUsername());
			user.setPassword(bCryptPasswordEncoder.encode(mguser.getPassword()));

			Authority auth = new Authority();
			auth.setAuthority("ROLE_USER");
			user.setEnabled(true);
			user.setAuthority(new HashSet<Authority>(Arrays.asList(auth)));

			userRepository.save(new User(user.getUsername(), user.getPassword(), true,
					new HashSet<Authority>(Arrays.asList(auth))));
			mgUserRepository.save(mguser);

			UserDetails registeredUser = userService.loadUserByUsername(user.getUsername());
			Authentication authority = new UsernamePasswordAuthenticationToken(registeredUser, null,
					registeredUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authority);
			model.addAttribute("username", user.getUsername());
		}
		return "index";
	}

	@GetMapping("/page-register")
	public String showForm(Model model) {
		MGUserDetails mgUser = new MGUserDetails();

		model.addAttribute("mguserdetails", mgUser);

		return "page-register";
	}

	@GetMapping("/")
	public String showMainPage(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			String name = auth.getName();
			model.addAttribute("username", name);
		}

		User user = new User();
		model.addAttribute("user", user);

		return "index";
	}

	
	
	
	
	@GetMapping("/page-about-us")
	public String showAboutUsPage(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			String name = auth.getName();
			model.addAttribute("username", name);
		}

		User user = new User();
		model.addAttribute("user", user);

		return "page-about-us";
	}
	
}
