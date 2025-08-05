Feature: WebdriverUniversity.com - Login Page

  Scenario Outline: Validate  valid & invalid login credentials
    Given I navigate to the webdriveruniversity homepage
    When I click on the login portal button
    And I type a user name '<username>' and a password '<password>'
    And I click on the login button
    Then I should be presented with an alert box which contains text '<expectedAlertText>'
    @wip
    Examples:
      | username  | password     | expectedAlertText    |
      |           |              | validation failed    |
      | webdriver |              | validation failed    |
      |           | webdriver123 | validation failed    |
      | webdriver | webdriver123 | validation succeeded |