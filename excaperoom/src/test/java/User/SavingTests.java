package User;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.model.DataLoader;
import com.model.User;
import com.model.UserList;


public class SavingTests {
    @Test
    public void testSaveUsers() {
        UserList users = UserList.getInstance();
        users.addUser("username", "firstName", "lastName", "password");
        DataLoader.getUserList();
        ArrayList<User> userList = DataLoader.getUserList();
        boolean found = false;

        for(User user : userList){
            if(user.getFirstName().equalsIgnoreCase("Ben")){
                found = true;
            }
        }

        assertTrue(found);
    }
}