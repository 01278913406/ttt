package org.chicha.ttt.extractor.services.peertube.extractors;

import org.chicha.ttt.extractor.StreamingService;
import org.chicha.ttt.extractor.suggestion.SuggestionExtractor;

import java.util.Collections;
import java.util.List;

public class PeertubeSuggestionExtractor extends SuggestionExtractor {
    public PeertubeSuggestionExtractor(final StreamingService service) {
        super(service);
    }

    @Override
    public List<String> suggestionList(final String query) {
        return Collections.emptyList();
    }
}
