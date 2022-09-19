import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserUpdateTest {
    public UserClient userClient;
    public User user;
    private String accessToken;

    @Before
    public void setup() {
        userClient = new UserClient();
        user = User.getRandomUser();
        Response response = userClient.create(user);
        accessToken = response.then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Update Email for autorization user")
    public void UserAuthorizationUpdateEmailTest() {
        userClient.getDataUser(accessToken);
        user.setEmail(User.getRandomEmail());
        Response response = userClient.setDataUserWithToken(accessToken, new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Update name of user for autorization user")
    public void UserAuthorizationUpdateNameTest() {
        userClient.getDataUser(accessToken);
        user.setName(User.getRandomName());
        Response response = userClient.setDataUserWithToken(accessToken, new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Update login of user for unautorization user")
    public void UserNoAuthorizationUpdateEmailTest() {
        userClient.getDataUser(accessToken);
        user.setEmail(User.getRandomEmail());
        Response response = userClient.setDataUserWithoutToken(new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(401);
    }

    @Test
    @DisplayName("Update nem of user for unautorization user ")
    public void UserNoAuthorizationUpdateNameTest() {
        userClient.getDataUser(accessToken);
        user.setName(User.getRandomName());
        Response response = userClient.setDataUserWithoutToken(new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(401);
    }

    @After
    public void deleteUser() {
        userClient.deleteUser();
    }
}