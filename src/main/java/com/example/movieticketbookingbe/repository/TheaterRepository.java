package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Theater;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    @EntityGraph(attributePaths = {"theaterOwner", "theaterOwner.user"})
    List<Theater> findByIsActiveTrue();

    List<Theater> findByCity(String city);

    List<Theater> findByCountry(String country);

    Optional<Theater> findByNameAndCity(String name, String city);

    boolean existsByNameAndCity(String name, String city);

    List<Theater> findByNameContainingIgnoreCaseAndCityAndState(String name, String city, String state);

    boolean existsByAddressAndTheaterOwnerId(String address, Long theaterOwnerId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Theater> findByTheaterOwnerId(Long theaterOwnerId);
    long countByIsActiveTrue();
    @Query("SELECT COUNT(t.id) FROM Theater t WHERE t.theaterOwnerId = :ownerId")
    Long getTotalTheatersByOwner(Long ownerId);

}