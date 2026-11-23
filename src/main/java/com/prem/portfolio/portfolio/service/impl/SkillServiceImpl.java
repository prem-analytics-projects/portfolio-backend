
package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.portfolio.dto.request.SkillRequest;
import com.prem.portfolio.portfolio.dto.response.SkillResponse;
import com.prem.portfolio.portfolio.entity.Skill;
import com.prem.portfolio.portfolio.entity.SkillCategory;
import com.prem.portfolio.portfolio.entity.Profile;
import com.prem.portfolio.common.exception.ResourceNotFoundException;
import com.prem.portfolio.portfolio.mapper.SkillMapper;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.repository.SkillCategoryRepository;
import com.prem.portfolio.portfolio.repository.SkillRepository;
import com.prem.portfolio.portfolio.service.SkillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillCategoryRepository categoryRepository;
    private final ProfileRepository profileRepository;
    private final SkillMapper skillMapper;

    public SkillServiceImpl(
            SkillRepository skillRepository,
            SkillCategoryRepository categoryRepository,
            ProfileRepository profileRepository,
            SkillMapper skillMapper
    ) {
        this.skillRepository = skillRepository;
        this.categoryRepository = categoryRepository;
        this.profileRepository = profileRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public SkillResponse createSkill(SkillRequest request) {

        validateProfile(request.getProfileId());
        validateCategory(request.getSkillCategoryId());

        String skillName = request.getSkillName().trim();

        if (skillRepository.existsByProfileIdAndSkillNameIgnoreCaseAndActiveTrue(
                request.getProfileId(), skillName)) {
            throw new IllegalArgumentException(
                    "This skill already exists for the selected profile"
            );
        }

        request.setSkillName(skillName);

        Skill skill = skillMapper.toEntity(request);
        Skill savedSkill = skillRepository.save(skill);

        return skillMapper.toResponse(savedSkill);
    }

    @Override
    public SkillResponse updateSkill(Long skillId, SkillRequest request) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill not found with ID: " + skillId
                ));

        validateProfile(request.getProfileId());
        validateCategory(request.getSkillCategoryId());

        String skillName = request.getSkillName().trim();

        boolean duplicateExists = skillRepository
                .existsByProfileIdAndSkillNameIgnoreCaseAndActiveTrue(
                        request.getProfileId(), skillName
                );

        if (duplicateExists
                && (!skill.getProfileId().equals(request.getProfileId())
                || !skill.getSkillName().equalsIgnoreCase(skillName))) {
            throw new IllegalArgumentException(
                    "This skill already exists for the selected profile"
            );
        }

        request.setSkillName(skillName);

        skillMapper.updateEntity(skill, request);

        Skill updatedSkill = skillRepository.save(skill);

        return skillMapper.toResponse(updatedSkill);
    }

    @Override
    @Transactional(readOnly = true)
    public SkillResponse getSkillById(Long skillId) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill not found with ID: " + skillId
                ));

        return skillMapper.toResponse(skill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponse> getAllSkills(
            Long profileId,
            Long skillCategoryId,
            Boolean active
    ) {
        return skillRepository.findSkills(profileId, skillCategoryId, active)
                .stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteSkill(Long skillId) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill not found with ID: " + skillId
                ));

        skill.setActive(false);

        skillRepository.save(skill);
    }

    private void validateProfile(Long profileId) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Profile not found with ID: " + profileId
                ));

        if (!Boolean.TRUE.equals(profile.getActive())) {
            throw new IllegalArgumentException(
                    "Cannot assign skills to an inactive profile"
            );
        }
    }

    private void validateCategory(Long skillCategoryId) {

        if (skillCategoryId == null) {
            return;
        }

        SkillCategory category = categoryRepository.findById(skillCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill category not found with ID: " + skillCategoryId
                ));

        if (!Boolean.TRUE.equals(category.getActive())) {
            throw new IllegalArgumentException(
                    "Cannot assign a skill to an inactive category"
            );
        }
    }
}
