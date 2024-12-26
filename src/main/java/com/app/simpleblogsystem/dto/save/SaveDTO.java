package com.app.simpleblogsystem.dto.save;

import com.app.simpleblogsystem.dto.BlogDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveDTO {
    private Long id;
    private Long userId;
    private List<BlogDTO> blogDTOs;
//    private String blogTitle;
}
