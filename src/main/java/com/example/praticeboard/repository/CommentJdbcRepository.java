package com.example.praticeboard.repository;

import com.example.praticeboard.model.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static javax.swing.text.html.parser.DTDConstants.ID;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
        String sql = "INSERT INTO comments(board_id, author, content) VALUES (?, ?, ?)";

        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            // 생성키 컬럼을 'id'로 한정
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, boardId);
            ps.setString(2, author);
            ps.setString(3, content);
            return ps;
        }, kh);

        // H2는 보통 대문자 키("ID")로 반환하므로 getKeys()에서 우선 조회
        Number idNum = (kh.getKeys() != null && kh.getKeys().get("ID") != null)
                ? (Number) kh.getKeys().get("ID")
                : kh.getKey(); // fallback (단일 키일 때만 존재)

        return idNum.longValue();
    }

    public int updateContent(Long id, String content) {
        return jdbcTemplate.update("UPDATE comments SET content = ?, updated_at = ? WHERE id = ?",
                content, Timestamp.valueOf(LocalDateTime.now()), id);
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM comments WHERE id = ?", id);
    }

}
