package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.Blog;
import org.acme.Comment;
import org.acme.CommentDTO;
import org.acme.User;
import org.acme.repository.BlogRepository;
import org.acme.repository.CommentRepository;
import org.acme.repository.UserRepository;

import java.util.List;

@Path("/api/comment")
public class CommentController {

    @Inject
    CommentRepository commentRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    BlogRepository blogRepository;

    @GET
    @Path("/{userId}/blogs/{blogId}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@PathParam("blogId") Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        return Response.ok(comments).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/blogs/{blogId}/comments")
    @Transactional
    public Response addComment(@PathParam("userId") Long userId, @PathParam("blogId") Long blogId, Comment comment) {

        User user = null;
        Blog blog = null;
        System.out.println("user id :" + userId + " blog id : " + blogId);
    try {
         user = userRepository.findById(userId);
    } catch (Exception e){
        System.out.println("exception in 1" + e.getMessage());
    }

    try {
         blog = blogRepository.findByIdAndUserId(blogId, userId);
    } catch (Exception e){
        System.out.println("exception in 2" + e.getMessage());
    }

        if (user == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity("User  not found").build();
        }

        if (blog == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity(" Blog not found").build();
        }


        comment.setUser(user);
        comment.setBlog(blog);
        comment.setId(null);
        commentRepository.persist(comment);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());

        return Response.status(Response.Status.CREATED).entity(commentDTO).build();
    }


    @DELETE
    @Path("/{userId}/blogs/{blogId}/comments/{commentId}")
    @Transactional
    public Response deleteComment(@PathParam("userId") Long userId, @PathParam("blogId") Long blogId, @PathParam("commentId") Long commentId) {
        commentRepository.deleteByIdAndUserIdAndBlogId(commentId, userId, blogId);
        return Response.noContent().build();
    }
}
