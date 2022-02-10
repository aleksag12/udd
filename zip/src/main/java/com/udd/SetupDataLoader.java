package com.udd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.udd.model.User;
import com.udd.repository.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}

		User user1 = new User("user1@gmail.com", passwordEncoder.encode("sifra123"));
		User user2 = new User("user2@gmail.com", passwordEncoder.encode("sifra123"));
		
		userRepository.save(user1);
		userRepository.save(user2);
		
		alreadySetup = true;
	}

}
