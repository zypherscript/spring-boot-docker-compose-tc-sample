package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import java.util.List;
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

  @Test
  void testFindAll() {
    String title = "title";
    String body = "body";
    var post = new Post();
    post.setTitle(title);
    post.setBody(body);

    postRepository.save(post);

    var posts = testRestTemplate.exchange("/api/posts",
        HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
        });

    assertThat(posts.getBody()).isEqualTo(List.of(post));
  }

}
