
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.portfolio.dto.request.ContactDetailRequest;
import com.prem.portfolio.portfolio.dto.response.ContactDetailResponse;
import com.prem.portfolio.portfolio.service.ContactDetailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/contact-details")
public class ContactDetailController {

    private final ContactDetailService contactDetailService;

    public ContactDetailController(
            ContactDetailService contactDetailService) {
        this.contactDetailService = contactDetailService;
    }

    @PostMapping
    public ResponseEntity<ContactDetailResponse> createContactDetail(
            @Valid @RequestBody ContactDetailRequest request) {

        ContactDetailResponse response =
                contactDetailService.createContactDetail(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ContactDetailResponse>> getContactDetails(
            @RequestParam Long profileId,
            @RequestParam(required = false) Boolean active) {

        return ResponseEntity.ok(
                contactDetailService.getContactDetails(profileId, active)
        );
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<ContactDetailResponse> getContactDetailById(
            @PathVariable Long contactId) {

        return ResponseEntity.ok(
                contactDetailService.getContactDetailById(contactId)
        );
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<ContactDetailResponse> updateContactDetail(
            @PathVariable Long contactId,
            @Valid @RequestBody ContactDetailRequest request) {

        return ResponseEntity.ok(
                contactDetailService.updateContactDetail(contactId, request)
        );
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContactDetail(
            @PathVariable Long contactId) {

        contactDetailService.deleteContactDetail(contactId);

        return ResponseEntity.noContent().build();
    }
}
