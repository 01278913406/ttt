/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class eu extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"segundo"},
            MINUTES  /**/ = {"minutu"},
            HOURS    /**/ = {"ordu", "ordubete"},
            DAYS     /**/ = {"egun"},
            WEEKS    /**/ = {"aste", "astebete"},
            MONTHS   /**/ = {"hilabete"},
            YEARS    /**/ = {"urte", "urtebete"};

    private static final eu INSTANCE = new eu();

    public static eu getInstance() {
        return INSTANCE;
    }

    private eu() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}