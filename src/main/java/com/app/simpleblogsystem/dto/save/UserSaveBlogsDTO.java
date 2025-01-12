package com.app.simpleblogsystem.dto.save;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveBlogsDTO {
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @NotNull(message = "Blog ID cannot be null")
    private Set<Long> blogId;
}
