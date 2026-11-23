
package com.prem.portfolio.portfolio.mapper;

import com.prem.portfolio.portfolio.dto.request.SkillRequest;
import com.prem.portfolio.portfolio.dto.response.SkillResponse;
import com.prem.portfolio.portfolio.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public Skill toEntity(SkillRequest request) {
        Skill skill = new Skill();

        skill.setProfileId(request.getProfileId());
        skill.setSkillCategoryId(request.getSkillCategoryId());
        skill.setSkillName(request.getSkillName());
        skill.setProficiencyLevel(request.getProficiencyLevel());
        skill.setYearsExperience(request.getYearsExperience());
        skill.setDisplayOrder(request.getDisplayOrder());
        skill.setActive(request.getActive());

        return skill;
    }

    public void updateEntity(Skill skill, SkillRequest request) {
        skill.setProfileId(request.getProfileId());
        skill.setSkillCategoryId(request.getSkillCategoryId());
        skill.setSkillName(request.getSkillName());
        skill.setProficiencyLevel(request.getProficiencyLevel());
        skill.setYearsExperience(request.getYearsExperience());
        skill.setDisplayOrder(request.getDisplayOrder());
        skill.setActive(request.getActive());
    }

    public SkillResponse toResponse(Skill skill) {
        SkillResponse response = new SkillResponse();

        response.setSkillId(skill.getSkillId());
        response.setProfileId(skill.getProfileId());
        response.setSkillCategoryId(skill.getSkillCategoryId());
        response.setSkillName(skill.getSkillName());
        response.setProficiencyLevel(skill.getProficiencyLevel());
        response.setYearsExperience(skill.getYearsExperience());
        response.setDisplayOrder(skill.getDisplayOrder());
        response.setActive(skill.getActive());
        response.setCreatedAt(skill.getCreatedAt());
        response.setUpdatedAt(skill.getUpdatedAt());

        return response;
    }
}
