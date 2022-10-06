package org.chicha.ttt.extractor.services.soundcloud;

import static org.chicha.ttt.extractor.StreamingService.ServiceInfo.MediaCapability.AUDIO;
import static org.chicha.ttt.extractor.StreamingService.ServiceInfo.MediaCapability.COMMENTS;
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
import org.chicha.ttt.extractor.localization.ContentCountry;
import org.chicha.ttt.extractor.playlist.PlaylistExtractor;
import org.chicha.ttt.extractor.search.SearchExtractor;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudChannelExtractor;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudChartsExtractor;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudCommentsExtractor;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudPlaylistExtractor;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudSearchExtractor;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudStreamExtractor;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudSubscriptionExtractor;
import org.chicha.ttt.extractor.services.soundcloud.extractors.SoundcloudSuggestionExtractor;
import org.chicha.ttt.extractor.services.soundcloud.linkHandler.SoundcloudChannelLinkHandlerFactory;
import org.chicha.ttt.extractor.services.soundcloud.linkHandler.SoundcloudChartsLinkHandlerFactory;
import org.chicha.ttt.extractor.services.soundcloud.linkHandler.SoundcloudCommentsLinkHandlerFactory;
import org.chicha.ttt.extractor.services.soundcloud.linkHandler.SoundcloudPlaylistLinkHandlerFactory;
import org.chicha.ttt.extractor.services.soundcloud.linkHandler.SoundcloudSearchQueryHandlerFactory;
import org.chicha.ttt.extractor.services.soundcloud.linkHandler.SoundcloudStreamLinkHandlerFactory;
import org.chicha.ttt.extractor.stream.StreamExtractor;
import org.chicha.ttt.extractor.subscription.SubscriptionExtractor;

import java.util.List;

public class SoundcloudService extends StreamingService {

    public SoundcloudService(final int id) {
        super(id, "SoundCloud", asList(AUDIO, COMMENTS));
    }

    @Override
    public String getBaseUrl() {
        return "https://soundcloud.com";
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return new SoundcloudSearchQueryHandlerFactory();
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return SoundcloudStreamLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return SoundcloudChannelLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return SoundcloudPlaylistLinkHandlerFactory.getInstance();
    }

    @Override
    public List<ContentCountry> getSupportedCountries() {
        // Country selector here: https://soundcloud.com/charts/top?genre=all-music
        return ContentCountry.listFrom(
                "AU", "CA", "DE", "FR", "GB", "IE", "NL", "NZ", "US"
        );
    }

    @Override
    public StreamExtractor getStreamExtractor(final LinkHandler linkHandler) {
        return new SoundcloudStreamExtractor(this, linkHandler);
    }

    @Override
    public ChannelExtractor getChannelExtractor(final ListLinkHandler linkHandler) {
        return new SoundcloudChannelExtractor(this, linkHandler);
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(final ListLinkHandler linkHandler) {
        return new SoundcloudPlaylistExtractor(this, linkHandler);
    }

    @Override
    public SearchExtractor getSearchExtractor(final SearchQueryHandler queryHandler) {
        return new SoundcloudSearchExtractor(this, queryHandler);
    }

    @Override
    public SoundcloudSuggestionExtractor getSuggestionExtractor() {
        return new SoundcloudSuggestionExtractor(this);
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        final KioskList.KioskExtractorFactory chartsFactory = (streamingService, url, id) ->
                new SoundcloudChartsExtractor(SoundcloudService.this,
                        new SoundcloudChartsLinkHandlerFactory().fromUrl(url), id);

        final KioskList list = new KioskList(this);

        // add kiosks here e.g.:
        final SoundcloudChartsLinkHandlerFactory h = new SoundcloudChartsLinkHandlerFactory();
        try {
            list.addKioskEntry(chartsFactory, h, "Top 50");
            list.addKioskEntry(chartsFactory, h, "New & hot");
            list.setDefaultKiosk("New & hot");
        } catch (final Exception e) {
            throw new ExtractionException(e);
        }

        return list;
    }

    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return new SoundcloudSubscriptionExtractor(this);
    }

    @Override
    public ListLinkHandlerFactory getCommentsLHFactory() {
        return SoundcloudCommentsLinkHandlerFactory.getInstance();
    }

    @Override
    public CommentsExtractor getCommentsExtractor(final ListLinkHandler linkHandler)
            throws ExtractionException {
        return new SoundcloudCommentsExtractor(this, linkHandler);
    }
}
