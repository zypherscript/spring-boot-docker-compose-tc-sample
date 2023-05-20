package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("tc")
class DemoApplicationTests {

  @Container
  static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(
      DockerImageName.parse("postgres:15.1"));

  static {
    POSTGRES.start();
  }

  @DynamicPropertySource
  static void props(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES::getUsername);
    registry.add("spring.datasource.password", POSTGRES::getPassword);
  }

  @Autowired
  private PostRepository postRepository;

  @Test
  void testFindAll() {
    String title = "title";
    String body = "body";
    var post = new Post();
    post.setTitle(title);
    post.setBody(body);

    postRepository.save(post);

    var posts = postRepository.findAll();
    assertEquals(posts.size(), 1);
    assertEquals(posts.get(0).getTitle(), title);
    assertEquals(posts.get(0).getBody(), body);
  }

}
