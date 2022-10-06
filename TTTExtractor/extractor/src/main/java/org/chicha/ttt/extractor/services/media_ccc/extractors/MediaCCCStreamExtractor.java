package org.chicha.ttt.extractor.services.media_ccc.extractors;

import static org.chicha.ttt.extractor.stream.AudioStream.UNKNOWN_BITRATE;
import static org.chicha.ttt.extractor.stream.Stream.ID_UNKNOWN;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;

import org.chicha.ttt.extractor.MediaFormat;
import org.chicha.ttt.extractor.StreamingService;
import org.chicha.ttt.extractor.downloader.Downloader;
import org.chicha.ttt.extractor.exceptions.ExtractionException;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.linkhandler.LinkHandler;
import org.chicha.ttt.extractor.localization.DateWrapper;
import org.chicha.ttt.extractor.localization.Localization;
import org.chicha.ttt.extractor.services.media_ccc.linkHandler.MediaCCCConferenceLinkHandlerFactory;
import org.chicha.ttt.extractor.services.media_ccc.linkHandler.MediaCCCStreamLinkHandlerFactory;
import org.chicha.ttt.extractor.stream.AudioStream;
import org.chicha.ttt.extractor.stream.Description;
import org.chicha.ttt.extractor.stream.StreamExtractor;
import org.chicha.ttt.extractor.stream.StreamType;
import org.chicha.ttt.extractor.stream.VideoStream;
import org.chicha.ttt.extractor.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

public class MediaCCCStreamExtractor extends StreamExtractor {
    private JsonObject data;
    private JsonObject conferenceData;

    public MediaCCCStreamExtractor(final StreamingService service, final LinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Nonnull
    @Override
    public String getTextualUploadDate() {
        return data.getString("release_date");
    }

    @Nonnull
    @Override
    public DateWrapper getUploadDate() throws ParsingException {
        return new DateWrapper(MediaCCCParsingHelper.parseDateFrom(getTextualUploadDate()));
    }

    @Nonnull
    @Override
    public String getThumbnailUrl() {
        return data.getString("thumb_url");
    }

    @Nonnull
    @Override
    public Description getDescription() {
        return new Description(data.getString("description"), Description.PLAIN_TEXT);
    }

    @Override
    public long getLength() {
        return data.getInt("length");
    }

    @Override
    public long getViewCount() {
        return data.getInt("view_count");
    }

    @Nonnull
    @Override
    public String getUploaderUrl() {
        return MediaCCCConferenceLinkHandlerFactory.CONFERENCE_PATH + getUploaderName();
    }

    @Nonnull
    @Override
    public String getUploaderName() {
        return data.getString("conference_url")
                .replaceFirst("https://(api\\.)?media\\.ccc\\.de/public/conferences/", "");
    }

    @Nonnull
    @Override
    public String getUploaderAvatarUrl() {
        return conferenceData.getString("logo_url");
    }

    @Override
    public List<AudioStream> getAudioStreams() throws ExtractionException {
        final JsonArray recordings = data.getArray("recordings");
        final List<AudioStream> audioStreams = new ArrayList<>();
        for (int i = 0; i < recordings.size(); i++) {
            final JsonObject recording = recordings.getObject(i);
            final String mimeType = recording.getString("mime_type");
            if (mimeType.startsWith("audio")) {
                // First we need to resolve the actual video data from the CDN
                final MediaFormat mediaFormat;
                if (mimeType.endsWith("opus")) {
                    mediaFormat = MediaFormat.OPUS;
                } else if (mimeType.endsWith("mpeg")) {
                    mediaFormat = MediaFormat.MP3;
                } else if (mimeType.endsWith("ogg")) {
                    mediaFormat = MediaFormat.OGG;
                } else {
                    mediaFormat = null;
                }

                // Not checking containsSimilarStream here, since MediaCCC does not provide enough
                // information to decide whether two streams are similar. Hence that method would
                // always return false, e.g. even for different language variations.
                audioStreams.add(new AudioStream.Builder()
                        .setId(recording.getString("filename", ID_UNKNOWN))
                        .setContent(recording.getString("recording_url"), true)
                        .setMediaFormat(mediaFormat)
                        .setAverageBitrate(UNKNOWN_BITRATE)
                        .build());
            }
        }
        return audioStreams;
    }

    @Override
    public List<VideoStream> getVideoStreams() throws ExtractionException {
        final JsonArray recordings = data.getArray("recordings");
        final List<VideoStream> videoStreams = new ArrayList<>();
        for (int i = 0; i < recordings.size(); i++) {
            final JsonObject recording = recordings.getObject(i);
            final String mimeType = recording.getString("mime_type");
            if (mimeType.startsWith("video")) {
                // First we need to resolve the actual video data from the CDN
                final MediaFormat mediaFormat;
                if (mimeType.endsWith("webm")) {
                    mediaFormat = MediaFormat.WEBM;
                } else if (mimeType.endsWith("mp4")) {
                    mediaFormat = MediaFormat.MPEG_4;
                } else {
                    mediaFormat = null;
                }

                // Not checking containsSimilarStream here, since MediaCCC does not provide enough
                // information to decide whether two streams are similar. Hence that method would
                // always return false, e.g. even for different language variations.
                videoStreams.add(new VideoStream.Builder()
                        .setId(recording.getString("filename", ID_UNKNOWN))
                        .setContent(recording.getString("recording_url"), true)
                        .setIsVideoOnly(false)
                        .setMediaFormat(mediaFormat)
                        .setResolution(recording.getInt("height") + "p")
                        .build());
            }
        }

        return videoStreams;
    }

    @Override
    public List<VideoStream> getVideoOnlyStreams() {
        return Collections.emptyList();
    }

    @Override
    public StreamType getStreamType() {
        return StreamType.VIDEO_STREAM;
    }

    @Override
    public void onFetchPage(@Nonnull final Downloader downloader)
            throws IOException, ExtractionException {
        final String videoUrl = MediaCCCStreamLinkHandlerFactory.VIDEO_API_ENDPOINT + getId();
        try {
            data = JsonParser.object().from(downloader.get(videoUrl).responseBody());
            conferenceData = JsonParser.object()
                    .from(downloader.get(data.getString("conference_url")).responseBody());
        } catch (final JsonParserException jpe) {
            throw new ExtractionException("Could not parse json returned by URL: " + videoUrl,
                    jpe);
        }
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return data.getString("title");
    }

    @Nonnull
    @Override
    public String getOriginalUrl() {
        return data.getString("frontend_link");
    }

    @Override
    public Locale getLanguageInfo() throws ParsingException {
        return Localization.getLocaleFromThreeLetterCode(data.getString("original_language"));
    }

    @Nonnull
    @Override
    public List<String> getTags() {
        return JsonUtils.getStringListFromJsonArray(data.getArray("tags"));
    }
}
