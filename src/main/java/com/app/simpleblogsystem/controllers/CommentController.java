package com.app.simpleblogsystem.controllers;

import com.app.simpleblogsystem.config.ApiResponse;
import com.app.simpleblogsystem.config.MessageStrings;
import com.app.simpleblogsystem.dto.CommentDTO;
import com.app.simpleblogsystem.models.Comment;
import com.app.simpleblogsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/blogs/{blogId}/comments")
    public ResponseEntity<ApiResponse<Object>> getAllCommentByBlogId(@PathVariable Long blogId) {
        List<CommentDTO> comments = commentService.getCommentByBlogId(blogId);
        ApiResponse<Object> response = new ApiResponse<>(200, MessageStrings.SUCCESS_GET, comments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/blogs/{blogId}/comments")
    public ResponseEntity<ApiResponse<Object>> addComment(
            @RequestParam String comment,
            @RequestParam Long userId,
            @PathVariable(value = "blogId") Long blogId
    ) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment(comment);
        CommentDTO createComment = commentService.createComment(commentDTO, userId, blogId);
        ApiResponse<Object> response = new ApiResponse<>(201, MessageStrings.SUCCESS_CREATE, createComment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Object>> editComment(
            @PathVariable Long commentId,
            @RequestParam String comment,
            @RequestParam Long userId,
            @PathVariable(value = "blogId") Long blogId
    ) throws IOException {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment(comment);
        CommentDTO updateComment = commentService.updateComment(commentId, userId, blogId, commentDTO);
        ApiResponse<Object> response = new ApiResponse(201, MessageStrings.SUCCESS_UPDATE, updateComment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

 /*
 {
    "status": 200,
    "message": "ดึงข้อมูลสำเร็จ",
    "data": [
        {
            "id": 2,
            "comment": "Test Comment 2",
            "createdDate": "2568-01-06",
            "user": {
                "id": 1,
                "username": "test"
            },
            <-- เพิ่ม field ลบข้อมูล -->
            is_deleted: "true"
        }
    ],
    "timestamp": "2025-01-06T21:06:48.604176700"
}
*/

    @DeleteMapping("/blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> removeComment(@PathVariable(value = "blogId") Long blogId, @PathVariable Long commentId) throws IOException {
        Comment comment = commentService.deleteComment(blogId, commentId);
        ApiResponse<Void> response = new ApiResponse(204, MessageStrings.SUCCESS_DELETE, comment);
        return new ResponseEntity<> (response, HttpStatus.NO_CONTENT);
    }
}
