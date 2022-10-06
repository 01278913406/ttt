package org.chicha.ttt.extractor.services;

@SuppressWarnings("unused")
public interface BaseSearchExtractorTest extends BaseListExtractorTest {
    void testSearchString() throws Exception;
    void testSearchSuggestion() throws Exception;
    void testSearchCorrected() throws Exception;
}
