
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.request.ContactDetailRequest;
import com.prem.portfolio.portfolio.dto.response.ContactDetailResponse;
import com.prem.portfolio.portfolio.entity.ContactDetail;
import com.prem.portfolio.portfolio.mapper.ContactDetailMapper;
import com.prem.portfolio.portfolio.repository.ContactDetailRepository;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.ContactDetailService;

@Service
@Transactional
public class ContactDetailServiceImpl implements ContactDetailService {

    private final ContactDetailRepository contactDetailRepository;
    private final ProfileRepository profileRepository;
    private final ContactDetailMapper contactDetailMapper;

    public ContactDetailServiceImpl(
            ContactDetailRepository contactDetailRepository,
            ProfileRepository profileRepository,
            ContactDetailMapper contactDetailMapper) {

        this.contactDetailRepository = contactDetailRepository;
        this.profileRepository = profileRepository;
        this.contactDetailMapper = contactDetailMapper;
    }

    @Override
    public ContactDetailResponse createContactDetail(
            ContactDetailRequest request) {

        validateProfile(request.getProfileId());

        ContactDetail contactDetail =
                contactDetailMapper.toEntity(request);

        ContactDetail savedContactDetail =
                contactDetailRepository.save(contactDetail);

        return contactDetailMapper.toResponse(savedContactDetail);
    }

    @Override
    public ContactDetailResponse updateContactDetail(
            Long contactId,
            ContactDetailRequest request) {

        ContactDetail contactDetail =
                contactDetailRepository.findByContactIdAndActiveTrue(contactId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Active contact detail not found with ID: " + contactId
                        ));

        validateProfile(request.getProfileId());

        contactDetailMapper.updateEntity(contactDetail, request);

        ContactDetail updatedContactDetail =
                contactDetailRepository.save(contactDetail);

        return contactDetailMapper.toResponse(updatedContactDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDetailResponse getContactDetailById(Long contactId) {

        ContactDetail contactDetail =
                contactDetailRepository.findByContactIdAndActiveTrue(contactId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Active contact detail not found with ID: " + contactId
                        ));

        return contactDetailMapper.toResponse(contactDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDetailResponse> getContactDetails(
            Long profileId,
            Boolean active) {

        validateProfile(profileId);

        return contactDetailRepository
                .findContactDetails(profileId, active)
                .stream()
                .map(contactDetailMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteContactDetail(Long contactId) {

        ContactDetail contactDetail =
                contactDetailRepository.findByContactIdAndActiveTrue(contactId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Active contact detail not found with ID: " + contactId
                        ));

        contactDetail.setActive(false);

        contactDetailRepository.save(contactDetail);
    }

    private void validateProfile(Long profileId) {

        profileRepository.findByProfileIdAndActiveTrue(profileId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active profile not found with ID: " + profileId
                ));
    }
}
