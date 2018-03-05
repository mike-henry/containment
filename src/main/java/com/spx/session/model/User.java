package com.spx.session.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spx.session.AuthorityAccess;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@AuthorityAccess
@Getter
@Setter
public class User
{
	@Id
	private String id = UUID.randomUUID().toString();

	private String password;

	private String name;

}
