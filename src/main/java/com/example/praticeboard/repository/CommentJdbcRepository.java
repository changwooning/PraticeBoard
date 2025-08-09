package com.example.praticeboard.repository;

import com.example.praticeboard.model.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Comment> ROW = (rs, i) -> {
        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");
        return new Comment(
                rs.getLong("id"),
                rs.getLong("board_id"),
                rs.getString("author"),
                rs.getString("content"),
                created != null ? created.toLocalDateTime().toLocalDate() : null,
                updated != null ? updated.toLocalDateTime().toLocalDate() : null
        );
    };

    public List<Comment> findByBoardId(Long boardId) {
        return jdbcTemplate.query("SELECT * FROM comments WHERE board_id = ? ORDER BY id DESC", ROW, boardId);
    }

    public Optional<Comment> findById(Long id) {
        List<Comment> list = jdbcTemplate.query("SELECT * FROM comments WHERE id = ?", ROW, id);
        return list.stream().findFirst();
    }

    public Long insert(Long boardId, String author, String content) {
        String sql = """
        INSERT INTO comments(board_id, author, content, created_at, updated_at)
        VALUES(:boardId, :author, :content, :createdAt, :updatedAt)
    """;

        MapSqlParameterSource p = new MapSqlParameterSource()
                .addValue("boardId", boardId)
                .addValue("author", author)
                .addValue("content", content)
                .addValue("createdAt", LocalDateTime.now())
                .addValue("updatedAt", null);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, p, keyHolder, new String[]{"id"});
        return keyHolder.getKey().longValue();  // ✅ H2에서도 자동 생성 키 회수
    }

    public int updateContent(Long id, String content) {
        return jdbcTemplate.update("UPDATE comments SET content = ?, updated_at = ? WHERE id = ?",
                content, Timestamp.valueOf(LocalDateTime.now()), id);
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM comments WHERE id = ?", id);
    }

}
