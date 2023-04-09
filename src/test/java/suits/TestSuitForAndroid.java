package suits;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;
import tests.ArticleTests;
import tests.ChangeAppConditionsTests;
import tests.ListsTests;
import tests.SearchTests;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("My Test Suite for Android app")
@SelectClasses({ArticleTests.class, ChangeAppConditionsTests.class, SearchTests.class, ListsTests.class})
public class TestSuitForAndroid {

}
