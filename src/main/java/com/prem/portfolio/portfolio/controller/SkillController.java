
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.SkillRequest;
import com.prem.portfolio.portfolio.dto.response.SkillResponse;
import com.prem.portfolio.portfolio.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(
            @Valid @RequestBody SkillRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(skillService.createSkill(request));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills(
            @RequestParam(required = false) Long profileId,
            @RequestParam(required = false) Long skillCategoryId,
            @RequestParam(required = false) Boolean active) {

        return ResponseEntity.ok(
                skillService.getAllSkills(profileId, skillCategoryId, active)
        );
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<SkillResponse> getSkillById(
            @PathVariable Long skillId) {

        return ResponseEntity.ok(skillService.getSkillById(skillId));
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<SkillResponse> updateSkill(
            @PathVariable Long skillId,
            @Valid @RequestBody SkillRequest request) {

        return ResponseEntity.ok(
                skillService.updateSkill(skillId, request)
        );
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable Long skillId) {

        skillService.deleteSkill(skillId);

        return ResponseEntity.noContent().build();
    }
}
