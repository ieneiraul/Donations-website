package com.dreamerproject.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dreamerproject.converter.DonationConverter;
import com.dreamerproject.converter.StoryConverter;
import com.dreamerproject.dto.DonationDTO;
import com.dreamerproject.dto.StoryDTO;
import com.dreamerproject.dto.StoryRequestDTO;
import com.dreamerproject.model.Donation;
import com.dreamerproject.model.Role;
import com.dreamerproject.model.Story;
import com.dreamerproject.model.User;
import com.dreamerproject.repository.DonationRepository;
import com.dreamerproject.repository.StoryRepository;
import com.dreamerproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class StoryService {

    private UserRepository userRepository;
    private StoryRepository storyRepository;
    private DonationRepository donationRepository;
    private StoryConverter storyConverter;
    private DonationConverter donationConverter;
    private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dtb67cyxp",
            "api_key", "463459147168639",
            "api_secret", "mwmzpTQwtF2LRqM4x2I9mW4FhpI",
            "secure", true));


    public StoryService(UserRepository userRepository, StoryRepository storyRepository, StoryConverter storyConverter, DonationRepository donationRepository, DonationConverter donationConverter)
    {
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
        this.storyConverter = storyConverter;
        this.donationRepository = donationRepository;
        this.donationConverter = donationConverter;
    }

    public List<Story> getStories() {
        return storyRepository.findAll();
    }

    public StoryDTO saveStory(StoryRequestDTO storyDTO, String username) {
        log.debug("saveStory method accessed");

        Optional<User> user = userRepository.getUserByUserName(username);
        Story story = storyConverter.requestDTOToEntity(storyDTO, user.get());
        storyRepository.save(story);

        return storyConverter.entityToDto(story);
    }

    public List<StoryDTO> getStoriesDTO() {
        return storyConverter.entityToDto(storyRepository.findAll());
    }

    public StoryDTO getStoryDTO(Long id) {
        return storyConverter.entityToDto(storyRepository.findById(id).get());
    }

    public String deleteStory(Long id, String username) {
        log.debug("deleteStory method accessed");

        User user = userRepository.getUserByUserName(username).get();
        Optional<Story> story = storyRepository.findById(id);
        if(story.isPresent()) {
            if(story.get().getAuthor().getUsername().equals(username) || user.getRole().equals(Role.ADMIN)
                    || user.getRole().equals(Role.MANAGER)) {
                storyRepository.delete(story.get());
            }
            else {
                return "Nu ai dreptul de a sterge aceasta postare!";
            }
        }
        else {
            return "nu exista o postare cu acest id!";
        }
        return "Ai sters postarea cu succes";
    }

    public String updateStory(Long id, String username, StoryRequestDTO storyRequestDTO) {
        log.debug("updateStory method accessed");

        User user = userRepository.getUserByUserName(username).get();
        Optional<Story> story = storyRepository.findById(id);
        if(story.isPresent()) {
            Story storyFromDTO = storyConverter.requestDTOToEntity(storyRequestDTO, story.get().getAuthor());
            if(story.get().getAuthor().getUsername().equals(username) || user.getRole().equals(Role.ADMIN)
                    || user.getRole().equals(Role.MANAGER)) {
                storyRepository.updateStory(id, storyFromDTO.getText(), storyFromDTO.getAmountNeeded(),
                        storyFromDTO.getAmountGiven(),storyFromDTO.getStartDate(),
                        storyFromDTO.getEndDate(), storyRequestDTO.getImageUrl(),storyRequestDTO.getVideoUrl());
            }
            else {
                return "Nu ai dreptul de a sterge aceasta postare!";
            }
        }
        else {
            return "nu exista o postare cu acest id!";
        }

        return "Ai modificat postarea cu succes";
    }

    public String uploadImageProfile(Long id, String username, MultipartFile image) throws IOException {
        File file =new ClassPathResource(
                "data/profil.jpeg").getFile();
        if(!image.isEmpty()) {
            if(!image.getOriginalFilename().endsWith(".jpg") &&!image.getOriginalFilename().endsWith(".jpeg") &&
                    !image.getOriginalFilename().endsWith(".png")) {
                return "Trebuie sa incarcati o poza!";
            }
            User userRequester = userRepository.getUserByUserName(username).get();
            Optional<Story> story = storyRepository.findById(id);
            if(!story.isPresent()) {
                return "nu exista o poveste cu id ul dat!";
            }
            if(story.get().getAuthor().getId()==userRequester.getId()) {
                image.transferTo(file);
                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                        "folder", "dreamsForFuture"));
                String url = (String) uploadResult.get("url");
                storyRepository.updateProfileStory(id,url);
                return "ai uploadat poza cu succes!";
            }
            else {
                return "nu ai dreptul sa uploadezi poza!";
            }
        }
        return "eroare la incarcare";
    }

    public String saveDonation(DonationDTO donationDTO) {
        log.debug("saveDonation method accessed");
        Optional<User> user = userRepository.findById(donationDTO.getDonatorID());
        if(!user.isPresent()) {
            log.debug("Nu exista un user cu id ul dat!");
            return "Nu exista un user cu id ul dat!";
        }
        Optional<Story> story = storyRepository.findById(donationDTO.getStoryID());
        if(!story.isPresent()) {
            log.debug("Nu exista o poveste cu id ul dat!");
            return "Nu exista o poveste cu id ul dat!";
        }
        Donation donation = donationConverter.dtoToEntity(donationDTO,user.get(), story.get());
        donationRepository.save(donation);
        log.debug("Se modifica suma data pentru poveste in:"+story.get().getAmountGiven()+donationDTO.getAmount());
        storyRepository.updateAmountGivenStory(donationDTO.getStoryID(),
                story.get().getAmountGiven()+donationDTO.getAmount());

        log.debug("donatia a fost salvata cu succes!");
        return "donatia a fost salvata cu succes!";
    }

    public List<StoryDTO> getStoriesDTOForUser(String username) {
        List<StoryDTO> stories = getStoriesDTO();
        List<StoryDTO> storyDTOList = new ArrayList<>();
        for (StoryDTO story: stories) {
            if(story.getAuthorUsername().equals(username)) storyDTOList.add(story);
        }
        return storyDTOList;
    }
}
