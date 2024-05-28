package org.haedal.zzansuni.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.haedal.zzansuni.global.security.Role;

@Getter
@AllArgsConstructor(staticName = "of")
public class JwtUser {
    private Long id;
    private Role role;
}
