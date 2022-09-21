package com.dreamerproject.converter;

import com.dreamerproject.dto.DonationDTO;
import com.dreamerproject.dto.StoryDTO;
import com.dreamerproject.model.Donation;
import com.dreamerproject.model.Story;
import com.dreamerproject.model.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DonationConverter {
    public DonationDTO entityToDto(Donation donation) {
        DonationDTO donationDTO = new DonationDTO();
        donationDTO.setDonatorID(donation.getDonator().getId());
        donationDTO.setStoryID(donation.getStory().getId());
        donationDTO.setAmount(donation.getAmount());
        donationDTO.setDate(Date.from(donation.getDate().atZone(ZoneId.systemDefault()).toInstant()));

        return donationDTO;
    }

    public List<DonationDTO> entityToDto(List<Donation> donationEntities) {
        return donationEntities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Donation dtoToEntity(DonationDTO donationDTO, User user, Story story) {
        Donation donation = new Donation();
        donation.setDonator(user);
        donation.setStory(story);
        donation.setAmount(donationDTO.getAmount());
        donation.setDate(donationDTO.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        return donation;
    }
}
