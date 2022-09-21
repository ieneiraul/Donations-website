package com.dreamerproject.repository;

import com.dreamerproject.model.BOOLEAN;
import com.dreamerproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> getUserByUserName(String userName);
    Optional<User> findByMail(String userName);
    List<User> getUserByEnabled(BOOLEAN state);

    @Transactional
    @Modifying
    @Query("UPDATE User as a " +
            "SET a.enabled ='TRUE' WHERE a.mail =:mail")
    int enableUser(@Param("mail") String email);

    @Transactional
    @Modifying
    @Query("UPDATE User as a " +
            "SET a.imageUrl=:image WHERE a.id =:id")
    int updateProfilePicture(@Param("id") Long id, @Param("image") String imageUrl);

    @Transactional
    @Modifying
    @Query("UPDATE User as u " +
            "SET u.password=:pass, u.iban=:iban, u.phone=:phone," +
            "u.imageUrl=:image WHERE u.id =:id")
    int updateUser(@Param("id") Long id, @Param("pass") String pass,  @Param("iban") String iban,
                   @Param("phone") String phone, @Param("image") String imageUrl);
}
