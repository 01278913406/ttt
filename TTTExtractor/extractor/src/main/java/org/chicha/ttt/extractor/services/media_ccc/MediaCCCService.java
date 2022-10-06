package org.chicha.ttt.extractor.services.media_ccc;

import static org.chicha.ttt.extractor.StreamingService.ServiceInfo.MediaCapability.AUDIO;
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
import org.chicha.ttt.extractor.services.media_ccc.extractors.MediaCCCConferenceExtractor;
import org.chicha.ttt.extractor.services.media_ccc.extractors.MediaCCCConferenceKiosk;
import org.chicha.ttt.extractor.services.media_ccc.extractors.MediaCCCLiveStreamExtractor;
import org.chicha.ttt.extractor.services.media_ccc.extractors.MediaCCCLiveStreamKiosk;
import org.chicha.ttt.extractor.services.media_ccc.extractors.MediaCCCParsingHelper;
import org.chicha.ttt.extractor.services.media_ccc.extractors.MediaCCCRecentKiosk;
import org.chicha.ttt.extractor.services.media_ccc.extractors.MediaCCCSearchExtractor;
import org.chicha.ttt.extractor.services.media_ccc.extractors.MediaCCCStreamExtractor;
import org.chicha.ttt.extractor.services.media_ccc.linkHandler.MediaCCCConferenceLinkHandlerFactory;
import org.chicha.ttt.extractor.services.media_ccc.linkHandler.MediaCCCConferencesListLinkHandlerFactory;
import org.chicha.ttt.extractor.services.media_ccc.linkHandler.MediaCCCLiveListLinkHandlerFactory;
import org.chicha.ttt.extractor.services.media_ccc.linkHandler.MediaCCCRecentListLinkHandlerFactory;
import org.chicha.ttt.extractor.services.media_ccc.linkHandler.MediaCCCSearchQueryHandlerFactory;
import org.chicha.ttt.extractor.services.media_ccc.linkHandler.MediaCCCStreamLinkHandlerFactory;
import org.chicha.ttt.extractor.stream.StreamExtractor;
import org.chicha.ttt.extractor.subscription.SubscriptionExtractor;
import org.chicha.ttt.extractor.suggestion.SuggestionExtractor;

public class MediaCCCService extends StreamingService {
    public MediaCCCService(final int id) {
        super(id, "media.ccc.de", asList(AUDIO, VIDEO));
    }

    @Override
    public SearchExtractor getSearchExtractor(final SearchQueryHandler query) {
        return new MediaCCCSearchExtractor(this, query);
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return new MediaCCCStreamLinkHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return new MediaCCCConferenceLinkHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return null;
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return new MediaCCCSearchQueryHandlerFactory();
    }

    @Override
    public StreamExtractor getStreamExtractor(final LinkHandler linkHandler) {
        if (MediaCCCParsingHelper.isLiveStreamId(linkHandler.getId())) {
            return new MediaCCCLiveStreamExtractor(this, linkHandler);
        }
        return new MediaCCCStreamExtractor(this, linkHandler);
    }

    @Override
    public ChannelExtractor getChannelExtractor(final ListLinkHandler linkHandler) {
        return new MediaCCCConferenceExtractor(this, linkHandler);
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(final ListLinkHandler linkHandler) {
        return null;
    }

    @Override
    public SuggestionExtractor getSuggestionExtractor() {
        return null;
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        final KioskList list = new KioskList(this);

        // add kiosks here e.g.:
        try {
            list.addKioskEntry(
                    (streamingService, url, kioskId) -> new MediaCCCConferenceKiosk(
                            MediaCCCService.this,
                            new MediaCCCConferencesListLinkHandlerFactory().fromUrl(url),
                            kioskId
                    ),
                    new MediaCCCConferencesListLinkHandlerFactory(),
                    "conferences"
            );

            list.addKioskEntry(
                    (streamingService, url, kioskId) -> new MediaCCCRecentKiosk(
                            MediaCCCService.this,
                            new MediaCCCRecentListLinkHandlerFactory().fromUrl(url),
                            kioskId
                    ),
                    new MediaCCCRecentListLinkHandlerFactory(),
                    "recent"
            );

            list.addKioskEntry(
                    (streamingService, url, kioskId) -> new MediaCCCLiveStreamKiosk(
                            MediaCCCService.this,
                            new MediaCCCLiveListLinkHandlerFactory().fromUrl(url),
                            kioskId
                    ),
                    new MediaCCCLiveListLinkHandlerFactory(),
                    "live"
            );

            list.setDefaultKiosk("recent");
        } catch (final Exception e) {
            throw new ExtractionException(e);
        }

        return list;
    }

    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return null;
    }

    @Override
    public ListLinkHandlerFactory getCommentsLHFactory() {
        return null;
    }

    @Override
    public CommentsExtractor getCommentsExtractor(final ListLinkHandler linkHandler) {
        return null;
    }

    @Override
    public String getBaseUrl() {
        return "https://media.ccc.de";
    }

}
