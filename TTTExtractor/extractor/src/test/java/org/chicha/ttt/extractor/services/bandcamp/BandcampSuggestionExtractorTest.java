// Created by Fynn Godau 2019, licensed GNU GPL version 3 or later

package org.chicha.ttt.extractor.services.bandcamp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.chicha.ttt.downloader.DownloaderTestImpl;
import org.chicha.ttt.extractor.NewPipe;
import org.chicha.ttt.extractor.exceptions.ExtractionException;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampSuggestionExtractor;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.chicha.ttt.extractor.ServiceList.Bandcamp;

/**
 * Tests for {@link BandcampSuggestionExtractor}
 */
public class BandcampSuggestionExtractorTest {

    private static BandcampSuggestionExtractor extractor;

    @BeforeAll
    public static void setUp() {
        NewPipe.init(DownloaderTestImpl.getInstance());
        extractor = (BandcampSuggestionExtractor) Bandcamp.getSuggestionExtractor();
    }

    @Test
    public void testSearchExample() throws IOException, ExtractionException {
        final List<String> c418 = extractor.suggestionList("c418");

        assertTrue(c418.contains("C418"));

        // There should be five results, but we can't be sure of that forever
        assertTrue(c418.size() > 2);
    }
}
