
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.ContactMessageRequest;
import com.prem.portfolio.portfolio.dto.response.ContactMessageResponse;
import com.prem.portfolio.portfolio.entity.ContactMessage;

@Component
public class ContactMessageMapper {

    public ContactMessage toEntity(ContactMessageRequest request) {

        ContactMessage contactMessage = new ContactMessage();

        contactMessage.setSenderName(request.getSenderName().trim());
        contactMessage.setSenderEmail(request.getSenderEmail().trim());
        contactMessage.setSubject(
                request.getSubject() == null
                        ? null
                        : request.getSubject().trim()
        );
        contactMessage.setMessage(request.getMessage().trim());
        contactMessage.setMessageStatus("NEW");

        return contactMessage;
    }

    public ContactMessageResponse toResponse(ContactMessage contactMessage) {

        ContactMessageResponse response = new ContactMessageResponse();

        response.setMessageId(contactMessage.getMessageId());
        response.setSenderName(contactMessage.getSenderName());
        response.setSenderEmail(contactMessage.getSenderEmail());
        response.setSubject(contactMessage.getSubject());
        response.setMessage(contactMessage.getMessage());
        response.setMessageStatus(contactMessage.getMessageStatus());
        response.setReceivedAt(contactMessage.getReceivedAt());
        response.setHandledBy(contactMessage.getHandledBy());
        response.setHandledAt(contactMessage.getHandledAt());
        response.setAdminNotes(contactMessage.getAdminNotes());

        return response;
    }
}
