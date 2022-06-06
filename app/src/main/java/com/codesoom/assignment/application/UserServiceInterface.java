package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.UserDTO;
import com.codesoom.assignment.dto.UserResponse;

import java.util.List;

public interface UserServiceInterface {

	/***
	 * 유저를 등록하고 정보를 응답한다.
	 * @param source
	 * @return 유저정보
	 */
    UserResponse createUser(UserDTO.CreateUser source);

	/***
	 * 해당 id 의 유저정보를 응답한다.
	 * @param id
	 * @return 유저정보
	 */
	UserResponse getUser(int id);

	/***
	 * 해당 id 의 유저를 삭제한다.
	 * @param id
	 */
    void deleteUser(int id);

	/***
	 * 유저 전체 목록을 응답한다.
	 * @return 유저정보 리스트
	 */
    List<UserResponse> getUsers();

	/***
	 * 해당 id 의 유저의 정보를 변경한다.
	 * @param id
	 * @param source
	 * @return 변경된 유저 정보
	 */
    UserResponse updateUsers(int id, UserDTO.UpdateUser source);
}
