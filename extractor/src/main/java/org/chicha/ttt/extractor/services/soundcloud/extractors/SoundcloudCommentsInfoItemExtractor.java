package org.chicha.ttt.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonObject;
import org.chicha.ttt.extractor.comments.CommentsInfoItemExtractor;
import org.chicha.ttt.extractor.exceptions.ParsingException;
import org.chicha.ttt.extractor.localization.DateWrapper;
import org.chicha.ttt.extractor.services.soundcloud.SoundcloudParsingHelper;

import javax.annotation.Nullable;
import java.util.Objects;

public class SoundcloudCommentsInfoItemExtractor implements CommentsInfoItemExtractor {
    private final JsonObject json;
    private final String url;

    public SoundcloudCommentsInfoItemExtractor(final JsonObject json, final String url) {
        this.json = json;
        this.url = url;
    }

    @Override
    public String getCommentId() {
        return Objects.toString(json.getLong("id"), null);
    }

    @Override
    public String getCommentText() {
        return json.getString("body");
    }

    @Override
    public String getUploaderName() {
        return json.getObject("user").getString("username");
    }

    @Override
    public String getUploaderAvatarUrl() {
        return json.getObject("user").getString("avatar_url");
    }

    @Override
    public boolean isUploaderVerified() throws ParsingException {
        return json.getObject("user").getBoolean("verified");
    }

    @Override
    public int getStreamPosition() throws ParsingException {
        return json.getInt("timestamp") / 1000; // convert milliseconds to seconds
    }

    @Override
    public String getUploaderUrl() {
        return json.getObject("user").getString("permalink_url");
    }

    @Override
    public String getTextualUploadDate() {
        return json.getString("created_at");
    }

    @Nullable
    @Override
    public DateWrapper getUploadDate() throws ParsingException {
        return new DateWrapper(SoundcloudParsingHelper.parseDateFrom(getTextualUploadDate()));
    }

    @Override
    public String getName() throws ParsingException {
        return json.getObject("user").getString("permalink");
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getThumbnailUrl() {
        return json.getObject("user").getString("avatar_url");
    }
}
