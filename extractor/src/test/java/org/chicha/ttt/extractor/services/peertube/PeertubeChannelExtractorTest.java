package org.chicha.ttt.extractor.services.peertube;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderTestImpl;
import org.chicha.ttt.extractor.ExtractorAsserts;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.channel.ChannelExtractor;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.services.BaseChannelExtractorTest;
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubeChannelExtractor;

import static org.junit.jupiter.api.Assertions.*;
import static org.chicha.ttt.extractor.ExtractorAsserts.assertIsSecureUrl;
import static org.chicha.ttt.extractor.ServiceList.PeerTube;
import static org.chicha.ttt.extractor.services.DefaultTests.*;

/**
 * Test for {@link PeertubeChannelExtractor}
 */
public class PeertubeChannelExtractorTest {

    public static class LaQuadratureDuNet implements BaseChannelExtractorTest {
        private static PeertubeChannelExtractor extractor;

        @BeforeAll
        public static void setUp() throws Exception {
            NewPipe.init(DownloaderTestImpl.getInstance());
            // setting instance might break test when running in parallel
            PeerTube.setInstance(new PeertubeInstance("https://framatube.org", "Framatube"));
            extractor = (PeertubeChannelExtractor) PeerTube
                    .getChannelExtractor("https://framatube.org/video-channels/lqdn_channel@video.lqdn.fr/videos");
            extractor.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Extractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(PeerTube.getServiceId(), extractor.getServiceId());
        }

        @Test
        public void testName() throws ParsingException {
            assertEquals("La Quadrature du Net", extractor.getName());
        }

        @Test
        public void testId() throws ParsingException {
            assertEquals("video-channels/lqdn_channel@video.lqdn.fr", extractor.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://framatube.org/video-channels/lqdn_channel@video.lqdn.fr", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://framatube.org/video-channels/lqdn_channel@video.lqdn.fr/videos", extractor.getOriginalUrl());
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
        public void testDescription() throws ParsingException {
            assertNotNull(extractor.getDescription());
        }

        @Test
        public void testParentChannelName() throws ParsingException {
            assertEquals("lqdn", extractor.getParentChannelName());
        }

        @Test
        public void testParentChannelUrl() throws ParsingException {
            assertEquals("https://video.lqdn.fr/accounts/lqdn", extractor.getParentChannelUrl());
        }

        @Test
        public void testParentChannelAvatarUrl() throws ParsingException {
            assertIsSecureUrl(extractor.getParentChannelAvatarUrl());
        }

        @Test
        public void testAvatarUrl() throws ParsingException {
            assertIsSecureUrl(extractor.getAvatarUrl());
        }

        @Test
        public void testBannerUrl() throws ParsingException {
            assertNull(extractor.getBannerUrl());
        }

        @Test
        public void testFeedUrl() throws ParsingException {
            assertEquals("https://framatube.org/feeds/videos.xml?videoChannelId=1126", extractor.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() throws ParsingException {
            ExtractorAsserts.assertGreaterOrEqual(230, extractor.getSubscriberCount());
        }

        @Override
        public void testVerified() throws Exception {
            assertFalse(extractor.isVerified());
        }
    }

    public static class ChatSceptique implements BaseChannelExtractorTest {

        private static PeertubeChannelExtractor extractor;

        @BeforeAll
        public static void setUp() throws Exception {
            NewPipe.init(DownloaderTestImpl.getInstance());
            // setting instance might break test when running in parallel
            PeerTube.setInstance(new PeertubeInstance("https://framatube.org", "Framatube"));
            extractor = (PeertubeChannelExtractor) PeerTube
                    .getChannelExtractor("https://framatube.org/api/v1/video-channels/chatsceptique@skeptikon.fr");
            extractor.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Additional Testing
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testGetPageInNewExtractor() throws Exception {
            final ChannelExtractor newExtractor = PeerTube.getChannelExtractor(extractor.getUrl());
            defaultTestGetPageInNewExtractor(extractor, newExtractor);
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Extractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(PeerTube.getServiceId(), extractor.getServiceId());
        }

        @Test
        public void testName() throws ParsingException {
            assertEquals("Chat Sceptique", extractor.getName());
        }

        @Test
        public void testId() throws ParsingException {
            assertEquals("video-channels/chatsceptique@skeptikon.fr", extractor.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://framatube.org/video-channels/chatsceptique@skeptikon.fr", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://framatube.org/api/v1/video-channels/chatsceptique@skeptikon.fr", extractor.getOriginalUrl());
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
        public void testDescription() throws ParsingException {
            assertNotNull(extractor.getDescription());
        }

        @Test
        public void testParentChannelName() throws ParsingException {
            assertEquals("nathan", extractor.getParentChannelName());
        }

        @Test
        public void testParentChannelUrl() throws ParsingException {
            assertEquals("https://skeptikon.fr/accounts/nathan", extractor.getParentChannelUrl());
        }

        @Test
        public void testParentChannelAvatarUrl() throws ParsingException {
            assertIsSecureUrl(extractor.getParentChannelAvatarUrl());
        }

        @Test
        public void testAvatarUrl() throws ParsingException {
            assertIsSecureUrl(extractor.getAvatarUrl());
        }

        @Test
        public void testBannerUrl() throws ParsingException {
            assertNull(extractor.getBannerUrl());
        }

        @Test
        public void testFeedUrl() throws ParsingException {
            assertEquals("https://framatube.org/feeds/videos.xml?videoChannelId=137", extractor.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() throws ParsingException {
            ExtractorAsserts.assertGreaterOrEqual(700, extractor.getSubscriberCount());
        }

        @Override
        public void testVerified() throws Exception {
            assertFalse(extractor.isVerified());
        }
    }
}
