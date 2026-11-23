
package com.prem.portfolio.portfolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prem.portfolio.portfolio.entity.WebsiteSetting;

public interface WebsiteSettingRepository
        extends JpaRepository<WebsiteSetting, Long> {

    Optional<WebsiteSetting> findBySettingKey(String settingKey);

    boolean existsBySettingKey(String settingKey);

    List<WebsiteSetting> findAllByOrderBySettingKeyAsc();

    List<WebsiteSetting> findByIsPublicTrueOrderBySettingKeyAsc();
}
