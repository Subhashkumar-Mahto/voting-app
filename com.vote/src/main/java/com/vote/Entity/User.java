package com.vote.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name="user_details",uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@Column(name="username", nullable =false)
	private String username;
	
	@Column(name="password", nullable =false)
	private String password;
	
	@Column(name="email")
	private String email;
	
	@Column(name="mobile", nullable =false)
	private String mobile;

	@Column(name="hasVoted", nullable =false)
	private boolean hasVoted;
	
	@Column(name="DateTime", nullable = true)
	private LocalDateTime DateTime;
	
	@Column(name="latitude", nullable=true)
	private Double latitude;
	
	@Column(name="longitude", nullable=true)
    private Double longitude;
	
	@Column(name="userType", nullable =false)
	private String userType;
	
	@Column(name="candidateId", nullable =false)
	private long candidateId;

	public User(long userId, String username, String password, String email, String mobile, boolean hasVoted,LocalDateTime DateTime,
			Double latitude,Double longitude,String userType, long candidateId) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
		this.hasVoted = hasVoted;
		this.DateTime = DateTime;
		this.latitude = latitude;
		this.longitude =longitude;
		this.userType = userType;
		this.candidateId = candidateId;
	}

	public User() {}
	
	

}
