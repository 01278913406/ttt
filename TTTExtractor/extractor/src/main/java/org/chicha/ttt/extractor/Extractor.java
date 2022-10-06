package org.chicha.ttt.extractor;

import org.chicha.ttt.extractor.downloader.Downloader;
import org.chicha.ttt.extractor.exceptions.ExtractionException;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.linkhandler.LinkHandler;
import org.chicha.ttt.extractor.localization.ContentCountry;
import org.chicha.ttt.extractor.localization.Localization;
import org.chicha.ttt.extractor.localization.TimeAgoParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.io.IOException;
import java.util.Objects;

public abstract class Extractor {
    /**
     * {@link StreamingService} currently related to this extractor.<br>
     * Useful for getting other things from a service (like the url handlers for
     * cleaning/accepting/get id from urls).
     */
    private final StreamingService service;
    private final LinkHandler linkHandler;

    @Nullable
    private Localization forcedLocalization = null;
    @Nullable
    private ContentCountry forcedContentCountry = null;

    private boolean pageFetched = false;
    // called like this to prevent checkstyle errors about "hiding a field"
    private final Downloader downloader;

    protected Extractor(final StreamingService service, final LinkHandler linkHandler) {
        this.service = Objects.requireNonNull(service, "service is null");
        this.linkHandler = Objects.requireNonNull(linkHandler, "LinkHandler is null");
        this.downloader = Objects.requireNonNull(NewPipe.getDownloader(), "downloader is null");
    }

    /**
     * @return The {@link LinkHandler} of the current extractor object (e.g. a ChannelExtractor
     *         should return a channel url handler).
     */
    @Nonnull
    public LinkHandler getLinkHandler() {
        return linkHandler;
    }

    /**
     * Fetch the current page.
     *
     * @throws IOException         if the page can not be loaded
     * @throws ExtractionException if the pages content is not understood
     */
    public void fetchPage() throws IOException, ExtractionException {
        if (pageFetched) {
            return;
        }
        onFetchPage(downloader);
        pageFetched = true;
    }

    protected void assertPageFetched() {
        if (!pageFetched) {
            throw new IllegalStateException("Page is not fetched. Make sure you call fetchPage()");
        }
    }

    protected boolean isPageFetched() {
        return pageFetched;
    }

    /**
     * Fetch the current page.
     *
     * @param downloader the downloader to use
     * @throws IOException         if the page can not be loaded
     * @throws ExtractionException if the pages content is not understood
     */
    @SuppressWarnings("HiddenField")
    public abstract void onFetchPage(@Nonnull Downloader downloader)
            throws IOException, ExtractionException;

    @Nonnull
    public String getId() throws ParsingException {
        return linkHandler.getId();
    }

    /**
     * Get the name
     *
     * @return the name
     * @throws ParsingException if the name cannot be extracted
     */
    @Nonnull
    public abstract String getName() throws ParsingException;

    @Nonnull
    public String getOriginalUrl() throws ParsingException {
        return linkHandler.getOriginalUrl();
    }

    @Nonnull
    public String getUrl() throws ParsingException {
        return linkHandler.getUrl();
    }

    @Nonnull
    public String getBaseUrl() throws ParsingException {
        return linkHandler.getBaseUrl();
    }

    @Nonnull
    public StreamingService getService() {
        return service;
    }

    public int getServiceId() {
        return service.getServiceId();
    }

    public Downloader getDownloader() {
        return downloader;
    }

    /*//////////////////////////////////////////////////////////////////////////
    // Localization
    //////////////////////////////////////////////////////////////////////////*/

    public void forceLocalization(final Localization localization) {
        this.forcedLocalization = localization;
    }

    public void forceContentCountry(final ContentCountry contentCountry) {
        this.forcedContentCountry = contentCountry;
    }

    @Nonnull
    public Localization getExtractorLocalization() {
        return forcedLocalization == null ? getService().getLocalization() : forcedLocalization;
    }

    @Nonnull
    public ContentCountry getExtractorContentCountry() {
        return forcedContentCountry == null ? getService().getContentCountry()
                : forcedContentCountry;
    }

    @Nonnull
    public TimeAgoParser getTimeAgoParser() {
        return getService().getTimeAgoParser(getExtractorLocalization());
    }
}
