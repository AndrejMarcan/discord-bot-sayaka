/** By YamiY Yaten */
package com.yatensoft.dcbot.core.mapper;

import com.yatensoft.dcbot.core.dto.DiscordChannelDTO;
import com.yatensoft.dcbot.core.dto.DiscordServerDTO;
import com.yatensoft.dcbot.core.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.core.enumeration.DiscordChannelTypeEnum;
import com.yatensoft.dcbot.core.persitence.model.DiscordChannel;
import com.yatensoft.dcbot.core.persitence.model.DiscordServer;
import com.yatensoft.dcbot.core.persitence.model.UrlArchive;
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

        result.setId(source.getId());
        result.setUrl(source.getUrl());
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

    /**
     * Map DiscordServer to DiscordServerDTO
     * @param discordServer entity
     * @return data transfer object
     */
    public DiscordServerDTO mapDiscordServerToDiscordServerDTO(final DiscordServer discordServer) {
        if (discordServer == null) {
            return null;
        }

        DiscordServerDTO discordServerDTO = new DiscordServerDTO();

        discordServerDTO.setId(discordServer.getId());
        discordServerDTO.setDiscordServerId(discordServer.getDiscordServerId());
        discordServerDTO.setDiscordServerName(discordServer.getDiscordServerName());

        return discordServerDTO;
    }

    /**
     * Map DiscordChannel to DiscordChannelDTO
     * @param discordChannel entity
     * @return data transfer object
     */
    public DiscordChannelDTO mapDiscordChannelToDiscordChannelDTO(final DiscordChannel discordChannel) {
        if (discordChannel == null) {
            return null;
        }

        DiscordChannelDTO discordChannelDTO = new DiscordChannelDTO();

        discordChannelDTO.setId(discordChannel.getId());
        discordChannelDTO.setDiscordChannelId(discordChannel.getDiscordChannelId());
        discordChannelDTO.setDiscordChannelName(discordChannel.getDiscordChannelName());
        discordChannelDTO.setDiscordServerId(discordChannel.getDiscordServerId());
        discordChannelDTO.setDiscordChannelType(
                DiscordChannelTypeEnum.getDiscordChannelTypeFromString(discordChannel.getDiscordChannelType()));

        return discordChannelDTO;
    }

    /**
     * Map DiscordChannelDTO to DiscordChannel
     * @param discordChannelDTO DTO model
     * @return entity model
     */
    public DiscordChannel mapDiscordChannelToDiscordChannelDTO(final DiscordChannelDTO discordChannelDTO) {
        if (discordChannelDTO == null) {
            return null;
        }

        DiscordChannel discordChannelEntity = new DiscordChannel();

        discordChannelEntity.setId(discordChannelDTO.getId());
        discordChannelEntity.setDiscordChannelId(discordChannelDTO.getDiscordChannelId());
        discordChannelEntity.setDiscordChannelName(discordChannelDTO.getDiscordChannelName());
        discordChannelEntity.setDiscordServerId(discordChannelDTO.getDiscordServerId());
        discordChannelEntity.setDiscordChannelType(
                discordChannelDTO.getDiscordChannelType().getValue());

        return discordChannelEntity;
    }
}
