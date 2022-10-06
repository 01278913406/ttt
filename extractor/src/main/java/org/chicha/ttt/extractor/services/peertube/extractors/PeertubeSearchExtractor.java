package org.chicha.ttt.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;

import org.chicha.ttt.extractor.InfoItem;
import org.chicha.ttt.extractor.MetaInfo;
import org.chicha.ttt.extractor.Page;
import org.chicha.ttt.extractor.StreamingService;
import org.chicha.ttt.extractor.downloader.Downloader;
import org.chicha.ttt.extractor.downloader.Response;
import org.chicha.ttt.extractor.exceptions.ExtractionException;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.linkhandler.SearchQueryHandler;
import org.chicha.ttt.extractor.MultiInfoItemsCollector;
import org.chicha.ttt.extractor.search.SearchExtractor;
import org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper;
import org.chicha.ttt.extractor.utils.Utils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.COUNT_KEY;
import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.ITEMS_PER_PAGE;
import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.START_KEY;
import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.collectStreamsFrom;
import static org.chicha.ttt.extractor.utils.Utils.isNullOrEmpty;

public class PeertubeSearchExtractor extends SearchExtractor {

    // if we should use PeertubeSepiaStreamInfoItemExtractor
    private final boolean sepia;

    public PeertubeSearchExtractor(final StreamingService service,
                                   final SearchQueryHandler linkHandler) {
        this(service, linkHandler, false);
    }

    public PeertubeSearchExtractor(final StreamingService service,
                                   final SearchQueryHandler linkHandler,
                                   final boolean sepia) {
        super(service, linkHandler);
        this.sepia = sepia;
    }

    @Nonnull
    @Override
    public String getSearchSuggestion() {
        return "";
    }

    @Override
    public boolean isCorrectedSearch() {
        return false;
    }

    @Nonnull
    @Override
    public List<MetaInfo> getMetaInfo() {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        return getPage(new Page(getUrl() + "&" + START_KEY + "=0&"
                + COUNT_KEY + "=" + ITEMS_PER_PAGE));
    }

    @Override
    public InfoItemsPage<InfoItem> getPage(final Page page)
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
                throw new ParsingException("Could not parse json data for search info", e);
            }
        }

        if (json != null) {
            PeertubeParsingHelper.validate(json);
            final long total = json.getLong("total");

            final MultiInfoItemsCollector collector = new MultiInfoItemsCollector(getServiceId());
            collectStreamsFrom(collector, json, getBaseUrl(), sepia);

            return new InfoItemsPage<>(collector,
                    PeertubeParsingHelper.getNextPage(page.getUrl(), total));
        } else {
            throw new ExtractionException("Unable to get PeerTube search info");
        }
    }

    @Override
    public void onFetchPage(@Nonnull final Downloader downloader)
            throws IOException, ExtractionException {
    }
}
