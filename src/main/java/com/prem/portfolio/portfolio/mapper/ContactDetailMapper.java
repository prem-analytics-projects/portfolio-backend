
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.ContactDetailRequest;
import com.prem.portfolio.portfolio.dto.response.ContactDetailResponse;
import com.prem.portfolio.portfolio.entity.ContactDetail;

@Component
public class ContactDetailMapper {

    public ContactDetail toEntity(ContactDetailRequest request) {

        ContactDetail contactDetail = new ContactDetail();

        contactDetail.setProfileId(request.getProfileId());
        contactDetail.setContactType(request.getContactType().trim());
        contactDetail.setContactValue(request.getContactValue().trim());
        contactDetail.setDisplayOrder(request.getDisplayOrder());
        contactDetail.setActive(request.getActive());

        return contactDetail;
    }

    public void updateEntity(
            ContactDetail contactDetail,
            ContactDetailRequest request) {

        contactDetail.setProfileId(request.getProfileId());
        contactDetail.setContactType(request.getContactType().trim());
        contactDetail.setContactValue(request.getContactValue().trim());
        contactDetail.setDisplayOrder(request.getDisplayOrder());
        contactDetail.setActive(request.getActive());
    }

    public ContactDetailResponse toResponse(ContactDetail contactDetail) {

        ContactDetailResponse response = new ContactDetailResponse();

        response.setContactId(contactDetail.getContactId());
        response.setProfileId(contactDetail.getProfileId());
        response.setContactType(contactDetail.getContactType());
        response.setContactValue(contactDetail.getContactValue());
        response.setDisplayOrder(contactDetail.getDisplayOrder());
        response.setActive(contactDetail.getActive());
        response.setCreatedAt(contactDetail.getCreatedAt());
        response.setUpdatedAt(contactDetail.getUpdatedAt());
        response.setCreatedBy(contactDetail.getCreatedBy());
        response.setUpdatedBy(contactDetail.getUpdatedBy());

        return response;
    }
}
