package com.example.verifyrepresentative.repository;

import com.example.verifyrepresentative.model.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentativeRepo extends JpaRepository<Representative, Long> {
    Representative findByPhoneNumber(String phoneNumber);
}
