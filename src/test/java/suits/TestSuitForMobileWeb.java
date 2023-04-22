package suits;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import tests.ArticleTests;
import tests.ListsTests;
import tests.SearchTests;

@Suite
@SuiteDisplayName("My Test Suite for Mobile wiki page")
@SelectClasses({ArticleTests.class, SearchTests.class, ListsTests.class})
public class TestSuitForMobileWeb {
}
