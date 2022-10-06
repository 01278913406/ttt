package org.chicha.ttt.extractor.services.peertube;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderTestImpl;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.linkhandler.LinkHandlerFactory;
import org.chicha.ttt.extractor.services.peertube.linkHandler.PeertubeTrendingLinkHandlerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.chicha.ttt.extractor.ServiceList.PeerTube;

/**
 * Test for {@link PeertubeTrendingLinkHandlerFactory}
 */
public class PeertubeTrendingLinkHandlerFactoryTest {
    private static LinkHandlerFactory LinkHandlerFactory;

    @BeforeAll
    public static void setUp() throws Exception {
        // setting instance might break test when running in parallel
        PeerTube.setInstance(new PeertubeInstance("https://peertube.mastodon.host", "PeerTube on Mastodon.host"));
        LinkHandlerFactory = new PeertubeTrendingLinkHandlerFactory();
        NewPipe.init(DownloaderTestImpl.getInstance());
    }

    @Test
    public void getUrl()
            throws Exception {
        assertEquals(LinkHandlerFactory.fromId("Trending").getUrl(), "https://peertube.mastodon.host/api/v1/videos?sort=-trending");
        assertEquals(LinkHandlerFactory.fromId("Most liked").getUrl(), "https://peertube.mastodon.host/api/v1/videos?sort=-likes");
        assertEquals(LinkHandlerFactory.fromId("Recently added").getUrl(), "https://peertube.mastodon.host/api/v1/videos?sort=-publishedAt");
        assertEquals(LinkHandlerFactory.fromId("Local").getUrl(), "https://peertube.mastodon.host/api/v1/videos?sort=-publishedAt&filter=local");
    }

    @Test
    public void getId()
            throws Exception {
        assertEquals(LinkHandlerFactory.fromUrl("https://peertube.mastodon.host/videos/trending").getId(), "Trending");
        assertEquals(LinkHandlerFactory.fromUrl("https://peertube.mastodon.host/videos/most-liked").getId(), "Most liked");
        assertEquals(LinkHandlerFactory.fromUrl("https://peertube.mastodon.host/videos/recently-added").getId(), "Recently added");
        assertEquals(LinkHandlerFactory.fromUrl("https://peertube.mastodon.host/videos/local").getId(), "Local");
    }

    @Test
    public void acceptUrl() throws ParsingException {
        assertTrue(LinkHandlerFactory.acceptUrl("https://peertube.mastodon.host/videos/trending"));
        assertTrue(LinkHandlerFactory.acceptUrl("https://peertube.mastodon.host/videos/trending?adsf=fjaj#fhe"));

        assertTrue(LinkHandlerFactory.acceptUrl("https://peertube.mastodon.host/videos/most-liked"));
        assertTrue(LinkHandlerFactory.acceptUrl("https://peertube.mastodon.host/videos/most-liked?adsf=fjaj#fhe"));

        assertTrue(LinkHandlerFactory.acceptUrl("https://peertube.mastodon.host/videos/recently-added"));
        assertTrue(LinkHandlerFactory.acceptUrl("https://peertube.mastodon.host/videos/recently-added?adsf=fjaj#fhe"));

        assertTrue(LinkHandlerFactory.acceptUrl("https://peertube.mastodon.host/videos/local"));
        assertTrue(LinkHandlerFactory.acceptUrl("https://peertube.mastodon.host/videos/local?adsf=fjaj#fhe"));
    }
}
