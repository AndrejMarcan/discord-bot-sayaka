/** By YamiY Yaten */
package com.yatensoft.dcbot.core.persitence.repository;

import com.yatensoft.dcbot.core.persitence.model.UrlArchive;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlArchiveRepository extends JpaRepository<UrlArchive, UUID> {

    /**
     * Check if the record for given criteria exists in DB
     * @param url link to website
     * @param topic topic identifier
     * @param type type of article where the URL leads
     * @return true if record exists
     */
    boolean existsByUrlAndTopicAndType(final String url, final String topic, final String type);
}
