
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.ContactDetailRequest;
import com.prem.portfolio.portfolio.dto.response.ContactDetailResponse;

public interface ContactDetailService {

    ContactDetailResponse createContactDetail(ContactDetailRequest request);

    ContactDetailResponse updateContactDetail(
            Long contactId,
            ContactDetailRequest request
    );

    ContactDetailResponse getContactDetailById(Long contactId);

    List<ContactDetailResponse> getContactDetails(
            Long profileId,
            Boolean active
    );

    void deleteContactDetail(Long contactId);
}
