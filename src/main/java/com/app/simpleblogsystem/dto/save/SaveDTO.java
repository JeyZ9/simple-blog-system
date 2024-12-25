package com.app.simpleblogsystem.dto.save;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveDTO {
    private Long id;
    private Long userId;
    private Long blogId;
    private String blogTitle;
}
