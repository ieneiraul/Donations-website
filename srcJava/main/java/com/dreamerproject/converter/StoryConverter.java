package com.dreamerproject.converter;

import com.dreamerproject.dto.StoryDTO;
import com.dreamerproject.dto.StoryRequestDTO;
import com.dreamerproject.dto.UserDTO;
import com.dreamerproject.model.Story;
import com.dreamerproject.model.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoryConverter {
    public StoryDTO entityToDto(Story story) {
        StoryDTO storyDTO = new StoryDTO();
        storyDTO.setId(story.getId());
        storyDTO.setAmountGiven(story.getAmountGiven());
        storyDTO.setAmountNeeded(story.getAmountNeeded());
        storyDTO.setAuthorUsername(story.getAuthor().getUsername());
        storyDTO.setStartDate(story.getStartDate());
        storyDTO.setEndDate(story.getEndDate());
        storyDTO.setText(story.getText());
        storyDTO.setImageUrl(story.getImageUrl());
        storyDTO.setVideoUrl(story.getVideoUrl());
        return storyDTO;
    }

    public List<StoryDTO> entityToDto(List<Story> storyEntities) {
        return storyEntities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Story dtoToEntity(StoryDTO storyDTO, User user) {
        Story story = new Story();
        story.setId(storyDTO.getId());
        story.setAmountGiven(storyDTO.getAmountGiven());
        story.setAmountNeeded(storyDTO.getAmountNeeded());
        story.setAuthor(user);
        story.setStartDate(storyDTO.getStartDate());
        story.setEndDate(storyDTO.getEndDate());
        story.setText(storyDTO.getText());
        story.setImageUrl(storyDTO.getImageUrl());
        story.setVideoUrl(storyDTO.getVideoUrl());
        return story;
    }

    public Story requestDTOToEntity(StoryRequestDTO storyDTO, User user) {
        Story story = new Story();
        story.setAmountGiven(storyDTO.getAmountGiven());
        story.setAmountNeeded(storyDTO.getAmountNeeded());
        story.setAuthor(user);
        if(storyDTO.getStartDate()!=null)
            story.setStartDate(storyDTO.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        if(storyDTO.getEndDate()!=null)
            story.setEndDate(storyDTO.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        story.setText(storyDTO.getText());
        story.setImageUrl(storyDTO.getImageUrl());
        story.setVideoUrl(storyDTO.getVideoUrl());
        return story;
    }

}
