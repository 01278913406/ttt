package org.chicha.ttt.extractor.services.soundcloud;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderTestImpl;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.services.BaseListExtractorTest;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudChartsExtractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.chicha.ttt.extractor.ServiceList.SoundCloud;
import static org.chicha.ttt.extractor.services.DefaultTests.*;

public class SoundcloudChartsExtractorTest {
    public static class NewAndHot implements BaseListExtractorTest {
        private static SoundcloudChartsExtractor extractor;

        @BeforeAll
        public static void setUp() throws Exception {
            NewPipe.init(DownloaderTestImpl.getInstance());
            extractor = (SoundcloudChartsExtractor) SoundCloud.getKioskList()
                    .getExtractorById("New & hot", null);
            extractor.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Extractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(SoundCloud.getServiceId(), extractor.getServiceId());
        }

        @Test
        public void testName() {
            assertEquals("New & hot", extractor.getName());
        }

        @Test
        public void testId() {
            assertEquals("New & hot", extractor.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/charts/new", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/charts/new", extractor.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(extractor);
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            defaultTestMoreItems(extractor);
        }
    }

    public static class Top50Charts implements BaseListExtractorTest {
        private static SoundcloudChartsExtractor extractor;

        @BeforeAll
        public static void setUp() throws Exception {
            NewPipe.init(DownloaderTestImpl.getInstance());
            extractor = (SoundcloudChartsExtractor) SoundCloud.getKioskList()
                    .getExtractorById("Top 50", null);
            extractor.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Extractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(SoundCloud.getServiceId(), extractor.getServiceId());
        }

        @Test
        public void testName() {
            assertEquals("Top 50", extractor.getName());
        }

        @Test
        public void testId() {
            assertEquals("Top 50", extractor.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/charts/top", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/charts/top", extractor.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(extractor);
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            defaultTestMoreItems(extractor);
        }
    }
}
