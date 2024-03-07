/** By YamiY Yaten */
package com.yatensoft.dcbot.mapper;

import com.yatensoft.dcbot.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.persitence.model.UrlArchive;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Business object mapper.
 */
@Component
public class BusinessObjectMapper {
    /**
     * Map UrlArchiveDTO to UrlArchive
     * @param source UrlArchiveDTO
     * @return UrlArchive object with default values if needed
     */
    public UrlArchive mapUrlArchiveDtoToUrlArchive(final UrlArchiveDTO source) {
        if (source == null) {
            return null;
        }

        UrlArchive result = new UrlArchive();

        result.setId(source.getId() == null ? null : source.getId());
        result.setUrl(source.getUrl() == null ? null : source.getUrl());
        result.setTopic(source.getTopic() == null ? null : source.getTopic().getShortName());
        result.setType(source.getType() == null ? null : source.getType().getValue());
        result.setDateOfCreation(
                source.getDateOfCreation() == null ? Date.from(Instant.now()) : source.getDateOfCreation());

        return result;
    }

    /**
     * Map UrlArchiveDTO to UrlArchive
     * @param sourceList UrlArchiveDTO
     * @return UrlArchive object with default values if needed
     */
    public List<UrlArchive> mapListUrlArchiveDtoToListUrlArchive(final List<UrlArchiveDTO> sourceList) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        return sourceList.stream()
                .map(element -> mapUrlArchiveDtoToUrlArchive(element))
                .toList();
    }
}
