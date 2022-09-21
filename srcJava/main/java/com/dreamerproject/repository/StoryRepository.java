package com.dreamerproject.repository;

import com.dreamerproject.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    Optional<Story> findById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Story as a " +
            "SET a.imageUrl=:image WHERE a.id =:id")
    int updateProfileStory(@Param("id") Long id, @Param("image") String imageUrl);

    @Transactional
    @Modifying
    @Query("UPDATE Story as a " +
            "SET a.amountGiven=:amount WHERE a.id =:id")
    int updateAmountGivenStory(@Param("id") Long id, @Param("amount") Double amountGiven);

    @Transactional
    @Modifying
    @Query("UPDATE Story as s " +
            "SET s.text =:text, s.amountNeeded=:aneeded, s.amountGiven=:agiven," +
            "s.startDate=:start, s.endDate=:end, s.imageUrl=:img," +
            "s.videoUrl=:video WHERE s.id =:id")
    int updateStory(@Param("id") Long id, @Param("text") String text, @Param("aneeded") Double amountNeeded,
                    @Param("agiven") Double amountGiven, @Param("start") LocalDateTime startDate,
                    @Param("end") LocalDateTime endDate, @Param("img") String imageUrl,
                    @Param("video") String videoUrl);
}
