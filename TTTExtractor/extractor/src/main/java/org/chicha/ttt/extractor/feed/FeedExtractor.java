package org.chicha.ttt.extractor.feed;

import org.chicha.ttt.extractor.ListExtractor;
import org.chicha.ttt.extractor.StreamingService;
import org.chicha.ttt.extractor.linkhandler.ListLinkHandler;
import org.chicha.ttt.extractor.stream.StreamInfoItem;

/**
 * This class helps to extract items from lightweight feeds that the services may provide.
 * <p>
 * YouTube is an example of a service that has this alternative available.
 */
public abstract class FeedExtractor extends ListExtractor<StreamInfoItem> {
    public FeedExtractor(final StreamingService service, final ListLinkHandler listLinkHandler) {
        super(service, listLinkHandler);
    }
}
