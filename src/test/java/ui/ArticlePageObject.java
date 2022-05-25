package ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {
  protected static String
          TITLE,
          FOOTER_ELEMENT,
          OPTIONS_BUTTON,
          OPTIONS_ADD_TO_MY_LIST_BUTTON,
          OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
          ADD_TO_MY_LIST_OVERLAY,
          MY_LIST_NAME_INPUT,
          MY_LIST_OK_BUTTON,
          CLOSE_ARTICLE_BUTTON;

  public ArticlePageObject(RemoteWebDriver driver) {
    super(driver);
  }

  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(TITLE, "Cannot find title article on a page", 15);
  }

  public String getArticleTitle() {
    WebElement title_element = waitForTitleElement();
    if (Platform.getInstance().isAndroid()) {
      return title_element.getAttribute("text");
    } else if (Platform.getInstance().isIOS()) {
      return title_element.getAttribute("name");
    } else {
      return title_element.getText();
    }
  }

  public String getArticleTitleNoWait() {
    WebElement title_element = waitForTitleElement();
    if (Platform.getInstance().isAndroid()) {
      return title_element.getAttribute("text");
    } else if (Platform.getInstance().isIOS()) {
      return title_element.getAttribute("name");
    } else {
      return title_element.getText();
    }
  }

  public void swipeToFooter() {
    if (Platform.getInstance().isAndroid()) {
      this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 40);
    } else if (Platform.getInstance().isIOS()) {
      this.swipeUpTillElementAppear(FOOTER_ELEMENT, "Cannot find the end of article", 40);
    } else {
      this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of article", 40);
    }
  }

  public void addArticleToMyList(String name_of_folder) {
    this.waitForElementAndClick(
            OPTIONS_BUTTON,
            "Cannot find button to open article options",
            15);

    this.waitForElementAndClick(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "Cannot find option to add article to reading list",
            15);

    this.waitForElementAndClick(
            ADD_TO_MY_LIST_OVERLAY,
            "Cannot find 'Got it' tip overlay",
            15);

    this.waitForElementAndClear(
            MY_LIST_NAME_INPUT,
            "Cannot find input to set name of article's folder",
            15);

    this.waitForElementAndSendKeys(
            MY_LIST_NAME_INPUT,
            name_of_folder,
            "Cannot put text into article's folder input",
            15);

    this.waitForElementAndClick(
            MY_LIST_OK_BUTTON,
            "Cannot press OK button",
            15);
  }

  public void addArticleToMySaved() {
    if (Platform.getInstance().isMW()) {
      this.removeArticleFromSavedIfItAdded();
    }
    this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 15);
  }

  public void removeArticleFromSavedIfItAdded() {
    if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
      this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON, "Cannot click button to remove an article from saved", 5);
    }
    this.waitForElementPresent(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find button to add an article to saved list after removing it from this list before", 5);
  }

  public void closeArticle() {
    if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
      this.waitForElementAndClick(
              CLOSE_ARTICLE_BUTTON,
              "Cannot close article, cannot find X link",
              15);
    } else {
      System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
    }
  }
}
