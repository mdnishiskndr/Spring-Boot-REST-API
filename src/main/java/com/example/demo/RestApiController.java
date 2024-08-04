package com.example.demo; 
 
import java.util.Arrays; 
import java.util.List; 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.client.RestTemplate; 
 
@RestController 
public class RestApiController { 
     
    @Autowired 
    private RestTemplate restTemplate; 
    private final String POST_API_URL = "https://jsonplaceholder.typicode.com/posts"; // Assign the url endpoint to string variable
 

    // Function to connect with JSONPlaceholder API to set and get all the posts using PostModel in a list
    @GetMapping("/posts") 
    public List<PostModel> getPosts() { 
        PostModel[] posts = restTemplate.getForObject(POST_API_URL, PostModel[].class); 
        List<PostModel> postList = Arrays.asList(posts); 
        findPostWithLongestTitle(postList); // Call the function to find the post with longest title
        return postList; 
    } 
 

    // Function to get post with the longest title
    private void findPostWithLongestTitle(List<PostModel> posts) { 
        PostModel postWithLongestTitle = null; 
        int maxLength = 0; 
 
        // Loop through all the posts and utilize if condition to find post with longest title
        for (PostModel post : posts) { 
            if (post.getTitleLength() > maxLength) { 
                maxLength = post.getTitleLength(); 
                postWithLongestTitle = post; 
            } 
        } 
 
        // Write the UserId, Id, Title, Body and Titlte Length of the post to the console
        if(postWithLongestTitle != null) { 
            System.out.println("Post with the longest title:"); 
            System.out.println("UserId: " + postWithLongestTitle.getUserId()); 
            System.out.println("Id: " + postWithLongestTitle.getId()); 
            System.out.println("Title: " + postWithLongestTitle.getTitle()); 
            System.out.println("Body: " + postWithLongestTitle.getBody()); 
            System.out.println("Title Length: " + postWithLongestTitle.getTitleLength()); 
        } 
    } 
     
}