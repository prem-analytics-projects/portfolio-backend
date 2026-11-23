
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.portfolio.dto.request.ContactMessageRequest;
import com.prem.portfolio.portfolio.dto.request.ContactMessageUpdateRequest;
import com.prem.portfolio.portfolio.dto.response.ContactMessageResponse;
import com.prem.portfolio.portfolio.service.ContactMessageService;

import jakarta.validation.Valid;

@RestController
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    public ContactMessageController(
            ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    // Public endpoint: visitors can submit messages
    @PostMapping("/api/v1/public/contact-messages")
    public ResponseEntity<ContactMessageResponse> submitMessage(
            @Valid @RequestBody ContactMessageRequest request) {

        ContactMessageResponse response =
                contactMessageService.submitMessage(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Admin endpoint: list and optionally filter messages
    @GetMapping("/api/v1/admin/contact-messages")
    public ResponseEntity<List<ContactMessageResponse>> getMessages(
            @RequestParam(required = false) String messageStatus) {

        return ResponseEntity.ok(
                contactMessageService.getMessages(messageStatus));
    }

    // Admin endpoint: get one message
    @GetMapping("/api/v1/admin/contact-messages/{messageId}")
    public ResponseEntity<ContactMessageResponse> getMessageById(
            @PathVariable Long messageId) {

        return ResponseEntity.ok(
                contactMessageService.getMessageById(messageId));
    }

    // Admin endpoint: update status and internal notes
    @PutMapping("/api/v1/admin/contact-messages/{messageId}")
    public ResponseEntity<ContactMessageResponse> updateMessage(
            @PathVariable Long messageId,
            @Valid @RequestBody ContactMessageUpdateRequest request) {

        return ResponseEntity.ok(
                contactMessageService.updateMessage(messageId, request));
    }
}
