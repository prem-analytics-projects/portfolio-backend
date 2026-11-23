
package com.prem.portfolio.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.prem.portfolio.portfolio.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
