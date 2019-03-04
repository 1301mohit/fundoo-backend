package com.bridgelabz.fundoo.user.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.util.EmailUtil;
import com.bridgelabz.fundoo.util.UserToken;
import com.bridgelabz.fundoo.util.Utility;

@Service
@PropertySource("classpath:message.properties")
public class UserServicesImpl implements UserServices {
	
	@Autowired
	public Environment environment;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	 
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Response response;
	
	//Response response =new Response();

	
//	@Autowired
//	private Response response;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws MessagingException 
	 * @throws Exception
	 */
	@Override
	public Response register(UserDTO userDTO) throws MessagingException, Exception //throws Exception 
	{	
		Optional<User> useravailable = userRepository.findByEmail(userDTO.getEmail());
		//Response response =new Response();
		//To check user is available or not
		if(useravailable.isPresent())
		{
			throw new UserException("Dublicate user found");
		}
		
		//Copy user data userDTO to user class
		User user=modelMapper.map(userDTO, User.class);
		
		//set password in encrypted form
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user.setRegisteredDate(LocalDate.now());
		
		//save user data in data base
	    user = userRepository.save(user);
	    
	    EmailUtil.send(user.getEmail(), "mail for Registration", getUrl(user.getuserId()));
	    response.setStatusCode(100);
	    response.setStatusMessage(environment.getProperty("1"));
	    return response;
	}
	
	@Override
	public Response login(LoginDTO loginuser) throws Exception
	{
		Optional<User> userAvailable = userRepository.findByEmail(loginuser.getEmail());
		System.out.println("database password"+userAvailable.get().getPassword());
		System.out.println("login user password"+passwordEncoder.encode(loginuser.getPassword()));
		if(userAvailable.isPresent() && passwordEncoder.matches(loginuser.getPassword(),userAvailable.get().getPassword()) && userAvailable.get().isIsverification()) 
		{ 
			String generateToken = UserToken.generateToken(userAvailable.get().getuserId());
			response.setStatusCode(3);
			response.setStatusMessage(environment.getProperty("2"));
			response.setToken(generateToken);
			return response; 
		} 
		else 
		{ 
			throw new UserException("Email and Password is not found"); 
		}
	}

	@Override
	public String validateEmailId(String token) throws Exception
	{
		Long id = UserToken.tokenVerify(token);
		User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found")); 
		user.setIsverification(true);
		userRepository.save(user);
		return "Successfully verified";
	}
	
	
	public String getUrl(Long id) throws Exception
	{
		return "192.168.0.84:8080/user/"+UserToken.generateToken(id);
	}
	
	@Override
	public Response forgotPassword(String email) throws MessagingException, Exception  
	{
		Optional<User> userAvailable = userRepository.findByEmail(email);
		if(userAvailable.isPresent()) 
		{
			User user = userAvailable.get();
			EmailUtil.send(email, "Password Reset", Utility.getBody(user, "user"));
			response.setStatusCode(300);
			response.setStatusMessage(environment.getProperty("3"));
			return response;
		}
		else 
		{
			throw new UserException("Email not found");
		}
	}
	
	@Override
	public Response resetPassword(String token, String password) throws Exception
	{
			long userId = UserToken.tokenVerify(token);
			System.out.println(userId);
			User user = userRepository.findById(userId).get();
			System.out.println(user.getPassword());
			user.setPassword(passwordEncoder.encode(password));
			System.out.println(user.getPassword());
			user.setAccountUpdateDate(LocalDate.now());
			userRepository.save(user);
			response.setStatusCode(400);
			response.setStatusMessage(environment.getProperty("4"));
			return response;
	}
}












//	@Override
//	public User resetPassword(String token) throws Exception {
//		Long id = UserToken.tokenVerify(token);
//		return userRepository.findById(id).get();
//	}
//	
//	public String getBody(User user, String link) throws Exception {
//		return "192.168.0.84:8080/"+link+UserToken.generateToken(user.getId());
//	}

