package org.chicha.ttt.extractor.exceptions;

public class SoundCloudGoPlusContentException extends ContentNotAvailableException {
    public SoundCloudGoPlusContentException() {
        super("This track is a SoundCloud Go+ track");
    }

    public SoundCloudGoPlusContentException(final Throwable cause) {
        super("This track is a SoundCloud Go+ track", cause);
    }
}
