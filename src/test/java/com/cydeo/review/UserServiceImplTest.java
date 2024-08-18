package com.cydeo.review;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/*
Unit testing is to test the smallest individual parts
And when you think about it, it has 2 different options/scenario that can come out as a result.
1-user is found successfully.
2-user does not found
we need to be able to test both of these scenarios.
So we need to create 2 tests.
 */

   //Enables Mockito integration with JUnit 5, If I do this now, I can use J. Unit in combination of mockito
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository; // A mock object to simulate UserRepository behavior
    @Mock
    private UserMapper userMapper;
    @Mock
    private ProjectService projectService;
    @Mock
    private TaskService taskService;
    @Mock
    private KeycloakService keycloakService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;  // Injects the mock repository into the service

    /*
    UserService user--> is this the correct usage?
   No. why? because spring boot is not running context, meaning Ioc container is not existing.
    So there is no bean of the user service.
    So that's why, I have to go with the actual UserServiceImpl
    I'm gonna need to let Mockito create the object of this user service by injecting whatever is needed right?
    So how we are gonna do that. First, we need to provide the mocks.
     */

 /*
      @BeforeAll
      public static void setUpBeforeClass() throws Exception {
          // Some impl
      }

      @AfterAll
      public static void tearDownAfterClass() throws Exception {
          // Some impl
      }

    BeforeAll vs BeforeEach and AfterAll vs AfterEach--> there two differences
   let's say we have 15 test method
    1-If you run the tests from class level:
    -beforeEach and AfterEach is gonna executed 15 times.
    -before all is gonna run only one time even if you run 15 test methods
    -after all, is also going to be executed. After all the tests are done.
    2-before all, and after all, methods needs to be static. why
    --https://excalidraw.com/ some draw web application
     */

/*
we have 3 components, Jupyter, platform and vintage.
1-Junit Jupiter provides us with the necessary annotations like @Test, @BeforeAll, Each, @AfterAll, Each..
 it also brings something called test engine
 TestEngine-->is the thing that is making it possible to run test methods, normally in java If there is no main method.
 You cannot run that application. test engine makes it possible for you to run your test methods.
 2-JUnit platform is providing an API for other libraries to be able to work with Test engine.
 API is not only RestAPI, API means, basically, application programming interface meaning interfaces.
So abstract layer. So it provides the abstract interfaces, abstract classes.
and by using those abstract interfaces and classes the other libraries can connect to
testing. Okay, they can bring their own implementations if it is needed.
But by using the actual interfaces and abstractions coming from the J unit Jupiter?
3-JUnit Vintage --makes it possible for you to use J. Unit 4 and J. Unit 3 annotations as well.it's for adaptation.
 */

    // I need to sample data to test my method
    //I'm testing user service. So I'm going to create some user and user Dto objects.
    User user;
    UserDTO userDTO;

    //I need to initialize the sample data i will do inside @BeforeEach
    @BeforeEach
    public void setUp() throws Exception {

        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("user");
        user.setPassWord("Abc1");
        user.setEnabled(true);
        user.setRole(new Role("Manager"));

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setUserName("user");
        userDTO.setPassWord("Abc1");
        userDTO.setEnabled(true);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setDescription("Manager");

        userDTO.setRole(roleDTO);

    } // I put this one inside to be for each what it means. It means that this data here is going to be created
    // each time whenever I run the test.

    private List<User> getUsers() {
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Emily");
        return List.of(user, user2);
    }

    private List<UserDTO> getUserDTOs() {

        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setFirstName("Emily");

        return List.of(userDTO, userDTO2);

    }

    private User getUserWithRole(String role) {

        User user3 = new User();

        user3.setUserName("user3");
        user3.setPassWord("Abc1");
        user3.setEnabled(true);
        user3.setIsDeleted(false);
        user3.setRole(new Role(role));

        return user3;

    }
