package org.chicha.ttt.extractor.services.bandcamp.extractors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.chicha.ttt.extractor.Page;
import org.chicha.ttt.extractor.StreamingService;
import org.chicha.ttt.extractor.comments.CommentsExtractor;
import org.chicha.ttt.extractor.comments.CommentsInfoItem;
import org.chicha.ttt.extractor.comments.CommentsInfoItemsCollector;
import org.chicha.ttt.extractor.downloader.Downloader;
import org.chicha.ttt.extractor.exceptions.ExtractionException;
import org.chicha.ttt.extractor.linkhandler.ListLinkHandler;

import javax.annotation.Nonnull;
import java.io.IOException;

public class BandcampCommentsExtractor extends CommentsExtractor {

    private Document document;


    public BandcampCommentsExtractor(final StreamingService service,
                                     final ListLinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Override
    public void onFetchPage(@Nonnull final Downloader downloader)
            throws IOException, ExtractionException {
        document = Jsoup.parse(downloader.get(getLinkHandler().getUrl()).responseBody());
    }

    @Nonnull
    @Override
    public InfoItemsPage<CommentsInfoItem> getInitialPage()
            throws IOException, ExtractionException {

        final CommentsInfoItemsCollector collector = new CommentsInfoItemsCollector(getServiceId());

        final Elements writings = document.getElementsByClass("writing");

        for (final Element writing : writings) {
            collector.commit(new BandcampCommentsInfoItemExtractor(writing, getUrl()));
        }

        return new InfoItemsPage<>(collector, null);
    }

    @Override
    public InfoItemsPage<CommentsInfoItem> getPage(final Page page)
            throws IOException, ExtractionException {
        return null;
    }
}
