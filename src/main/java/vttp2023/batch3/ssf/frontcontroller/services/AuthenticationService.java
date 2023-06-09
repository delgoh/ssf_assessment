package vttp2023.batch3.ssf.frontcontroller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonObject;
import vttp2023.batch3.ssf.frontcontroller.models.Login;
import vttp2023.batch3.ssf.frontcontroller.respositories.AuthenticationRepository;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationRepository authenticationRepository;

	private String AUTHENTICATE_URL = "https://authservice-production-e8b2.up.railway.app";

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public void authenticate(String username, String password) throws Exception {
		String requestUrl = UriComponentsBuilder
			.fromUriString(AUTHENTICATE_URL)
			.path("/api/authenticate")
			.toUriString();

		JsonObject requestObj = new Login(username, password).toJSON();

		RequestEntity<String> req = RequestEntity
			.post(requestUrl)
			.contentType(MediaType.APPLICATION_JSON)
			.body(requestObj.toString(), String.class);

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> res = template.exchange(req, String.class);
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
		authenticationRepository.disableNewUser(username);
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return authenticationRepository.isUserDisabled(username);
	}

	public void increaseLoginAttempt(String username) {
		authenticationRepository.increaseLoginAttempt(username);
	}

	public boolean isAttemptsExceeded(String username) {
		Integer attemptCount = authenticationRepository.getLoginAttempt(username);
		return attemptCount >= 3;
	}

	public void resetLoginAttempt(String username) {
		authenticationRepository.removeLoginAttempts(username);
	}

	public void addAuthenticatedSession(String sessionId) {
		authenticationRepository.addAuthenticatedSession(sessionId);
	}

	public boolean isSessionAuthenticated(String sessionId) {
		return authenticationRepository.isSessionAuthenticated(sessionId);
	}

	public void removeAuthenticatedSession(String sessionId) {
		authenticationRepository.removeAuthenticatedSession(sessionId);
	}
}
