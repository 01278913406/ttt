package org.chicha.ttt.extractor.services.peertube;

import static org.chicha.ttt.extractor.StreamingService.ServiceInfo.MediaCapability.COMMENTS;
import static org.chicha.ttt.extractor.StreamingService.ServiceInfo.MediaCapability.VIDEO;
import static java.util.Arrays.asList;

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
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubeAccountExtractor;
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubeChannelExtractor;
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubeCommentsExtractor;
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubePlaylistExtractor;
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubeSearchExtractor;
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubeStreamExtractor;
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubeSuggestionExtractor;
import org.chicha.ttt.extractor.services.peertube.extractors.PeertubeTrendingExtractor;
import org.chicha.ttt.extractor.services.peertube.linkHandler.PeertubeChannelLinkHandlerFactory;
import org.chicha.ttt.extractor.services.peertube.linkHandler.PeertubeCommentsLinkHandlerFactory;
import org.chicha.ttt.extractor.services.peertube.linkHandler.PeertubePlaylistLinkHandlerFactory;
import org.chicha.ttt.extractor.services.peertube.linkHandler.PeertubeSearchQueryHandlerFactory;
import org.chicha.ttt.extractor.services.peertube.linkHandler.PeertubeStreamLinkHandlerFactory;
import org.chicha.ttt.extractor.services.peertube.linkHandler.PeertubeTrendingLinkHandlerFactory;
import org.chicha.ttt.extractor.stream.StreamExtractor;
import org.chicha.ttt.extractor.subscription.SubscriptionExtractor;
import org.chicha.ttt.extractor.suggestion.SuggestionExtractor;

import java.util.List;

public class PeertubeService extends StreamingService {

    private PeertubeInstance instance;

    public PeertubeService(final int id) {
        this(id, PeertubeInstance.DEFAULT_INSTANCE);
    }

    public PeertubeService(final int id, final PeertubeInstance instance) {
        super(id, "PeerTube", asList(VIDEO, COMMENTS));
        this.instance = instance;
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return PeertubeStreamLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return PeertubeChannelLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return PeertubePlaylistLinkHandlerFactory.getInstance();
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return PeertubeSearchQueryHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getCommentsLHFactory() {
        return PeertubeCommentsLinkHandlerFactory.getInstance();
    }

    @Override
    public SearchExtractor getSearchExtractor(final SearchQueryHandler queryHandler) {
        final List<String> contentFilters = queryHandler.getContentFilters();
        return new PeertubeSearchExtractor(this, queryHandler,
                !contentFilters.isEmpty() && contentFilters.get(0).startsWith("sepia_"));
    }

    @Override
    public SuggestionExtractor getSuggestionExtractor() {
        return new PeertubeSuggestionExtractor(this);
    }

    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return null;
    }

    @Override
    public ChannelExtractor getChannelExtractor(final ListLinkHandler linkHandler)
            throws ExtractionException {

        if (linkHandler.getUrl().contains("/video-channels/")) {
            return new PeertubeChannelExtractor(this, linkHandler);
        } else {
            return new PeertubeAccountExtractor(this, linkHandler);
        }
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(final ListLinkHandler linkHandler)
            throws ExtractionException {
        return new PeertubePlaylistExtractor(this, linkHandler);
    }

    @Override
    public StreamExtractor getStreamExtractor(final LinkHandler linkHandler)
            throws ExtractionException {
        return new PeertubeStreamExtractor(this, linkHandler);
    }

    @Override
    public CommentsExtractor getCommentsExtractor(final ListLinkHandler linkHandler)
            throws ExtractionException {
        return new PeertubeCommentsExtractor(this, linkHandler);
    }

    @Override
    public String getBaseUrl() {
        return instance.getUrl();
    }

    public PeertubeInstance getInstance() {
        return this.instance;
    }

    public void setInstance(final PeertubeInstance instance) {
        this.instance = instance;
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        final KioskList.KioskExtractorFactory kioskFactory = (streamingService, url, id) ->
                new PeertubeTrendingExtractor(
                        PeertubeService.this,
                        new PeertubeTrendingLinkHandlerFactory().fromId(id),
                        id
                );

        final KioskList list = new KioskList(this);

        // add kiosks here e.g.:
        final PeertubeTrendingLinkHandlerFactory h = new PeertubeTrendingLinkHandlerFactory();
        try {
            list.addKioskEntry(kioskFactory, h, PeertubeTrendingLinkHandlerFactory.KIOSK_TRENDING);
            list.addKioskEntry(kioskFactory, h,
                    PeertubeTrendingLinkHandlerFactory.KIOSK_MOST_LIKED);
            list.addKioskEntry(kioskFactory, h, PeertubeTrendingLinkHandlerFactory.KIOSK_RECENT);
            list.addKioskEntry(kioskFactory, h, PeertubeTrendingLinkHandlerFactory.KIOSK_LOCAL);
            list.setDefaultKiosk(PeertubeTrendingLinkHandlerFactory.KIOSK_TRENDING);
        } catch (final Exception e) {
            throw new ExtractionException(e);
        }

        return list;
    }


}
