// Created by Fynn Godau 2019, licensed GNU GPL version 3 or later

package org.chicha.ttt.extractor.services.bandcamp;

import static org.chicha.ttt.extractor.StreamingService.ServiceInfo.MediaCapability.AUDIO;
import static org.chicha.ttt.extractor.StreamingService.ServiceInfo.MediaCapability.COMMENTS;
import static org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampExtractorHelper.BASE_URL;
import static org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampFeaturedExtractor.FEATURED_API_URL;
import static org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampFeaturedExtractor.KIOSK_FEATURED;
import static org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampRadioExtractor.KIOSK_RADIO;
import static org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampRadioExtractor.RADIO_API_URL;

import org.chicha.ttt.extractor.StreamingService;
import org.chicha.ttt.extractor.channel.ChannelExtractor;
import org.chicha.ttt.extractor.comments.CommentsExtractor;
import org.chicha.ttt.extractor.exceptions.ExtractionException;
import org.chicha.ttt.extractor.kiosk.KioskList;
import org.chicha.ttt.extractor.linkhandler.LinkHandler;
import org.chicha.ttt.extractor.linkhandler.LinkHandlerFactory;
import org.chicha.ttt.extractor.linkhandler.ListLinkHandler;
import org.chicha.ttt.extractor.linkhandler.ListLinkHandlerFactory;
import org.chicha.ttt.extractor.linkhandler.SearchQueryHandler;
import org.chicha.ttt.extractor.linkhandler.SearchQueryHandlerFactory;
import org.chicha.ttt.extractor.playlist.PlaylistExtractor;
import org.chicha.ttt.extractor.search.SearchExtractor;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampChannelExtractor;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampCommentsExtractor;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampFeaturedExtractor;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampPlaylistExtractor;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampRadioExtractor;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampRadioStreamExtractor;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampSearchExtractor;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampStreamExtractor;
import org.chicha.ttt.extractor.services.bandcamp.extractors.BandcampSuggestionExtractor;
import org.chicha.ttt.extractor.services.bandcamp.linkHandler.BandcampChannelLinkHandlerFactory;
import org.chicha.ttt.extractor.services.bandcamp.linkHandler.BandcampCommentsLinkHandlerFactory;
import org.chicha.ttt.extractor.services.bandcamp.linkHandler.BandcampFeaturedLinkHandlerFactory;
import org.chicha.ttt.extractor.services.bandcamp.linkHandler.BandcampPlaylistLinkHandlerFactory;
import org.chicha.ttt.extractor.services.bandcamp.linkHandler.BandcampSearchQueryHandlerFactory;
import org.chicha.ttt.extractor.services.bandcamp.linkHandler.BandcampStreamLinkHandlerFactory;
import org.chicha.ttt.extractor.stream.StreamExtractor;
import org.chicha.ttt.extractor.subscription.SubscriptionExtractor;
import org.chicha.ttt.extractor.suggestion.SuggestionExtractor;

import java.util.Arrays;

public class BandcampService extends StreamingService {

    public BandcampService(final int id) {
        super(id, "Bandcamp", Arrays.asList(AUDIO, COMMENTS));
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return new BandcampStreamLinkHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return new BandcampChannelLinkHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return new BandcampPlaylistLinkHandlerFactory();
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return new BandcampSearchQueryHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getCommentsLHFactory() {
        return new BandcampCommentsLinkHandlerFactory();
    }

    @Override
    public SearchExtractor getSearchExtractor(final SearchQueryHandler queryHandler) {
        return new BandcampSearchExtractor(this, queryHandler);
    }

    @Override
    public SuggestionExtractor getSuggestionExtractor() {
        return new BandcampSuggestionExtractor(this);
    }

    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return null;
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {

        final KioskList kioskList = new KioskList(this);

        try {
            kioskList.addKioskEntry(
                    (streamingService, url, kioskId) -> new BandcampFeaturedExtractor(
                            BandcampService.this,
                            new BandcampFeaturedLinkHandlerFactory().fromUrl(FEATURED_API_URL),
                            kioskId
                    ),
                    new BandcampFeaturedLinkHandlerFactory(),
                    KIOSK_FEATURED
            );

            kioskList.addKioskEntry(
                    (streamingService, url, kioskId) -> new BandcampRadioExtractor(
                            BandcampService.this,
                            new BandcampFeaturedLinkHandlerFactory().fromUrl(RADIO_API_URL),
                            kioskId
                    ),
                    new BandcampFeaturedLinkHandlerFactory(),
                    KIOSK_RADIO
            );

            kioskList.setDefaultKiosk(KIOSK_FEATURED);

        } catch (final Exception e) {
            throw new ExtractionException(e);
        }

        return kioskList;
    }

    @Override
    public ChannelExtractor getChannelExtractor(final ListLinkHandler linkHandler) {
        return new BandcampChannelExtractor(this, linkHandler);
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(final ListLinkHandler linkHandler) {
        return new BandcampPlaylistExtractor(this, linkHandler);
    }

    @Override
    public StreamExtractor getStreamExtractor(final LinkHandler linkHandler) {
        if (BandcampExtractorHelper.isRadioUrl(linkHandler.getUrl())) {
            return new BandcampRadioStreamExtractor(this, linkHandler);
        }
        return new BandcampStreamExtractor(this, linkHandler);
    }

    @Override
    public CommentsExtractor getCommentsExtractor(final ListLinkHandler linkHandler) {
        return new BandcampCommentsExtractor(this, linkHandler);
    }
}