/*
it's going to be executed when you normally run user repository because we are going to mock?
So it's not going to be able to run it. But we are still going to need some data.
But that's what I'm trying to do here.
 */
    @AfterEach
    public void tearDown() throws Exception {
        // Some impl
    }
    // TestNG --> other testing library just like jUnit
    // Test Engine

    // we don't need to return anything in my test method, because
    //we are gonna do the verification, assertion in the test,
    // and then we are done.
    //these methods are the methods that we are running, just like how we run main method.
    // we don't get the return value from a main method in java
    //main method is returning void

    @Test
    public void test() {
     // fail(); //If we want to make this one fail without putting in any actual implementation
     // this is what we can do.
    }// default behaviour of a test is pass

    // I want to test listAllUsers() in UserServiceImpl class

    @Test
    public void should_list_all_users() {
        //stab (fake implementation), to do that we use when
        //given- Preparation
        when(userRepository.findAllByIsDeletedOrderByFirstNameDesc(false)).thenReturn(getUsers()); //to use this method to do our stabs, provide the fake implementation for the mock methods,
        //and I'm gonna return some fake list of users. (getUsers() which is we created inside this Test class above)
        when(userMapper.convertToDto(user)).thenReturn(userDTO); // we are matching user and userDTO
        when(userMapper.convertToDto(getUsers().get(1))).thenReturn(getUserDTOs().get(1));

        // these two line can be like this  when(userMapper.convertToDto(any(User.class))).thenReturn(userDTO, getUserDTOs().get(1));
        // any kind of user object, thenReturn(userDTO, getUserDTOs().get(1)) means, first time executed expected userDTO, second time executed getUserDTOs().get(1)) means
        /*
        thenReturn method is using something called spread operator.
        Spread Operator(...): In some languages like JavaScript, the spread operator (...) allows an iterable such as an array to be expanded in places
        where zero or more arguments (for function calls) or elements (for array literals) are expected. Java doesn't have a direct equivalent to the spread operator,
        but it does have varargs (...) for methods that take a variable number of arguments.
        T... in here T is just generic, it can be any kind of object, and  ... means that you can pass any amount of them
         */

        List<UserDTO> expectedList = getUserDTOs();

        // until now we did preparation, I didn't still actual action

        //when - Action
        //userService.listAllUsers(); // after the action I need to capture the data that is coming form listAllUsers
        List<UserDTO> actualList = userService.listAllUsers();

        //then - Assertion/Verification (and, or)
        //JUnit has a bunch of assertion methods a lot of them, there ara a lot of overloaded method
        //assertEquals(expectedList, actualList); // obj1, obj2- objs3, obj4 , it will check memory address, we need to @EqualsAndHashCode in UserDTO
        /*
        if it is not Equlas method in some class, it is checking the addresses, because it is overriding form OBject. we can create override method -->@EqualsAndHashCode we need to do put it inside UserDTO class
        because we are comparing listOfDTO
         */
        //first solution of equals issue is adding @EqualsAndHashCode
        // another solution equals issue is assertThat that is coming from AsserJ library,
        //AssertJ library
        assertThat(actualList).usingRecursiveComparison().isEqualTo(expectedList);
        //if you use it like (usingRecursiveComparison()) that Even if you don't have @EqualsHashcode.
        //it will check the data instead of the object memory addresses.

        verify(userRepository, times(1)).findAllByIsDeletedOrderByFirstNameDesc(false);
        verify(userRepository, never()).findAllByIsDeletedOrderByFirstNameDesc(true);

    }

    // we want to test  `if (user == null) throw new NoSuchElementException("User not found.")`;
    // I should give some explanatory name to method like should_throw_no_such_element_exception_when_user_not_found
       @Test
    public void should_throw_no_such_element_exception_when_user_not_found(){
        // this kind of name of test is common like this, in the test you should explain what we are testing
        // we have already mock for repository and mapper
       //given (in here we don't need to do anything in given step, because we test only throw)
       // when(userRepository.findByUserNameAndIsDeleted(anyString(), anyBoolean())).thenReturn(null); //we don't need this one,
       // because mock object from UserRepository is gonna return null by default
       // we test only throw
        // when(userMapper.convertToDto(user)).thenReturn(userDTO); //we don't need this one we test only throw
        //when + then
        // Throwable actualException = assertThrows(NoSuchElementException.class, ()-> userService.findByUserName("SomeUserName")); // it passed
      // Throwable actualException = assertThrowsExactly(RuntimeException.class, ()-> userService.findByUserName("SomeUserName")); // it is failed
     Throwable actualException = assertThrows(NoSuchElementException.class, ()-> userService.findByUserName("SomeUsername")); // it passed

        // we can not use 'any' stuff because 'any' comes with Mockito, but now we work actual part in here

       assertEquals("User not found.", actualException.getMessage()); // it passed
        // this is asserted throw method by using jUnit library

        //AssertJ
         //Throwable actualException = catchThrowable(()->userService.findByUserName("SomeUsername"));
        // this is another way to doing same thing
    }
    /*
      User Story - 1: As a user of the application, I want my password to be encoded
     so that my account remains secure.

     Acceptance Criteria:
     1 - When a user creates a new account, their password should be encoded using
     a secure algorithm such as bcrypt or PBKDF2.

     2 - Passwords should not be stored in plain text in the database or any other storage.

     3 - Passwords encoding should be implemented consistently throughout the application,
     including any password reset or change functionality.

     */

    @Test
    void should_encode_user_password_on_save_operation(){

        when(userMapper.convertToEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user); // we can use any(User.class) but in here it is example, it can be done like this
        when(userMapper.convertToDto(any(User.class))).thenReturn(userDTO);
        when(passwordEncoder.encode(anyString())).thenReturn("some-password");

        String expectedPassword = "some-password";
        //  we skip keycloakService mock, because we are not going to use any value from this part in our test

        // when
        UserDTO savedUser = userService.save(userDTO);

        //then
        assertEquals(expectedPassword, savedUser.getPassWord());

        // verify
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void should_encode_user_password_on_update_operation(){
        when(userRepository.findByUserNameAndIsDeleted(anyString(), anyBoolean())).thenReturn(user);
        when(userMapper.convertToEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user); // we can use any(User.class) but in here it is example, it can be done like this
        when(userMapper.convertToDto(any(User.class))).thenReturn(userDTO);
        when(passwordEncoder.encode(anyString())).thenReturn("some-password");

        String expectedPassword = "some-password";
        //  we skip keycloakService mock

        // when
        UserDTO updatedUser = userService.update(userDTO);

        //then
        assertEquals(expectedPassword, updatedUser.getPassWord());
        // then
        //verify that passwordEncoder is executed
        verify(passwordEncoder, times(1)).encode(anyString());
    }
/*
    User Story 2: As an admin, I shouldn't be able to delete a manager user,
    if that manager has projects linked to them to prevent data loss.

    Acceptance Criteria:

    1 - The system should prevent a manager user from being deleted
    if they have projects linked to them.
    2 - An error message should be displayed to the user if they attempt
    to delete a manager user with linked projects.

    User Story 3: As an admin, I shouldn't be able to delete an employee user,
    if that employee has tasks linked to them to prevent data loss.

    Acceptance Criteria:

    1 - The system should prevent an employee user from being deleted
    if they have tasks linked to them.
    2 - An error message should be displayed to the user if they attempt
    to delete an employee user with linked tasks.

 */

    @Test
    void should_delete_manager() throws TicketingProjectException {
        // given - Preparation
        User managerUser = getUserWithRole("Manager");
        when(userRepository.findByUserNameAndIsDeleted(anyString(), anyBoolean())).thenReturn(managerUser);
        when(userRepository.save(any())).thenReturn(managerUser);
        when(projectService.listAllNonCompletedByAssignedManager(any())).thenReturn(new ArrayList<>());
        // when - Action
        userService.delete(managerUser.getUserName());
        // then - Assertion/Verification
        assertTrue(managerUser.getIsDeleted());
        assertNotEquals("user3", managerUser.getUserName());
    }


    @Test
    void should_delete_employee() throws TicketingProjectException {
        // given - Preparation
        User employeeUser = getUserWithRole("Employee");
        when(userRepository.findByUserNameAndIsDeleted(anyString(), anyBoolean())).thenReturn(employeeUser);
        when(userRepository.save(any())).thenReturn(employeeUser);
        when(taskService.listAllNonCompletedByAssignedEmployee(any())).thenReturn(new ArrayList<>());
        // when - Action
        userService.delete(employeeUser.getUserName());
        // then - Assertion/Verification
        assertTrue(employeeUser.getIsDeleted());
        assertNotEquals("user3", employeeUser.getUserName());

    }

    @ParameterizedTest
    @ValueSource(strings = {"Manager", "Employee"})
    void should_delete_user(String role) throws TicketingProjectException {

        // given - Preparation
        User testUser = getUserWithRole(role);

        when(userRepository.findByUserNameAndIsDeleted(anyString(), anyBoolean())).thenReturn(testUser);
        when(userRepository.save(any())).thenReturn(testUser);

//        when(projectService.listAllNonCompletedByAssignedManager(any())).thenReturn(new ArrayList<>());
//        when(taskService.listAllNonCompletedByAssignedEmployee(any())).thenReturn(new ArrayList<>());

//        if (testUser.getRole().getDescription().equals("Manager")) {
//            when(projectService.listAllNonCompletedByAssignedManager(any())).thenReturn(new ArrayList<>());
//        } else if (testUser.getRole().getDescription().equals("Employee")) {
//            when(taskService.listAllNonCompletedByAssignedEmployee(any())).thenReturn(new ArrayList<>());
//        }

        lenient().when(projectService.listAllNonCompletedByAssignedManager(any())).thenReturn(new ArrayList<>());
        lenient().when(taskService.listAllNonCompletedByAssignedEmployee(any())).thenReturn(new ArrayList<>());

        // when - Action
        userService.delete(testUser.getUserName());

        // then - Assertion/Verification
        assertTrue(testUser.getIsDeleted());
        assertNotEquals("user3", testUser.getUserName());

    }

    @Test
    void should_throw_exception_when_deleting_manager_with_project() {

        User managerUser = getUserWithRole("Manager");

        when(userRepository.findByUserNameAndIsDeleted(anyString(), anyBoolean())).thenReturn(managerUser);
        when(projectService.listAllNonCompletedByAssignedManager(any())).thenReturn(List.of(new ProjectDTO(), new ProjectDTO()));

        Throwable actualException = assertThrows(TicketingProjectException.class, () -> userService.delete(managerUser.getUserName()));

        assertEquals("User can not be deleted", actualException.getMessage());

    }

    @Test
    void should_throw_exception_when_deleting_employee_with_task() {

        User employeeUser = getUserWithRole("Employee");

        when(userRepository.findByUserNameAndIsDeleted(anyString(), anyBoolean())).thenReturn(employeeUser);
        when(taskService.listAllNonCompletedByAssignedEmployee(any())).thenReturn(List.of(new TaskDTO(), new TaskDTO()));

        Throwable actualException = assertThrows(TicketingProjectException.class, () -> userService.delete(employeeUser.getUserName()));

        assertEquals("User can not be deleted", actualException.getMessage());

    }

    //	User Story 4: As an admin, I shouldn't be able to delete an admin user,
    //	if that admin is the last admin in the system.
    //
    //	Acceptance Criteria:
    //
    //	1 - The system should prevent an admin user from being deleted
    //	if it is the last admin.
    //	2 - An error message should be displayed to the user if there is an
    //	attempt to delete the last admin user.

}