package org.chicha.ttt.extractor.linkhandler;

import org.chicha.ttt.extractor.exceptions.ParsingException;

import java.util.Collections;
import java.util.List;

public abstract class SearchQueryHandlerFactory extends ListLinkHandlerFactory {

    ///////////////////////////////////
    // To Override
    ///////////////////////////////////

    @Override
    public abstract String getUrl(String query, List<String> contentFilter, String sortFilter)
            throws ParsingException;

    @SuppressWarnings("unused")
    public String getSearchString(final String url) {
        return "";
    }

    ///////////////////////////////////
    // Logic
    ///////////////////////////////////

    @Override
    public String getId(final String url) {
        return getSearchString(url);
    }

    @Override
    public SearchQueryHandler fromQuery(final String query,
                                        final List<String> contentFilter,
                                        final String sortFilter) throws ParsingException {
        return new SearchQueryHandler(super.fromQuery(query, contentFilter, sortFilter));
    }

    public SearchQueryHandler fromQuery(final String query) throws ParsingException {
        return fromQuery(query, Collections.emptyList(), "");
    }

    /**
     * It's not mandatory for NewPipe to handle the Url
     */
    @Override
    public boolean onAcceptUrl(final String url) {
        return false;
    }
}
