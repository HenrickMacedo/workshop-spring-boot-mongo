package com.henrickmacedo.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.henrickmacedo.workshopmongo.domain.Post;
import com.henrickmacedo.workshopmongo.domain.User;
import com.henrickmacedo.workshopmongo.dto.AuthorDTO;
import com.henrickmacedo.workshopmongo.dto.CommentDTO;
import com.henrickmacedo.workshopmongo.repository.PostRepository;
import com.henrickmacedo.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, null, "Partiu Viagem", "Vou viajar para São Paulo, Abraços!!", new AuthorDTO(maria));
		Post post2 = new Post(null, null, "Bom dia", "Acordei Feliz hoje!!", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa Viagem Mano!!", null, new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!!", null, new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um Ótimo dia!!", null, new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1,post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
