package com.example.application.data.repository;
import com.example.application.data.entity.ListEntry;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class ListEntryRepoTest {
    @Autowired
    ListEntryRepo listEntryRepo;

    @Test
    public void testSaveListEntry() {
        ListEntry listEntry = new ListEntry();
        listEntry.setDescription("TaskOne");
        listEntry.setDone(false);
        ListEntry savedlistEntry = listEntryRepo.save(listEntry);

        assertThat(savedlistEntry.getId()).isNotNull();
        assertThat(savedlistEntry.getDescription()).isEqualTo("TaskOne");
        assertThat(savedlistEntry.getDone()).isEqualTo(false);
}
    @Test
    public void testDeleteListEntry() {
        ListEntry listEntry = new ListEntry();
        listEntry.setDescription("TaskOne");
        listEntry.setDone(false);
        listEntryRepo.save(listEntry);

        listEntryRepo.delete(listEntry);

        assertThat(listEntryRepo.findById(listEntry.getId())).isEmpty();
    }
    @Test
    public void testEditListEntry() {
        ListEntry listEntry = new ListEntry();
        listEntry.setDescription("TaskOne");
        listEntry.setDone(false);
        listEntryRepo.save(listEntry);

        listEntry.setDescription("TaskTwo");
        listEntryRepo.save(listEntry);

        ListEntry updatedListEntry = listEntryRepo.findById(listEntry.getId()).orElse(null);

        assertThat(updatedListEntry).isNotNull();
        assertThat(updatedListEntry.getDescription()).isEqualTo("TaskTwo");
    }



}