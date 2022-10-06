/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class zh_TW extends PatternsHolder {
    private static final String WORD_SEPARATOR = "";
    private static final String[]
            SECONDS  /**/ = {"秒前"},
            MINUTES  /**/ = {"分鐘前"},
            HOURS    /**/ = {"小時前"},
            DAYS     /**/ = {"天前"},
            WEEKS    /**/ = {"週前"},
            MONTHS   /**/ = {"個月前"},
            YEARS    /**/ = {"年前"};

    private static final zh_TW INSTANCE = new zh_TW();

    public static zh_TW getInstance() {
        return INSTANCE;
    }

    private zh_TW() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}