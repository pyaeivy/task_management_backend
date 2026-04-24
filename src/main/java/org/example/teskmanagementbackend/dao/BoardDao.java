package org.example.teskmanagementbackend.dao;

import java.util.List;

import org.example.teskmanagementbackend.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BoardDao extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
	
	@Query("""
			select b from Board b where b.user.username = :username
			""")
	List<Board> listBoardByUsername(String username);
}
