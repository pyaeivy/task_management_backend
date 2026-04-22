package org.example.teskmanagementbackend.dao;

import org.example.teskmanagementbackend.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BoardDao extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
}
