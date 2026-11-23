
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.request.WebsiteSettingRequest;
import com.prem.portfolio.portfolio.dto.response.WebsiteSettingResponse;
import com.prem.portfolio.portfolio.entity.WebsiteSetting;
import com.prem.portfolio.portfolio.mapper.WebsiteSettingMapper;
import com.prem.portfolio.portfolio.repository.WebsiteSettingRepository;
import com.prem.portfolio.portfolio.service.WebsiteSettingService;

@Service
@Transactional
public class WebsiteSettingServiceImpl implements WebsiteSettingService {

    private final WebsiteSettingRepository settingRepository;
    private final WebsiteSettingMapper settingMapper;

    public WebsiteSettingServiceImpl(
            WebsiteSettingRepository settingRepository,
            WebsiteSettingMapper settingMapper) {
        this.settingRepository = settingRepository;
        this.settingMapper = settingMapper;
    }

    @Override
    public WebsiteSettingResponse createSetting(WebsiteSettingRequest request) {

        validateDuplicateKey(request.getSettingKey(), null);

        WebsiteSetting setting = settingMapper.toEntity(request);

        WebsiteSetting savedSetting = settingRepository.save(setting);

        return settingMapper.toResponse(savedSetting);
    }

    @Override
    public WebsiteSettingResponse updateSetting(
            Long settingId,
            WebsiteSettingRequest request) {

        WebsiteSetting setting = settingRepository.findById(settingId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Website setting not found"));

        validateDuplicateKey(request.getSettingKey(), settingId);

        settingMapper.updateEntity(setting, request);

        WebsiteSetting updatedSetting = settingRepository.save(setting);

        return settingMapper.toResponse(updatedSetting);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebsiteSettingResponse> getAllSettings() {

        return settingRepository.findAllByOrderBySettingKeyAsc()
                .stream()
                .map(settingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebsiteSettingResponse> getPublicSettings() {

        return settingRepository.findByIsPublicTrueOrderBySettingKeyAsc()
                .stream()
                .map(settingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WebsiteSettingResponse getSettingById(Long settingId) {

        WebsiteSetting setting = settingRepository.findById(settingId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Website setting not found"));

        return settingMapper.toResponse(setting);
    }

    @Override
    public void deleteSetting(Long settingId) {

        WebsiteSetting setting = settingRepository.findById(settingId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Website setting not found"));

        settingRepository.delete(setting);
    }

    private void validateDuplicateKey(String settingKey, Long currentSettingId) {

        boolean duplicateExists = settingRepository.findAll()
                .stream()
                .anyMatch(setting ->
                        setting.getSettingKey().equalsIgnoreCase(settingKey.trim())
                        && !setting.getSettingId().equals(currentSettingId));

        if (duplicateExists) {
            throw new IllegalArgumentException(
                    "A website setting with this key already exists");
        }
    }
}
