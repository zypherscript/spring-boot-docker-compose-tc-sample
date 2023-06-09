package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("tc")
@Import(TestContainersConfiguration.class)
public class DemoApplicationTests {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  protected TestRestTemplate testRestTemplate;

  private Post post;

  @BeforeEach
  void init() {
    post = new Post();
    post.setTitle("title");
    post.setBody("body");

    postRepository.save(post);
  }

  @AfterEach
  void tearDown() {
    postRepository.deleteAll();
  }

  @Test
  void testFindAll() {
    var posts = postRepository.findAll();

    assertEquals(posts.size(), 1);
    assertEquals(posts.get(0).getTitle(), post.getTitle());
    assertEquals(posts.get(0).getBody(), post.getBody());
  }

  @Test
  void testRequest() {
    var posts = testRestTemplate.exchange("/api/posts",
        HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
        });

    assertEquals(posts.getBody(), List.of(post));
  }

}
