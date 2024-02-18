package com.yatensoft.dcbot.persitence.repository;

import com.yatensoft.dcbot.persitence.entity.UrlArchive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UrlArchiveRepository extends JpaRepository<UrlArchive, UUID> {
}
