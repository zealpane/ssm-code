package dps.service;

import dps.dao.UserDao;

public class UserServiceImpl implements UserService {
	private UserDao testDao;
	public void setTestDao(UserDao testDao) {
		this.testDao = testDao;
	}
	public void modify() {
		testDao.modify();
	}
	public void view() {
		testDao.view();
	}
}