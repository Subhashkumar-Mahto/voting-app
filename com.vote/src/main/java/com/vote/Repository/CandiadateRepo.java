package com.vote.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote.Entity.Candidate;

@Repository
public interface CandiadateRepo  extends JpaRepository<Candidate, Long> {

}
