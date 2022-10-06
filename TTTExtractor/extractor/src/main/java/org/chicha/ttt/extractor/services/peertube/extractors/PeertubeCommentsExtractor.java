package org.chicha.ttt.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import org.chicha.ttt.extractor.Page;
import org.chicha.ttt.extractor.StreamingService;
import org.chicha.ttt.extractor.comments.CommentsExtractor;
import org.chicha.ttt.extractor.comments.CommentsInfoItem;
import org.chicha.ttt.extractor.comments.CommentsInfoItemsCollector;
import org.chicha.ttt.extractor.downloader.Downloader;
import org.chicha.ttt.extractor.downloader.Response;
import org.chicha.ttt.extractor.exceptions.ExtractionException;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.linkhandler.ListLinkHandler;
import org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper;
import org.chicha.ttt.extractor.utils.Utils;

import java.io.IOException;

import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.COUNT_KEY;
import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.ITEMS_PER_PAGE;
import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.START_KEY;
import static org.chicha.ttt.extractor.utils.Utils.isNullOrEmpty;

import javax.annotation.Nonnull;

public class PeertubeCommentsExtractor extends CommentsExtractor {
    public PeertubeCommentsExtractor(final StreamingService service,
                                     final ListLinkHandler uiHandler) {
        super(service, uiHandler);
    }

    @Nonnull
    @Override
    public InfoItemsPage<CommentsInfoItem> getInitialPage()
            throws IOException, ExtractionException {
        return getPage(new Page(getUrl() + "?" + START_KEY + "=0&"
                + COUNT_KEY + "=" + ITEMS_PER_PAGE));
    }

    private void collectCommentsFrom(final CommentsInfoItemsCollector collector,
                                     final JsonObject json) throws ParsingException {
        final JsonArray contents = json.getArray("data");

        for (final Object c : contents) {
            if (c instanceof JsonObject) {
                final JsonObject item = (JsonObject) c;
                if (!item.getBoolean("isDeleted")) {
                    collector.commit(new PeertubeCommentsInfoItemExtractor(item, this));
                }
            }
        }
    }

    @Override
    public InfoItemsPage<CommentsInfoItem> getPage(final Page page)
            throws IOException, ExtractionException {
        if (page == null || isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }

        final Response response = getDownloader().get(page.getUrl());

        JsonObject json = null;
        if (response != null && !Utils.isBlank(response.responseBody())) {
            try {
                json = JsonParser.object().from(response.responseBody());
            } catch (final Exception e) {
                throw new ParsingException("Could not parse json data for comments info", e);
            }
        }

        if (json != null) {
            PeertubeParsingHelper.validate(json);
            final long total = json.getLong("total");

            final CommentsInfoItemsCollector collector
                    = new CommentsInfoItemsCollector(getServiceId());
            collectCommentsFrom(collector, json);

            return new InfoItemsPage<>(collector,
                    PeertubeParsingHelper.getNextPage(page.getUrl(), total));
        } else {
            throw new ExtractionException("Unable to get PeerTube kiosk info");
        }
    }

    @Override
    public void onFetchPage(@Nonnull final Downloader downloader) {
    }
}
