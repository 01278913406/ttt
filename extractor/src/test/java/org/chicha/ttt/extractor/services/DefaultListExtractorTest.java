package org.chicha.ttt.extractor.services;

import org.junit.jupiter.api.Test;
import org.chicha.ttt.extractor.InfoItem;
import org.chicha.ttt.extractor.ListExtractor;

import javax.annotation.Nullable;

import static org.chicha.ttt.extractor.services.DefaultTests.*;

public abstract class DefaultListExtractorTest<T extends ListExtractor<? extends InfoItem>> extends DefaultExtractorTest<T>
        implements BaseListExtractorTest {

    @Nullable
    public InfoItem.InfoType expectedInfoItemType() {
        return null;
    }

    public boolean expectedHasMoreItems() {
        return true;
    }

    @Test
    @Override
    public void testRelatedItems() throws Exception {
        final ListExtractor<? extends InfoItem> extractor = extractor();

        final InfoItem.InfoType expectedType = expectedInfoItemType();
        final ListExtractor.InfoItemsPage<? extends InfoItem> items = defaultTestRelatedItems(extractor);
        if (expectedType != null) {
            assertOnlyContainsType(items, expectedType);
        }
    }

    @Test
    @Override
    public void testMoreRelatedItems() throws Exception {
        final ListExtractor<? extends InfoItem> extractor = extractor();

        if (expectedHasMoreItems()) {
            final InfoItem.InfoType expectedType = expectedInfoItemType();
            final ListExtractor.InfoItemsPage<? extends InfoItem> items = defaultTestMoreItems(extractor);
            if (expectedType != null) {
                assertOnlyContainsType(items, expectedType);
            }
        } else {
            assertNoMoreItems(extractor);
        }
    }
}
