package org.chicha.ttt.extractor.services.peertube.search;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.extractor.services.peertube.PeertubeInstance;
import org.chicha.ttt.extractor.services.peertube.linkHandler.PeertubeSearchQueryHandlerFactory;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.chicha.ttt.extractor.ServiceList.PeerTube;

public class PeertubeSearchQHTest {

    @BeforeAll
    public static void setUpClass() throws Exception {
        // setting instance might break test when running in parallel
        PeerTube.setInstance(new PeertubeInstance("https://peertube.mastodon.host", "PeerTube on Mastodon.host"));
    }

    @Test
    public void testRegularValues() throws Exception {
        assertEquals("https://peertube.mastodon.host/api/v1/search/videos?search=asdf", PeerTube.getSearchQHFactory().fromQuery("asdf").getUrl());
        assertEquals("https://peertube.mastodon.host/api/v1/search/videos?search=hans", PeerTube.getSearchQHFactory().fromQuery("hans").getUrl());
        assertEquals("https://peertube.mastodon.host/api/v1/search/videos?search=Poifj%26jaijf", PeerTube.getSearchQHFactory().fromQuery("Poifj&jaijf").getUrl());
        assertEquals("https://peertube.mastodon.host/api/v1/search/videos?search=G%C3%BCl%C3%BCm", PeerTube.getSearchQHFactory().fromQuery("Gülüm").getUrl());
        assertEquals("https://peertube.mastodon.host/api/v1/search/videos?search=%3Fj%24%29H%C2%A7B", PeerTube.getSearchQHFactory().fromQuery("?j$)H§B").getUrl());
        assertEquals("https://sepiasearch.org/api/v1/search/videos?search=%3Fj%24%29H%C2%A7B", PeerTube.getSearchQHFactory().fromQuery("?j$)H§B", singletonList(PeertubeSearchQueryHandlerFactory.SEPIA_VIDEOS), "").getUrl());
        assertEquals("https://anotherpeertubeindex.com/api/v1/search/videos?search=%3Fj%24%29H%C2%A7B", PeerTube.getSearchQHFactory().fromQuery("?j$)H§B", singletonList(PeertubeSearchQueryHandlerFactory.SEPIA_VIDEOS), "", "https://anotherpeertubeindex.com").getUrl());
    }
}