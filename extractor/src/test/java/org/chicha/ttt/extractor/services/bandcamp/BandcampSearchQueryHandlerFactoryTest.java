// Created by Fynn Godau 2019, licensed GNU GPL version 3 or later

package org.chicha.ttt.extractor.services.bandcamp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderTestImpl;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.services.bandcamp.linkHandler.BandcampSearchQueryHandlerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.chicha.ttt.extractor.ServiceList.Bandcamp;

public class BandcampSearchQueryHandlerFactoryTest {

    static BandcampSearchQueryHandlerFactory searchQuery;

    @BeforeAll
    public static void setUp() {
        NewPipe.init(DownloaderTestImpl.getInstance());

        searchQuery = (BandcampSearchQueryHandlerFactory) Bandcamp
                .getSearchQHFactory();
    }

    @Test
    public void testEncoding() throws ParsingException {
        // Note: this isn't exactly as bandcamp does it (it wouldn't encode '!'), but both works
        assertEquals("https://bandcamp.com/search?q=hello%21%22%C2%A7%24%25%26%2F%28%29%3D&page=1", searchQuery.getUrl("hello!\"§$%&/()="));
        // Note: bandcamp uses %20 instead of '+', but both works
        assertEquals("https://bandcamp.com/search?q=search+query+with+spaces&page=1", searchQuery.getUrl("search query with spaces"));
    }
}
