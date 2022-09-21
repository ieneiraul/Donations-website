package com.dreamerproject.controller;

import com.dreamerproject.dto.DonationDTO;
import com.dreamerproject.dto.StoryRequestDTO;
import com.dreamerproject.service.StoryService;
import com.dreamerproject.dto.StoryDTO;
import com.dreamerproject.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping(path = "stories")
public class StoryController {

    private StoryService storyService;

    public StoryController(StoryService storyService)
    {
        this.storyService = storyService;
    }

    @GetMapping
    public List<StoryDTO> getStories() {
        return storyService.getStoriesDTO();
    }
    @GetMapping("user/{username}")
    public List<StoryDTO> getStoriesForUser(@PathVariable(name = "username") String username) {

        return storyService.getStoriesDTOForUser(username);
    }

    @GetMapping("/{id}")
    public StoryDTO getStory(@PathVariable(name = "id") Long id) {
        return storyService.getStoryDTO(id);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('STUDENT')")
    public StoryDTO saveStory(@RequestBody StoryRequestDTO storyRequestDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        StoryDTO storyDTO = storyService.saveStory(storyRequestDTO, username);
        return storyDTO;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'STUDENT')")
    public String deleteStory(@PathVariable(name = "id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return storyService.deleteStory(id, username);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'STUDENT')")
    public String updateStory(@PathVariable(name = "id") Long id, @RequestBody StoryRequestDTO storyRequestDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return storyService.updateStory(id, username, storyRequestDTO);
    }

    @PostMapping("image/{id}")
    public String uploadImageStory(@PathVariable(name = "id") Long id,
                                     @RequestParam(name="image") MultipartFile image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try {
            return storyService.uploadImageProfile(id, username, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("donations/save")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String saveDonation(@RequestBody DonationDTO donationDTO) {
        return storyService.saveDonation(donationDTO);
    }

}
