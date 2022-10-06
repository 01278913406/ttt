// Created by Fynn Godau 2019, licensed GNU GPL version 3 or later

package org.chicha.ttt.extractor.services.bandcamp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderTestImpl;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.channel.ChannelExtractor;
import org.chicha.ttt.extractor.exceptions.ExtractionException;
import org.chicha.ttt.extractor.services.BaseChannelExtractorTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.chicha.ttt.extractor.ServiceList.Bandcamp;

public class BandcampChannelExtractorTest implements BaseChannelExtractorTest {

    private static ChannelExtractor extractor;

    @BeforeAll
    public static void setUp() throws Exception {
        NewPipe.init(DownloaderTestImpl.getInstance());
        extractor = Bandcamp.getChannelExtractor("https://toupie.bandcamp.com/releases");
        extractor.fetchPage();
    }

    @Test
    public void testLength() throws ExtractionException, IOException {
        assertTrue(extractor.getInitialPage().getItems().size() >= 0);
    }

    @Override
    @Test
    public void testDescription() throws Exception {
        assertEquals("making music:)", extractor.getDescription());
    }

    @Override
    public void testAvatarUrl() throws Exception {
        assertTrue(extractor.getAvatarUrl().contains("://f4.bcbits.com/"), "unexpected avatar URL");
    }

    @Override
    public void testBannerUrl() throws Exception {
        assertTrue(extractor.getBannerUrl().contains("://f4.bcbits.com/"), "unexpected banner URL");
    }

    @Override
    public void testFeedUrl() throws Exception {
        assertNull(extractor.getFeedUrl());
    }

    @Override
    public void testSubscriberCount() throws Exception {
        assertEquals(-1, extractor.getSubscriberCount());
    }

    @Override
    public void testVerified() throws Exception {
        assertFalse(extractor.isVerified());
    }

    @Override
    public void testRelatedItems() throws Exception {
        // not implemented
    }

    @Override
    public void testMoreRelatedItems() throws Exception {
        // not implemented
    }

    @Override
    public void testServiceId() {
        assertEquals(Bandcamp.getServiceId(), extractor.getServiceId());
    }

    @Override
    public void testName() throws Exception {
        assertEquals("toupie", extractor.getName());
    }

    @Override
    public void testId() throws Exception {
        assertEquals("https://toupie.bandcamp.com/", extractor.getId());
    }

    @Override
    public void testUrl() throws Exception {
        assertEquals("https://toupie.bandcamp.com", extractor.getUrl());
    }

    @Override
    public void testOriginalUrl() throws Exception {
        assertEquals("https://toupie.bandcamp.com", extractor.getUrl());
    }
}
