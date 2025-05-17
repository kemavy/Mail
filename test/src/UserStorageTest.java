package mail;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserStorageTest {

    @Test
    public void testAddUser() {
        User user = new User("Mary");
        UserStorage users = new UserStorage();
        users.addUser(user);
        assertTrue(users.isExist("Mary"));
    }

    @Test
    public void testGetUserTrue() {
        User user = new User("Mary");
        UserStorage users = new UserStorage();
        users.addUser(user);
        assertEquals(user, users.getUser("Mary"));
    }

    @Test
    public void testGetUserFalse() {
        User user = new User("Mary");
        UserStorage users = new UserStorage();
        users.addUser(user);
        assertNull(users.getUser("Mike"));
    }

    @Test
    public void testGetUnexistentUser() {
        UserStorage users = new UserStorage();
        assertNull(users.getUser("Mary"));
    }

    @Test
    public void testIsEmptyIfEmpty() {
        UserStorage users = new UserStorage();
        assertTrue(users.isEmpty());
    }

    @Test
    public void testIsEmptyIfNot() {
        User user = new User("Mary");
        UserStorage users = new UserStorage();
        users.addUser(user);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testIsExistIfExist() {
        User user = new User("Mary");
        UserStorage users = new UserStorage();
        users.addUser(user);
        assertTrue(users.isExist("Mary"));
    }

    @Test
    public void testIsExistIfNot() {
        User user = new User("Mary");
        UserStorage users = new UserStorage();
        users.addUser(user);
        assertFalse(users.isExist("Mike"));
    }

    @Test
    public void testIsExistIfEmpty() {
        UserStorage users = new UserStorage();
        assertFalse(users.isExist("Mike"));
    }

    @Test
    public void testGetList() {
        User user = new User("Mary");
        UserStorage users = new UserStorage();
        users.addUser(user);
        assertEquals("Mary", users.getList());
    }


}