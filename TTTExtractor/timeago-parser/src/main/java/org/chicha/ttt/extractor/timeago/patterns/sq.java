/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class sq extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"sekonda", "sekondë"},
            MINUTES  /**/ = {"minuta", "minutë"},
            HOURS    /**/ = {"orë"},
            DAYS     /**/ = {"ditë"},
            WEEKS    /**/ = {"javë"},
            MONTHS   /**/ = {"muaj"},
            YEARS    /**/ = {"vit", "vjet"};

    private static final sq INSTANCE = new sq();

    public static sq getInstance() {
        return INSTANCE;
    }

    private sq() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}