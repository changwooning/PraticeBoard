package com.example.praticeboard.repository;

import com.example.praticeboard.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentJpaRepository extends JpaRepository<CommentEntity,Long> {

    List<CommentEntity> findByBoardIdOrderByIdDesc(Long board);
}
