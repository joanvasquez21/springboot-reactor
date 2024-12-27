package com.springboot.reactor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.reactor.entity.Comments;
import com.springboot.reactor.entity.User;
import com.springboot.reactor.entity.UserWithComments;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//exampleIterator();
		//exampleFlatMap();
		//exampleToString();
		exampleToCollectList();

	}
	

	public void exampleUserCommentsZipWith() {
		Mono<User> userMono = Mono.fromCallable( ()-> new User("John", "Doe"));
		
		Mono<Comments> commentsUserMono = Mono.fromCallable( ()-> {		
			Comments comments = new Comments();
			comments.addComment("Hi John doe");
			return comments;
		});
		
		userMono.zipWith(commentsUserMono, (user, commentsUser) -> new UserWithComments(user, commentsUser))
		.subscribe(uc -> log.info(uc.toString()));
	}

	
	public void exampleUserCommentsFlatMap() {
		//First it is created mono type observable
		//First of user
		Mono<User> userMono = Mono.fromCallable( ()-> new User("John", "Doe"));
		
		
		
		Mono<Comments> commentsMono = Mono.fromCallable( ()-> {		
			Comments comments = new Comments();
			comments.addComment("Hi John doe");
			return comments;
		});
		
		userMono.flatMap(u -> commentsMono.map(c -> new UserWithComments(u, c)));
	}

	public void exampleToCollectList() throws Exception {
		List<User> usersList = new ArrayList<>();
		usersList.add(new User("Joan", "Fulano"));
		usersList.add(new User("Ana", "Six"));
		usersList.add(new User("Luis", "Five"));
		usersList.add(new User("Manuel", "Nine"));

		 /*Flux.just("Ana Sofia", "Andres Guzman", "Joan Juan", "Joan Liam" ); */
		Flux.fromIterable(usersList)
				.map(user -> user.getName().toUpperCase().concat(" ").concat(user.getLastname().toUpperCase()))
				.flatMap( name -> {
					if(name.contains("joan".toUpperCase())) {
						return Mono.just(name);
					}else {
						return Mono.empty();
					}
				})
				.map(name -> {
					return name.toLowerCase();
				}).subscribe( u -> log.info( u.toString()));
	}
	

	public void exampleToString() throws Exception {
		List<User> usersList = new ArrayList<>();
		usersList.add(new User("Joan", "Fulano"));
		usersList.add(new User("Ana", "Six"));
		usersList.add(new User("Luis", "Five"));
		usersList.add(new User("Manuel", "Nine"));

		 /*Flux.just("Ana Sofia", "Andres Guzman", "Joan Juan", "Joan Liam" ); */
		Flux.fromIterable(usersList)
				.map(user -> user.getName().toUpperCase().concat(" ").concat(user.getLastname().toUpperCase()))
				.flatMap( name -> {
					if(name.contains("joan".toUpperCase())) {
						return Mono.just(name);
					}else {
						return Mono.empty();
					}
				})
				.map(name -> {
					return name.toLowerCase();
				}).subscribe( u -> log.info( u.toString()));
	}
	

	public void exampleFlatMap() throws Exception {
		List<String> usersList = new ArrayList<>();
		usersList.add("Joan Fulano");
		usersList.add("Ana Six");
		usersList.add("Luis Five");
		usersList.add("Manuel Nine");

		 /*Flux.just("Ana Sofia", "Andres Guzman", "Joan Juan", "Joan Liam" ); */
		Flux.fromIterable(usersList)
				.map(name -> new User(name.split(" ")[0].toUpperCase(),name.split(" ")[1].toUpperCase()))
				.flatMap( user -> {
					if(user.getName().equalsIgnoreCase("joan")) {
						return Mono.just(user);
					}else {
						return Mono.empty();
					}
				})
				.map(user -> {
					String name = user.getName().toLowerCase();
					user.setName(name);
					return user;
				}).subscribe( u -> log.info( u.toString()));

	}
	
	
	public void exampleIterator() throws Exception {
		
		List<String> usersList = new ArrayList<>();
		usersList.add("Joan Fulano");
		usersList.add("Ana Six");
		usersList.add("Luis Five");
		usersList.add("Manuel Nine");

		 /*Flux.just("Ana Sofia", "Andres Guzman", "Joan Juan", "Joan Liam" ); */
		Flux<String> names = Flux.fromIterable(usersList);
			 Flux<User> users = 	names.map(name -> new User(name.split(" ")[0].toUpperCase(),name.split(" ")[1].toUpperCase()))
				.filter(user -> user.getName().toLowerCase().equals("joan"))
				.doOnNext(user -> {
					if (user == null) {
						throw new RuntimeException("Names cannot be empty");
					}

					System.out.print("DO ON NEXT" + user.getName().concat(" ").concat(user.getLastname()));
				}).map(user -> {
					String name = user.getName().toLowerCase();
					user.setName(name);
					return user;
				});

		users.subscribe(e -> log.info(e.toString()), error -> log.error(error.getMessage()), new Runnable() {

			@Override
			public void run() {
				log.info("the execution of the observable has ended with exit!");

			}
		});

	}
}
