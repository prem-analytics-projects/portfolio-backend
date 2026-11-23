
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.ContactMessageRequest;
import com.prem.portfolio.portfolio.dto.request.ContactMessageUpdateRequest;
import com.prem.portfolio.portfolio.dto.response.ContactMessageResponse;

public interface ContactMessageService {

    // Public visitor operation
    ContactMessageResponse submitMessage(ContactMessageRequest request);

    // Administrative operations
    List<ContactMessageResponse> getMessages(String messageStatus);

    ContactMessageResponse getMessageById(Long messageId);

    ContactMessageResponse updateMessage(
            Long messageId,
            ContactMessageUpdateRequest request
    );
}
