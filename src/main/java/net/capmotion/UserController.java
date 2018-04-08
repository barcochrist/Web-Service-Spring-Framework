package net.capmotion;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@RequestBody User resource) {
		userRepository.save(resource);
		return "User created";
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public String update(@RequestBody User resource) {
		Optional<User> userOpt = userRepository.findById(resource.getId());
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setFirstName(resource.getFirstName());
			user.setLastName(resource.getLastName());
			user.setUserName(resource.getUserName());
			user.setEmail(resource.getEmail());
			user.setPhoneNumber(resource.getPhoneNumber());
			userRepository.save(user);
			return "User [" + resource.getId() + "]  updated";
		} else {
			return "User not found";
		}
	}

	@RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String deleteUser(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
		return "User [" + id + "] deleted";
	}
}
