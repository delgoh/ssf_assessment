package vttp2023.batch3.ssf.frontcontroller.respositories;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository {

	@Autowired
	private RedisTemplate<String, Object> template;

	// TODO Task 5
	// Use this class to implement CRUD operations on Redis

	public void disableNewUser(String username) {
		template.opsForValue().set(username, "", 30, TimeUnit.SECONDS);
	}

	public boolean isUserDisabled(String username) {
		return template.opsForValue().get(username) != null;
	}


	public void increaseLoginAttempt(String username) {
		if (template.opsForHash().hasKey("USERS_LOGIN_COUNT", username)) {
			int currentAttempt = Integer.parseInt((String) template.opsForHash().get("USERS_LOGIN_COUNT", username));
			template.opsForHash().put("USERS_LOGIN_COUNT", username, String.valueOf(currentAttempt + 1));
		} else {
			template.opsForHash().put("USERS_LOGIN_COUNT", username, String.valueOf(1));
		}
	}

	public Integer getLoginAttempt(String username) {
		return Integer.parseInt((String) template.opsForHash().get("USERS_LOGIN_COUNT", username));
	}

	public void removeLoginAttempts(String username) {
		template.opsForHash().delete("USERS_LOGIN_COUNT", username);
	}


	public void addAuthenticatedSession(String sessionId) {
		template.opsForSet().add("AUTHENTICATED_SESSIONS", sessionId);
	}

	public boolean isSessionAuthenticated(String sessionId) {
		return template.opsForSet().isMember("AUTHENTICATED_SESSIONS", sessionId);
	}

	public void removeAuthenticatedSession(String sessionId) {
		template.opsForSet().remove("AUTHENTICATED_SESSIONS", sessionId);
	}

}
