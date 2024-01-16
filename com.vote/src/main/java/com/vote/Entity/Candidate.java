package com.vote.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Candidate_data")
@Data
public class Candidate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long candidateId;
	
	@Column(name="candidateName")
	private String candidateName;
	
	@Column(name="voteCount")
	private int voteCount;

	public Candidate(long candidateId, String candidateName, int voteCount) {
		super();
		this.candidateId = candidateId;
		this.candidateName = candidateName;
		this.voteCount = voteCount;
	}

	public Candidate() {}
}
