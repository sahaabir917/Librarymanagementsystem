//this repository is responsible for data access


package com.infinitycodehubltd.librarymanagement.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT s FROM Member s WHERE s.email = ?1")
    Optional<Member> findMemberByEmail(String email);

}
