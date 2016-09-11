package com.maaz.lms.service;

public interface LoginService {
	public Integer login(String username, String password);

	public void changePassword(String username, String password);
}
