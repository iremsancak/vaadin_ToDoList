package com.example.application.data.repository;

import com.example.application.data.entity.ListEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListEntryRepo extends JpaRepository<ListEntry, Integer> {
}
