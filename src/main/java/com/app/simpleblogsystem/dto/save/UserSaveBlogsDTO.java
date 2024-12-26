package com.app.simpleblogsystem.dto.save;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSaveBlogsDTO {
    private Long id;
    private Long userId;
    private Long blogId;
}
