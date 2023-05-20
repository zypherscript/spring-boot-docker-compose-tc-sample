package com.example.demo;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import java.util.stream.IntStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  @Profile("!tc")
  CommandLineRunner runner(PostRepository postRepository) {
    return args -> {
      var posts = IntStream.range(0, 2)
          .boxed()
          .map(i -> new Post(null, "title".concat(String.valueOf(i)),
              "body".concat(String.valueOf(i))))
          .toList();
      postRepository.saveAll(posts);
    };
  }
}
