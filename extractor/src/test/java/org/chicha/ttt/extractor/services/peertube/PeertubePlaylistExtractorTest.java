package org.chicha.ttt.extractor.services.peertube;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderTestImpl;
import org.chicha.ttt.extractor.ExtractorAsserts;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubePlaylistExtractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.chicha.ttt.extractor.ServiceList.PeerTube;

public class PeertubePlaylistExtractorTest {

    public static class Shocking {
        private static PeertubePlaylistExtractor extractor;

        @BeforeAll
        public static void setUp() throws Exception {
            NewPipe.init(DownloaderTestImpl.getInstance());
            extractor = (PeertubePlaylistExtractor) PeerTube.getPlaylistExtractor(
                    "https://framatube.org/videos/watch/playlist/96b0ee2b-a5a7-4794-8769-58d8ccb79ab7");
            extractor.fetchPage();
        }

        @Test
        void testGetName() throws ParsingException {
            assertEquals("Shocking !", extractor.getName());
        }

        @Test
        @Disabled("URL changes with every request")
        void testGetThumbnailUrl() throws ParsingException {
            assertEquals(
                    "https://framatube.org/static/thumbnails/playlist-96b0ee2b-a5a7-4794-8769-58d8ccb79ab7.jpg",
                    extractor.getThumbnailUrl());
        }

        @Test
        void testGetUploaderUrl() throws ParsingException {
            assertEquals("https://skeptikon.fr/accounts/metadechoc", extractor.getUploaderUrl());
        }

        @Test
        void testGetUploaderAvatarUrl() throws ParsingException {
            assertEquals(
                    "https://framatube.org/lazy-static/avatars/cd0f781d-0287-4be2-94f1-24cd732337b2.jpg",
                    extractor.getUploaderAvatarUrl());
        }

        @Test
        void testGetUploaderName() throws ParsingException {
            assertEquals("Méta de Choc", extractor.getUploaderName());
        }

        @Test
        void testGetStreamCount() throws ParsingException {
            ExtractorAsserts.assertGreaterOrEqual(39, extractor.getStreamCount());
        }

        @Test
        void testGetSubChannelUrl() throws ParsingException {
            assertEquals("https://skeptikon.fr/video-channels/metadechoc_channel", extractor.getSubChannelUrl());
        }

        @Test
        void testGetSubChannelName() throws ParsingException {
            assertEquals("SHOCKING !", extractor.getSubChannelName());
        }

        @Test
        void testGetSubChannelAvatarUrl() throws ParsingException {
            assertEquals(
                    "https://framatube.org/lazy-static/avatars/637753af-fcf2-4b61-88f9-b9857c953457.png",
                    extractor.getSubChannelAvatarUrl());
        }
    }
}
