package org.chicha.ttt.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;

import org.chicha.ttt.extractor.Page;
import org.chicha.ttt.extractor.StreamingService;
import org.chicha.ttt.extractor.downloader.Downloader;
import org.chicha.ttt.extractor.downloader.Response;
import org.chicha.ttt.extractor.exceptions.ExtractionException;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.kiosk.KioskExtractor;
import org.chicha.ttt.extractor.linkhandler.ListLinkHandler;
import org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper;
import org.chicha.ttt.extractor.stream.StreamInfoItem;
import org.chicha.ttt.extractor.stream.StreamInfoItemsCollector;
import org.chicha.ttt.extractor.utils.Utils;

import java.io.IOException;

import javax.annotation.Nonnull;

import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.COUNT_KEY;
import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.ITEMS_PER_PAGE;
import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.START_KEY;
import static org.chicha.ttt.extractor.services.peertube.PeertubeParsingHelper.collectStreamsFrom;
import static org.chicha.ttt.extractor.utils.Utils.isNullOrEmpty;

public class PeertubeTrendingExtractor extends KioskExtractor<StreamInfoItem> {
    public PeertubeTrendingExtractor(final StreamingService streamingService,
                                     final ListLinkHandler linkHandler,
                                     final String kioskId) {
        super(streamingService, linkHandler, kioskId);
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return getId();
    }

    @Nonnull
    @Override
    public InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        return getPage(new Page(getUrl() + "&" + START_KEY + "=0&"
                + COUNT_KEY + "=" + ITEMS_PER_PAGE));
    }

    @Override
    public InfoItemsPage<StreamInfoItem> getPage(final Page page)
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
                throw new ParsingException("Could not parse json data for kiosk info", e);
            }
        }

        if (json != null) {
            PeertubeParsingHelper.validate(json);
            final long total = json.getLong("total");

            final StreamInfoItemsCollector collector = new StreamInfoItemsCollector(getServiceId());
            collectStreamsFrom(collector, json, getBaseUrl());

            return new InfoItemsPage<>(collector,
                    PeertubeParsingHelper.getNextPage(page.getUrl(), total));
        } else {
            throw new ExtractionException("Unable to get PeerTube kiosk info");
        }
    }

    @Override
    public void onFetchPage(@Nonnull final Downloader downloader)
            throws IOException, ExtractionException {
    }
}
