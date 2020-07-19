package com.example.RedditClone.service;

import com.example.RedditClone.dto.SubredditDto;
import com.example.RedditClone.exceptions.SpringRedditException;
import com.example.RedditClone.mapper.SubredditMapper;
import com.example.RedditClone.model.Subreddit;
import com.example.RedditClone.repository.SubredditRepository;
import com.example.RedditClone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final AuthService authService;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit subreddit = subredditMapper.mapDtoToSubreddit(subredditDto);
        subreddit.setUser(authService.getCurrentUser());
        subredditRepository.save(subreddit);
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSuberdditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException(String.format("No subreddit with id %s found", id)));
        return subredditMapper.mapSuberdditToDto(subreddit);
    }
}
