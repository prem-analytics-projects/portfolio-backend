
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prem.portfolio.portfolio.dto.response.ContactDetailResponse;
import com.prem.portfolio.portfolio.dto.response.PublicContactDetailResponse;
import com.prem.portfolio.portfolio.service.ContactDetailService;

@RestController
@RequestMapping("/api/v1/public/contact-details")
public class PublicContactDetailController {

    private final ContactDetailService contactDetailService;

    public PublicContactDetailController(
            ContactDetailService contactDetailService) {
        this.contactDetailService = contactDetailService;
    }

    @GetMapping
    public ResponseEntity<List<PublicContactDetailResponse>> getPublicContactDetails(
            @RequestParam Long profileId) {

        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException(
                    "Profile ID must be a positive number");
        }

        List<ContactDetailResponse> contactDetails =
                contactDetailService.getContactDetails(profileId, true);

        List<PublicContactDetailResponse> response =
                contactDetails.stream()
                        .map(this::toPublicResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    private PublicContactDetailResponse toPublicResponse(
            ContactDetailResponse source) {

        PublicContactDetailResponse response =
                new PublicContactDetailResponse();

        response.setContactId(source.getContactId());
        response.setProfileId(source.getProfileId());
        response.setContactType(source.getContactType());
        response.setContactValue(source.getContactValue());
        response.setDisplayOrder(source.getDisplayOrder());

        return response;
    }
}
