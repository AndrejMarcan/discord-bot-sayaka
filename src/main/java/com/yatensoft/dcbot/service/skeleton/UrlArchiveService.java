package com.yatensoft.dcbot.service.skeleton;

import com.yatensoft.dcbot.persitence.entity.UrlArchive;

/**
 * Service class responsible for handling of database operations related to URLs to various sites.
 */
public interface UrlArchiveService {
    /**
     * Creates record of url to be archived
     * @param record object to store in DB
     * @return object of created record
     */
    public UrlArchive createUrlArchiveRecord(UrlArchive record);
}
