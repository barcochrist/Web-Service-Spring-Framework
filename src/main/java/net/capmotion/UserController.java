package net.capmotion;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "/users")
	public @ResponseBody Iterable<User> getAllUsers() {
		logger.info("getting all users");
		return userRepository.findAll();
	}

	@GetMapping(path = "/add")
	public @ResponseBody String addUser(@RequestParam(value = "firstName", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "password", required = true) String password) {

		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(userName);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setPassword(password);
		userRepository.save(user);
		return "User created";
	}

	@GetMapping(path = "/update")
	public @ResponseBody String updateUser(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "firstName", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber) {

		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUserName(userName);
			user.setEmail(email);
			user.setPhoneNumber(phoneNumber);
			userRepository.save(user);
			return "User [" + id + "] updated";
		} else {
			return "User [" + id + "] not found";
		}
	}

	@GetMapping(path = "/delete/{id}")
	public @ResponseBody String deleteUser(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
		return "User [" + id + "] deleted";
	}
}
