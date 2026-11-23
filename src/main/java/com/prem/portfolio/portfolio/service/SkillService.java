
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.SkillRequest;
import com.prem.portfolio.portfolio.dto.response.SkillResponse;

import java.util.List;

public interface SkillService {

    SkillResponse createSkill(SkillRequest request);

    SkillResponse updateSkill(Long skillId, SkillRequest request);

    SkillResponse getSkillById(Long skillId);

    List<SkillResponse> getAllSkills(
            Long profileId,
            Long skillCategoryId,
            Boolean active
    );

    void deleteSkill(Long skillId);
}
