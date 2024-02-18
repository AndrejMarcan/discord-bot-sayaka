package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.persitence.entity.UrlArchive;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of UrlArchiveService
 */
@Service
public class UrlArchiveServiceImpl implements UrlArchiveService {
    private final UrlArchiveService urlArchiveService;

    public UrlArchiveServiceImpl(@Autowired final UrlArchiveService urlArchiveService) {
        super();
        this.urlArchiveService = urlArchiveService;
    }

    /** See {@link UrlArchiveService#createUrlArchiveRecord(UrlArchive)} */
    @Override
    public UrlArchive createUrlArchiveRecord(UrlArchive record) {
        return null;
    }
}
