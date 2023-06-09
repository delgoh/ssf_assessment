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
