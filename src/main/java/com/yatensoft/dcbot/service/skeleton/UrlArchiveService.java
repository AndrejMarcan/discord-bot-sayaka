/** By YamiY Yaten */
package com.yatensoft.dcbot.service.skeleton;

import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.persitence.entity.UrlArchive;

/**
 * Service class responsible for handling of database operations related to URLs to various sites.
 */
public interface UrlArchiveService {
    /**
     * Creates record of url to be archived
     * @param record object to store in DB
     */
    void createUrlArchiveRecord(UrlArchive record);

    /**
     * Fetches url archive record by url and topic or empty Optional if no record is found
     * @param url url string
     * @param topic topic to which the url belongs to
     * @param type article type
     * @return record if exists
     */
    boolean checkIfUrlArchiveRecordExists(String url, TopicEnum topic, ArchiveTypeEnum type);
}
