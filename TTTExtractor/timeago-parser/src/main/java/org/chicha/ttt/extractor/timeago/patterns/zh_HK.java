/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class zh_HK extends PatternsHolder {
    private static final String WORD_SEPARATOR = "";
    private static final String[]
            SECONDS  /**/ = {"秒前"},
            MINUTES  /**/ = {"分鐘前"},
            HOURS    /**/ = {"小時前"},
            DAYS     /**/ = {"天前"},
            WEEKS    /**/ = {"週前"},
            MONTHS   /**/ = {"個月前"},
            YEARS    /**/ = {"年前"};

    private static final zh_HK INSTANCE = new zh_HK();

    public static zh_HK getInstance() {
        return INSTANCE;
    }

    private zh_HK() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}