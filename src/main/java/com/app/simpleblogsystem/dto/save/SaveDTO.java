package com.app.simpleblogsystem.dto.save;

import com.app.simpleblogsystem.dto.BlogDTO;
import com.app.simpleblogsystem.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveDTO {
    private Long id;
    private UserDTO users;
    private Set<BlogDTO> blogs;
}
