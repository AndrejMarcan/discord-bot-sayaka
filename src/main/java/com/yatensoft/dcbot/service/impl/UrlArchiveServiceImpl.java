/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.mapper.BusinessObjectMapper;
import com.yatensoft.dcbot.persitence.repository.UrlArchiveRepository;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of UrlArchiveService
 */
@Service
public class UrlArchiveServiceImpl implements UrlArchiveService {
    private final UrlArchiveRepository urlArchiveRepository;
    private final BusinessObjectMapper mapper;

    public UrlArchiveServiceImpl(
            @Autowired final UrlArchiveRepository urlArchiveRepository, @Autowired final BusinessObjectMapper mapper) {
        super();
        this.urlArchiveRepository = urlArchiveRepository;
        this.mapper = mapper;
    }

    /** See {@link UrlArchiveService#createUrlArchiveRecord(UrlArchiveDTO)} */
    @Override
    public void createUrlArchiveRecord(final UrlArchiveDTO record) {
        urlArchiveRepository.save(mapper.mapUrlArchiveDtoToUrlArchive(record));
    }

    /** See {@link UrlArchiveService#checkIfUrlArchiveRecordExists(String, TopicEnum, ArchiveTypeEnum)} */
    @Override
    public boolean checkIfUrlArchiveRecordExists(final String url, final TopicEnum topic, final ArchiveTypeEnum type) {
        return urlArchiveRepository.existsByUrlAndTopicAndType(url, topic.getShortName(), type.getValue());
    }

    /** See {@link UrlArchiveService#storeUrlArchiveRecords(List)} */
    @Override
    public void storeUrlArchiveRecords(final List<UrlArchiveDTO> records) {
        urlArchiveRepository.saveAll(mapper.mapListUrlArchiveDtoToListUrlArchive(records));
    }
}
