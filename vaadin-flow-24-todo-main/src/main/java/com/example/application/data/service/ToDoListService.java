package com.example.application.data.service;

import com.example.application.data.entity.ListEntry;
import com.example.application.data.repository.ListEntryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService {
    private final ListEntryRepo listEntryRepo;
    public ToDoListService(ListEntryRepo listEntryRepo) {
        this.listEntryRepo = listEntryRepo;
    }
    public List<ListEntry> findAllListEntries() {
        return listEntryRepo.findAll();
    }
    public void deleteListEntry(ListEntry listEntry) {
        listEntryRepo.delete(listEntry);
    }
    public void saveListEntry(ListEntry listEntry) {listEntryRepo.save(listEntry);}
}
