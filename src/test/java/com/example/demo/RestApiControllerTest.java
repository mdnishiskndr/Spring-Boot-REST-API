package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RestApiControllerTest {
    
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestApiController apiController;
    private PostModel[] mockPosts;

    @BeforeEach // Setup mockPosts using the PostModel to create a mock data
    public void setUp() {
        mockPosts = new PostModel[]{ // Call the createPost() function
            createPost(1, 1, "Short title", "This is the body of short title"),
            createPost(1, 2, "A bit longer title", "This is the body of a bit longer title"),
            createPost(1, 3, "The longest title of all posts", "This is the body of the longest title of all posts")
        }; 

        // Mock the RestTemplate to return the mock posts
        when(restTemplate.getForObject(anyString(), eq(PostModel[].class))).thenReturn(mockPosts);

    }


    // Function to create each PostModel instances
    private PostModel createPost(int userId, int id, String title, String body) {
        PostModel post = new PostModel();
        post.setUserId(userId);
        post.setId(id);
        post.setTitle(title);
        post.setBody(body);
        return post;
    }

    @Test // Testing to validate the functionality of getPosts method from RestApiController
    public void testGetPosts() {
        List<PostModel> posts = apiController.getPosts();

        assertNotNull(posts);
        assertEquals(3, posts.size());

        for (PostModel post : posts) {
            assertNotNull(post.getTitleLength());
            assertEquals(post.getTitle().length(), post.getTitleLength());
        }
    }

    @Test // Testing to validate the findPostWithLongestTitle method in getPost method from RestApiController
    public void testFindPostWithLongestTitle() {
        List<PostModel> posts = apiController.getPosts();

        PostModel postWithLongestTitle = null;
        int maxLength = 0;

        for (PostModel post : posts) {
            if (post.getTitleLength() > maxLength) {
                maxLength = post.getTitleLength();
                postWithLongestTitle = post;
            }
        }

        assertNotNull(postWithLongestTitle);
        assertEquals("The longest title of all posts", postWithLongestTitle.getTitle());
        assertEquals(3, postWithLongestTitle.getId());
        assertEquals(1, postWithLongestTitle.getUserId());
        assertEquals("This is the body of the longest title of all posts", postWithLongestTitle.getBody());
        assertEquals(postWithLongestTitle.getTitle().length(), postWithLongestTitle.getTitleLength());
    }
}
