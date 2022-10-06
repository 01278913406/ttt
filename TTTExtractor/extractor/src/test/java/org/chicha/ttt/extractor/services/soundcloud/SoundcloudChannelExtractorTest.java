package org.chicha.ttt.extractor.services.soundcloud;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderTestImpl;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.channel.ChannelExtractor;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.services.BaseChannelExtractorTest;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudChannelExtractor;

import static org.junit.jupiter.api.Assertions.*;
import static org.chicha.ttt.extractor.ExtractorAsserts.assertEmpty;
import static org.chicha.ttt.extractor.ExtractorAsserts.assertIsSecureUrl;
import static org.chicha.ttt.extractor.ServiceList.SoundCloud;
import static org.chicha.ttt.extractor.services.DefaultTests.*;

/**
 * Test for {@link SoundcloudChannelExtractor}
 */
public class SoundcloudChannelExtractorTest {
    public static class LilUzi implements BaseChannelExtractorTest {
        private static SoundcloudChannelExtractor extractor;

        @BeforeAll
        public static void setUp() throws Exception {
            NewPipe.init(DownloaderTestImpl.getInstance());
            extractor = (SoundcloudChannelExtractor) SoundCloud
                    .getChannelExtractor("http://soundcloud.com/liluzivert/sets");
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
            assertEquals("Lil Uzi Vert", extractor.getName());
        }

        @Test
        public void testId() {
            assertEquals("10494998", extractor.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/liluzivert", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("http://soundcloud.com/liluzivert/sets", extractor.getOriginalUrl());
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

        /*//////////////////////////////////////////////////////////////////////////
        // ChannelExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testDescription() {
            assertNotNull(extractor.getDescription());
        }

        @Test
        public void testAvatarUrl() {
            assertIsSecureUrl(extractor.getAvatarUrl());
        }

        @Test
        public void testBannerUrl() {
            assertIsSecureUrl(extractor.getBannerUrl());
        }

        @Test
        public void testFeedUrl() {
            assertEmpty(extractor.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() {
            assertTrue(extractor.getSubscriberCount() >= 1e6, "Wrong subscriber count");
        }

        @Override
        public void testVerified() throws Exception {
            assertTrue(extractor.isVerified());
        }
    }

    public static class DubMatix implements BaseChannelExtractorTest {
        private static SoundcloudChannelExtractor extractor;

        @BeforeAll
        public static void setUp() throws Exception {
            NewPipe.init(DownloaderTestImpl.getInstance());
            extractor = (SoundcloudChannelExtractor) SoundCloud
                    .getChannelExtractor("https://soundcloud.com/dubmatix");
            extractor.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Additional Testing
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testGetPageInNewExtractor() throws Exception {
            final ChannelExtractor newExtractor = SoundCloud.getChannelExtractor(extractor.getUrl());
            defaultTestGetPageInNewExtractor(extractor, newExtractor);
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
            assertEquals("dubmatix", extractor.getName());
        }

        @Test
        public void testId() {
            assertEquals("542134", extractor.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/dubmatix", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/dubmatix", extractor.getOriginalUrl());
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

        /*//////////////////////////////////////////////////////////////////////////
        // ChannelExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testDescription() {
            assertNotNull(extractor.getDescription());
        }

        @Test
        public void testAvatarUrl() {
            assertIsSecureUrl(extractor.getAvatarUrl());
        }

        @Test
        public void testBannerUrl() {
            assertIsSecureUrl(extractor.getBannerUrl());
        }

        @Test
        public void testFeedUrl() {
            assertEmpty(extractor.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() {
            assertTrue(extractor.getSubscriberCount() >= 2e6, "Wrong subscriber count");
        }

        @Override
        public void testVerified() throws Exception {
            assertTrue(extractor.isVerified());
        }
    }
}
