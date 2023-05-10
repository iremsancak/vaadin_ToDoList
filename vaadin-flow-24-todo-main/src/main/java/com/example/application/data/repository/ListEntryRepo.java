package com.example.application.data.repository;

import com.example.application.data.entity.ListEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListEntryRepo extends JpaRepository<ListEntry, Integer> {
}
