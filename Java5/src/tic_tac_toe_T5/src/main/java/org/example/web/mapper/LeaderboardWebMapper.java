package org.example.web.mapper;

import org.example.domain.model.LeaderboardEntry;
import org.example.domain.model.User;
import org.example.web.model.LeaderboardResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LeaderboardWebMapper {

    public LeaderboardResponse toResponse(LeaderboardEntry entry, User user) {
        if (entry == null) return null;
        LeaderboardResponse response = new LeaderboardResponse();
        response.setUserId(entry.getUserId());
        response.setWinRatio(entry.getWinRatio());
        response.setLogin(user != null ? user.getLogin() : null);
        return response;
    }

    public List<LeaderboardResponse> toResponseList(List<LeaderboardEntry> entries, List<User> users) {
        if (entries == null || entries.isEmpty()) return List.of();
        
        // Create a map of userId to user for quick lookup
        var userMap = users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        
        return entries.stream()
                .map(entry -> toResponse(entry, userMap.get(entry.getUserId())))
                .collect(Collectors.toList());
    }
}