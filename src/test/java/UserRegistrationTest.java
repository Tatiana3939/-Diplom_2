import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserRegistrationTest {
    public UserClient userClient;
    public User user;
    Response response;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Create unique user")
    public void testUserIsCreated() {

        user = User.getRandomUser();
        response = userClient.create(user);
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Create user, who was created")
    public void testUserIsAlreadyRegister() {

        user = User.getRandomUser();
        userClient.create(user);
        response = userClient.create(user);
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(403);
    }

    @Test
    @DisplayName("Create user without filling password field email")
    public void testUserIsCreatedWithoutEmail() {

        user = User.getRandomUser();
        user.setEmail(null);
        userClient.create(user);
        response = userClient.create(user);
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(403);
    }

    @Test
    @DisplayName("Create user without filling password field")
    public void testUserIsCreatedWithoutPassword() {

        user = User.getRandomUser();
        user.setPassword(null);
        userClient.create(user);
        response = userClient.create(user);
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(403);
    }

    @Test
    @DisplayName("Create user without name field")
    public void testUserIsCreatedWithoutName() {

        user = User.getRandomUser();
        user.setName(null);
        userClient.create(user);
        response = userClient.create(user);
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(403);
    }

    @After
    public void deleteUser() {
        userClient.deleteUser();
    }
}