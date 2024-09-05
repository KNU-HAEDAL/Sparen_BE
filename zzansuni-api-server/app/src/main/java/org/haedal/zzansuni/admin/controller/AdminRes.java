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
        public static ManagerAndAdmin from(UserModel.Main userMain) {
            return ManagerAndAdmin.builder()
                    .id(userMain.id())
                    .email(userMain.email())
                    .name(userMain.nickname())
                    .role(userMain.role().name())
                    .createdAt(userMain.createdAt())
                    .build();
        }

        public static List<ManagerAndAdmin> from(List<UserModel.Main> userMainList) {
            return userMainList.stream()
                    .map(ManagerAndAdmin::from)
                    .toList();
        }
    }
}
