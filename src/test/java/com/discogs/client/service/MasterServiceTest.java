package com.discogs.client.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.discogs.client.model.Master;
import com.discogs.client.repository.MasterRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class MasterServiceTest {

    @Mock
    private MasterRepository masterRepository;

    @InjectMocks
    private MasterService masterService;

    @Test
    public void testSaveMaster() {
        Master master = new Master();
        master.setTitle("Test Master");

        when(masterRepository.save(any(Master.class))).thenReturn(master);

        Master savedMaster = masterService.saveMaster(master);

        assertNotNull(savedMaster);
        assertEquals("Test Master", savedMaster.getTitle());
    }
}