/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class ky extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"секунд"},
            MINUTES  /**/ = {"мүнөт"},
            HOURS    /**/ = {"саат"},
            DAYS     /**/ = {"күн"},
            WEEKS    /**/ = {"апта"},
            MONTHS   /**/ = {"ай"},
            YEARS    /**/ = {"жыл"};

    private static final ky INSTANCE = new ky();

    public static ky getInstance() {
        return INSTANCE;
    }

    private ky() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}