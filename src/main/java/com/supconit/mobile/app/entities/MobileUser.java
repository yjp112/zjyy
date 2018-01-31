package com.supconit.mobile.app.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.organization.entities.Person;
import hc.base.domains.OrderPart;

/**
 * 扩展User,增加手势密码
 *
 * @author wangwei
 * 
 */
public class MobileUser extends User {

	private static final long serialVersionUID = -8109001732301955533L;
	protected String username;
	protected String email;
	@JSONField(
			serialize = false
	)
	protected String password;
	protected String description;
	protected Long personId;
	protected String avatar;
	private boolean expired = false;
	private boolean disabled = false;
	private boolean locked = false;
	protected Person person;
	private OrderPart[] orderParts;
	private String signPassword;//手势密码
	private int isOpenSignPassword;

	public MobileUser(String username) {
		this.username = username;
	}

	public MobileUser() {
	}

	public MobileUser(Long id) {
		this.id = id;
	}

	public boolean isExpired() {
		return this.expired;
	}

	public boolean isDisabled() {
		return this.disabled;
	}

	public boolean isLocked() {
		return this.locked;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public OrderPart[] getOrderParts() {
		return this.orderParts;
	}

	public void setOrderParts(OrderPart[] orderParts) {
		this.orderParts = orderParts;
	}

	public String getSignPassword() {
		return signPassword;
	}

	public void setSignPassword(String signPassword) {
		this.signPassword = signPassword;
	}

	public int getIsOpenSignPassword() {
		return isOpenSignPassword;
	}

	public void setIsOpenSignPassword(int isOpenSignPassword) {
		this.isOpenSignPassword = isOpenSignPassword;
	}
}
