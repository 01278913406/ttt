/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class fil extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"segundo"},
            MINUTES  /**/ = {"minuto"},
            HOURS    /**/ = {"oras"},
            DAYS     /**/ = {"araw"},
            WEEKS    /**/ = {"linggo"},
            MONTHS   /**/ = {"buwan"},
            YEARS    /**/ = {"taon"};

    private static final fil INSTANCE = new fil();

    public static fil getInstance() {
        return INSTANCE;
    }

    private fil() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}