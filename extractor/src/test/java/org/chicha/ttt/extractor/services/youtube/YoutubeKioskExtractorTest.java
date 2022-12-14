package org.chicha.ttt.extractor.services.youtube;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.chicha.ttt.extractor.ServiceList.YouTube;
import static org.chicha.ttt.extractor.services.DefaultTests.assertNoMoreItems;
import static org.chicha.ttt.extractor.services.DefaultTests.defaultTestRelatedItems;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderFactory;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.services.BaseListExtractorTest;
import org.chicha.ttt.extractor.services.youtube.extractors.YoutubeTrendingExtractor;

public class YoutubeKioskExtractorTest {

    private static final String RESOURCE_PATH = DownloaderFactory.RESOURCE_PATH + "services/youtube/extractor/kiosk/";
    
    public static class Trending implements BaseListExtractorTest {
        private static YoutubeTrendingExtractor extractor;

        @BeforeAll
        public static void setUp() throws Exception {
            YoutubeTestsUtils.ensureStateless();
            NewPipe.init(DownloaderFactory.getDownloader(RESOURCE_PATH + "trending"));
            extractor = (YoutubeTrendingExtractor) YouTube.getKioskList().getDefaultKioskExtractor();
            extractor.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Extractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(YouTube.getServiceId(), extractor.getServiceId());
        }

        @Test
        public void testName() throws Exception {
            assertEquals("Trending", extractor.getName());
        }

        @Test
        public void testId() throws Exception {
            assertEquals("Trending", extractor.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/feed/trending", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/feed/trending", extractor.getOriginalUrl());
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
            assertNoMoreItems(extractor);
        }
    }
}
