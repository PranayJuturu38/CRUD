
package com.crudapp.main.requestDTOs;

public class PersonDTo {
	private Integer id;

	private String personname;

	private String password;

	private String email;

	private Integer deptId;

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getid() {
		return id;
	}

	public void setid(Integer id) {
		this.id = id;
	}

	public String getpersonname() {
		return personname;
	}

	public void setpersonname(String personname) {
		this.personname = personname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
