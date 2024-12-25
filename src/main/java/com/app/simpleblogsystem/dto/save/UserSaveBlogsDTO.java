package com.app.simpleblogsystem.dto.save;

import com.app.simpleblogsystem.dto.BlogDTO;

import java.util.List;

public class UserSaveBlogsDTO {
    private Long id;
    private Long userId;
    private List<BlogDTO> blogDTOs;
}
