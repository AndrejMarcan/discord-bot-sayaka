/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.persitence.entity.UrlArchive;
import com.yatensoft.dcbot.persitence.repository.UrlArchiveRepository;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of UrlArchiveService
 */
@Service
public class UrlArchiveServiceImpl implements UrlArchiveService {
    private final UrlArchiveRepository urlArchiveRepository;

    public UrlArchiveServiceImpl(@Autowired final UrlArchiveRepository urlArchiveRepository) {
        super();
        this.urlArchiveRepository = urlArchiveRepository;
    }

    /** See {@link UrlArchiveService#createUrlArchiveRecord(UrlArchive)} */
    @Override
    public void createUrlArchiveRecord(final UrlArchive record) {
        urlArchiveRepository.save(record);
    }

    /** See {@link UrlArchiveService#checkIfUrlArchiveRecordExists(String, TopicEnum, ArchiveTypeEnum)} */
    @Override
    public boolean checkIfUrlArchiveRecordExists(final String url, final TopicEnum topic, final ArchiveTypeEnum type) {
        return urlArchiveRepository.existsByUrlAndTopicAndType(url, topic.getShortName(), type.getValue());
    }

    /** See {@link UrlArchiveService#storeUrlArchiveRecords(List)} */
    @Override
    public void storeUrlArchiveRecords(final List<UrlArchive> records) {
        urlArchiveRepository.saveAll(records);
    }
}
