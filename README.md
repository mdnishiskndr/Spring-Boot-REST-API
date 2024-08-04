# Spring Boot REST API
This is a coding exercise to connect with JSONPlaceholder API https://jsonplaceholder.typicode.com/ and retrieves a list of "post" from the endpoint https://jsonplaceholder.typicode.com/posts.



## Codes Overview
- `PostModel.java`: A model class representing a Post.
- `RestApiController.java`: A controller class to handle API requests.
- `RestApiConfig.java`: A configuration class to create a `RestTemplate` bean.
- `RestApiControllerTest.java`: Unit tests for the `RestApiController` class.



## Installation and Setup
To get started with the Spring Boot REST API, follow these steps:
### 1. Download and install [Java SE 11](https://www.oracle.com/my/java/technologies/javase/jdk11-archive-downloads.html) in your local machine
### 2. Install Java Extension Pack and Spring Boot Extension Pack in Visual Studio Code
### 3. **Clone the Repository**
```sh
git clone https://github.com/your-username/Spring-Boot-REST-API.git
cd Spring-Boot-REST-API
```


## Running the Application
All of the *PostModel*, *RestApiController* and *RestApiConfig* java scripts are located in the `src\main\java` folder
### 1. Build the project
```sh
mvn clean install
```
### 2. Run the application
```sh
mvn spring-boot:run
```
The application will start on port `8080` by default. You can access the API at http://localhost:8080/posts.


## Running Unit Tests
The *RestApiControllerTest* java script is located in `src\main\test`. To run the unit test, use the following command:
```sh
mvn test
```



## Application Codes Structure
### PostModel
This model class initialized the *UserId*, *Id* ,*Title*, *Body* and *TitleLength*.
```java
package com.example.demo;

public class PostModel {

    private int userId;
    private int id;
    private String title;
    private String body;

    // Field to store the length of the title
    private int titleLength; 



    // Getters and Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
        this.titleLength = title.length(); // Set titleLength using length() method to title
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public int getTitleLength(){
        return titleLength;
    }

}
```


### RestApiController
This controller class interacts with an external API to fetch and process data. The url endpoint will be assigned to a string variable.
```java
@RestController 
public class RestApiController { 

    @Autowired 
    private RestTemplate restTemplate; 
    private final String POST_API_URL = "https://jsonplaceholder.typicode.com/posts"; // Assign the url endpoint to string variable
```

The `getPosts()` function will connect with JSONPlaceholder API to set and get all the posts using PostModel in a list. It will also call `findPostWithLongestTitle()` function to find the post with longest title.
```java
// Function to connect with JSONPlaceholder API to set and get all the posts using PostModel in a list
    @GetMapping("/posts") 
    public List<PostModel> getPosts() { 
        PostModel[] posts = restTemplate.getForObject(POST_API_URL, PostModel[].class); 
        List<PostModel> postList = Arrays.asList(posts); 
        findPostWithLongestTitle(postList); // Call the function to find the post with longest title
        return postList; 
    }
```

The `findPostWithLongestTitle()` function will loop through all the posts and utilize if condition to find post with longest title. Then, the function will write back the *UserId*, *Id*, *Title*, *Body* and *TitleLength* of the post to the console.
```java
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
```

### RestApiConfig
 The `RestApiConfig` class is responsible for providing a `RestTemplate` bean to the Spring application context. This allows other components of the application to inject and use `RestTemplate` for making HTTP requests.
 ```java
package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestApiConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

### Output from localhost
![image](https://github.com/user-attachments/assets/58a1dd98-3e4c-4581-9355-58d511df6e65)

### Output from terminal console
![image](https://github.com/user-attachments/assets/cc75e5bd-e903-4eb2-bfd3-d28a178ce96b)



## Unit Tests Code Structure
### RestApiControllerTest
This test class involve testing and validating `getPosts()` and `findPostWithLongestTitle` function in `RestApiController`. Firstly, the test class will setup mockPosts using the PostModel to create a mock data
```java
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
```


Then, the code will proceed to make 2 test which is `testGetPosts()` and `testFindPostWithLongestTitle()`.
- `testGetPosts()` will test whether the retreive post is not null and equals to the mockPosts that have been created.
```java
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
```
- `testFindPostWithLongestTitle()` will test whether the post with the longest title that have been retrieved is equal to the expected output.
```java
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
```

### Test Result
![image](https://github.com/user-attachments/assets/a1e921da-b7da-487e-8d5e-94e763a9441c)


