package com.uxpsystems.assignement.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.uxpsystems.assignement.exception.UserException;
import com.uxpsystems.assignement.model.User;
import com.uxpsystems.assignement.model.UserStatus;
import com.uxpsystems.assignement.service.UserService;

public class UserControllerTest {
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	private List<User> userList;
	private User user1, user2;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userList = new ArrayList<User>();
		
		user1 = new User();
		user1.setId(1L);
		user1.setPassword("123123");
		user1.setStatus(UserStatus.Activated);
		user1.setUsername("riardorqr");
		userList.add(user1);
		
		user2 = new User();
		user2.setId(2L);
		user2.setPassword("qweqwe");
		user2.setStatus(UserStatus.Activated);
		user2.setUsername("qweqwe");
		userList.add(user2);
	}
	
	@Test
	public void testGetAllUsers() {
		when(userService.getAllUsers()).thenReturn(userList);
		
		List<User> dataBaseUserList = userController.getAllUsers();
		assertNotNull(dataBaseUserList);
		assertEquals(dataBaseUserList.size(), 2);
	}
	
	@Test
	public void testGetAllUsersReturningEmptyList() {
		when(userService.getAllUsers()).thenReturn(new ArrayList<User>());
		
		List<User> dataBaseUserList = userController.getAllUsers();
		assertNotNull(dataBaseUserList);
		assertEquals(dataBaseUserList.size(), 0);
	}
	
	@Test
	public void testGetUser() {
		when(userService.getUserById(Mockito.anyLong())).thenReturn(Optional.of(user1));
		
		User user = userController.getUser(1L);
		
		assertNotNull(user);
		assertEquals(user, this.user1);
		assertEquals(user.getId().longValue(), user1.getId().longValue());
	}
	
	@Test(expected = UserException.class)
	public void testGetUserWithAnyOtherElseID() {
		when(userService.getUserById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		User user = userController.getUser(10L);
		
		assertNotNull(user);
		assertEquals(user, null);
	}

	@Test
	public void testSaveUser() {
		when(userService.saveUser(Mockito.any(User.class))).thenReturn(user1);
		
		User user = new User();
		user.setPassword("123123");
		user.setStatus(UserStatus.Activated);
		user.setUsername("riardorqr");
		User savedUser = userController.saveUser(user);
		
		assertNotNull(savedUser);
		assertEquals(savedUser.getId().longValue(), user1.getId().longValue());
	}
	
	@Test(expected = UserException.class)
	public void testSaveUserWithTheSameUsername() {
		when(userService.getUserByUsername(Mockito.anyString())).thenReturn(Optional.of(user1));
		
		User user = new User();
		user.setPassword("123123");
		user.setStatus(UserStatus.Activated);
		user.setUsername("riardorqr");
		User savedUser = userController.saveUser(user);
		
		assertNotNull(savedUser);
		assertEquals(savedUser.getId().longValue(), user1.getId().longValue());
	}
	
	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setId(1L);
		user.setPassword("123123");
		user.setStatus(UserStatus.Activated);
		user.setUsername("riardorqrTestOld");
		when(userService.updateUser(Mockito.any(User.class))).thenReturn(user);

		User savedUser = userController.updateUser(user, 1L);
		
		assertNotNull(savedUser);
		assertEquals(savedUser.getUsername(), user.getUsername());
	}
	
	@Test(expected = UserException.class)
	public void testUpdateUserWithTheSameUserName() {
		when(userService.getUserByUsername(Mockito.anyString())).thenReturn(Optional.of(user1));
		
		User user = new User();
		user.setId(1L);
		user.setPassword("123123");
		user.setStatus(UserStatus.Activated);
		user.setUsername("riardorqr");
		User savedUser = userController.updateUser(user, 1L);
		
		assertNotNull(savedUser);
		assertEquals(savedUser.getUsername(), user.getUsername());
	}
	
}
