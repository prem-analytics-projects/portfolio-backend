
package com.prem.portfolio.portfolio.service.impl;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.request.ContactMessageRequest;
import com.prem.portfolio.portfolio.dto.request.ContactMessageUpdateRequest;
import com.prem.portfolio.portfolio.dto.response.ContactMessageResponse;
import com.prem.portfolio.portfolio.entity.ContactMessage;
import com.prem.portfolio.portfolio.mapper.ContactMessageMapper;
import com.prem.portfolio.portfolio.repository.ContactMessageRepository;
import com.prem.portfolio.portfolio.service.ContactMessageService;

@Service
@Transactional
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;

    public ContactMessageServiceImpl(
            ContactMessageRepository contactMessageRepository,
            ContactMessageMapper contactMessageMapper) {

        this.contactMessageRepository = contactMessageRepository;
        this.contactMessageMapper = contactMessageMapper;
    }

    @Override
    public ContactMessageResponse submitMessage(ContactMessageRequest request) {

        ContactMessage contactMessage = contactMessageMapper.toEntity(request);

        contactMessage.setMessageStatus("NEW");

        ContactMessage savedMessage =
                contactMessageRepository.save(contactMessage);

        return contactMessageMapper.toResponse(savedMessage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactMessageResponse> getMessages(String messageStatus) {

        String normalizedStatus = null;

        if (messageStatus != null && !messageStatus.isBlank()) {

            normalizedStatus = messageStatus.trim().toUpperCase();

            if (!List.of("NEW", "READ", "REPLIED", "ARCHIVED", "SPAM")
                    .contains(normalizedStatus)) {
                throw new IllegalArgumentException("Invalid message status");
            }
        }

        return contactMessageRepository.findMessages(normalizedStatus)
                .stream()
                .map(contactMessageMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ContactMessageResponse getMessageById(Long messageId) {

        ContactMessage contactMessage = contactMessageRepository
                .findByMessageId(messageId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Contact message not found"));

        return contactMessageMapper.toResponse(contactMessage);
    }

    @Override
    public ContactMessageResponse updateMessage(
            Long messageId,
            ContactMessageUpdateRequest request) {

        ContactMessage contactMessage = contactMessageRepository
                .findByMessageId(messageId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Contact message not found"));

        String status = request.getMessageStatus().trim().toUpperCase();

        if (!List.of("NEW", "READ", "REPLIED", "ARCHIVED", "SPAM")
                .contains(status)) {
            throw new IllegalArgumentException("Invalid message status");
        }

        contactMessage.setMessageStatus(status);
        contactMessage.setAdminNotes(request.getAdminNotes());
        contactMessage.setHandledAt(OffsetDateTime.now());

        ContactMessage updatedMessage =
                contactMessageRepository.save(contactMessage);

        return contactMessageMapper.toResponse(updatedMessage);
    }
}
