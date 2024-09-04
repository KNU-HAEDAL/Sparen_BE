package org.haedal.zzansuni.admin.controller;

import lombok.Builder;
import org.haedal.zzansuni.user.domain.UserModel;

import java.time.LocalDateTime;
import java.util.List;

public class AdminRes {
    @Builder
    public record ManagerAndAdmin(
            Long id,
            String email,
            String name,
            String role,
            LocalDateTime createdAt
    ) {
        public static ManagerAndAdmin from(UserModel.Admin managerAndAdmin) {
            return ManagerAndAdmin.builder()
                    .id(managerAndAdmin.id())
                    .email(managerAndAdmin.email())
                    .name(managerAndAdmin.nickname())
                    .role(managerAndAdmin.role().name())
                    .createdAt(managerAndAdmin.createdAt())
                    .build();
        }
        public static List<ManagerAndAdmin> from(List<UserModel.Admin> managerAndAdmins) {
            return managerAndAdmins.stream()
                    .map(ManagerAndAdmin::from)
                    .toList();
        }
    }

}
