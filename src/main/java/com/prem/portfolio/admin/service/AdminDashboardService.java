
package com.prem.portfolio.admin.service;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> getDashboardStatistics() {

        String sql = """
            SELECT
                (SELECT COUNT(*) FROM portfolio.profile)
                    AS total_profiles,

                (SELECT COUNT(*) FROM portfolio.about_content)
                    AS total_about_sections,

                (SELECT COUNT(*) FROM portfolio.projects)
                    AS total_projects,

                (SELECT COUNT(*) FROM portfolio.projects
                    WHERE is_active = TRUE)
                    AS active_projects,

                (SELECT COUNT(*) FROM portfolio.skills)
                    AS total_skills,

                (SELECT COUNT(*) FROM portfolio.skills
                    WHERE is_active = TRUE)
                    AS active_skills,

                (SELECT COUNT(*) FROM portfolio.experiences)
                    AS total_experiences,

                (SELECT COUNT(*) FROM portfolio.education)
                    AS total_education,

                (SELECT COUNT(*) FROM portfolio.blogs)
                    AS total_blogs,

                (SELECT COUNT(*) FROM portfolio.blogs
                    WHERE publication_status = 'PUBLISHED'
                    AND is_active = TRUE)
                    AS published_blogs,

                (SELECT COUNT(*) FROM portfolio.media_assets)
                    AS total_media,

                (SELECT COUNT(*) FROM portfolio.media_assets
                    WHERE is_active = TRUE)
                    AS active_media,

                (SELECT COUNT(*) FROM portfolio.contact_messages)
                    AS total_contact_messages,

                (SELECT COUNT(*) FROM portfolio.contact_messages
                    WHERE message_status = 'NEW')
                    AS new_contact_messages
            """;

        return jdbcTemplate.queryForMap(sql);
    }
}
