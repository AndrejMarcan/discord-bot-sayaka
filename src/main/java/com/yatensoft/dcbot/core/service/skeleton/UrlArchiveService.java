/** By YamiY Yaten */
package com.yatensoft.dcbot.core.service.skeleton;

import com.yatensoft.dcbot.core.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.core.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.core.enumeration.TopicEnum;
import java.util.List;

/**
 * Service class responsible for handling of database operations related to URLs to various sites.
 */
public interface UrlArchiveService {
    /**
     * Creates record of url to be archived
     * @param record object to store in DB
     */
    void createUrlArchiveRecord(UrlArchiveDTO record);

    /**
     * Fetches url archive record by url and topic or empty Optional if no record is found
     * @param url url string
     * @param topic topic to which the url belongs to
     * @param type article type
     * @return record if exists
     */
    boolean checkIfUrlArchiveRecordExists(String url, TopicEnum topic, ArchiveTypeEnum type);

    /**
     * Create records of urls to be archived
     * @param records object to be stored in DB
     */
    void storeUrlArchiveRecords(List<UrlArchiveDTO> records);
}
