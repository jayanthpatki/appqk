package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Comment;

import java.util.List;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<Comment> {

    public List<Comment> findByBlogId(Long blogId) {
        return list("blogId", blogId);
    }

    public void deleteByIdAndUserIdAndBlogId(Long commentId, Long userId, Long blogId) {
        delete("id = ?1 and user.id = ?2 and blog.id = ?3", commentId, userId, blogId);
    }
}
