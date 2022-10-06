package org.chicha.ttt.extractor.services.peertube;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderTestImpl;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.services.peertube.linkHandler.PeertubeChannelLinkHandlerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.chicha.ttt.extractor.ServiceList.PeerTube;

/**
 * Test for {@link PeertubeChannelLinkHandlerFactory}
 */
public class PeertubeChannelLinkHandlerFactoryTest {

    private static PeertubeChannelLinkHandlerFactory linkHandler;

    @BeforeAll
    public static void setUp() {
        PeerTube.setInstance(new PeertubeInstance("https://peertube.stream", "PeerTube on peertube.stream"));
        linkHandler = PeertubeChannelLinkHandlerFactory.getInstance();
        NewPipe.init(DownloaderTestImpl.getInstance());
    }

    @Test
    public void acceptUrlTest() throws ParsingException {
        assertTrue(linkHandler.acceptUrl("https://peertube.stream/accounts/kranti@videos.squat.net"));
        assertTrue(linkHandler.acceptUrl("https://peertube.stream/a/kranti@videos.squat.net"));
        assertTrue(linkHandler.acceptUrl("https://peertube.stream/api/v1/accounts/kranti@videos.squat.net/videos"));
        assertTrue(linkHandler.acceptUrl("https://peertube.stream/video-channels/kranti_channel@videos.squat.net/videos"));
        assertTrue(linkHandler.acceptUrl("https://peertube.stream/c/kranti_channel@videos.squat.net/videos"));
        assertTrue(linkHandler.acceptUrl("https://peertube.stream/api/v1/video-channels/7682d9f2-07be-4622-862e-93ec812e2ffa"));
    }

    @Test
    public void getId() throws ParsingException {
        assertEquals("accounts/kranti@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/accounts/kranti@videos.squat.net").getId());
        assertEquals("accounts/kranti@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/a/kranti@videos.squat.net").getId());
        assertEquals("accounts/kranti@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/accounts/kranti@videos.squat.net/videos").getId());
        assertEquals("accounts/kranti@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/a/kranti@videos.squat.net/videos").getId());
        assertEquals("accounts/kranti@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/api/v1/accounts/kranti@videos.squat.net").getId());
        assertEquals("accounts/kranti@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/api/v1/accounts/kranti@videos.squat.net/videos").getId());

        assertEquals("video-channels/kranti_channel@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/video-channels/kranti_channel@videos.squat.net/videos").getId());
        assertEquals("video-channels/kranti_channel@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/c/kranti_channel@videos.squat.net/videos").getId());
        assertEquals("video-channels/kranti_channel@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/c/kranti_channel@videos.squat.net/video-playlists").getId());
        assertEquals("video-channels/kranti_channel@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/api/v1/video-channels/kranti_channel@videos.squat.net").getId());
    }

    @Test
    public void getUrl() throws ParsingException {
        assertEquals("https://peertube.stream/video-channels/kranti_channel@videos.squat.net",
                linkHandler.fromId("video-channels/kranti_channel@videos.squat.net").getUrl());
        assertEquals("https://peertube.stream/accounts/kranti@videos.squat.net",
                linkHandler.fromId("accounts/kranti@videos.squat.net").getUrl());
        assertEquals("https://peertube.stream/accounts/kranti@videos.squat.net",
                linkHandler.fromId("kranti@videos.squat.net").getUrl());
        assertEquals("https://peertube.stream/video-channels/kranti_channel@videos.squat.net",
                linkHandler.fromUrl("https://peertube.stream/api/v1/video-channels/kranti_channel@videos.squat.net").getUrl());
    }
}
