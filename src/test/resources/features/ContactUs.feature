Feature: WebdriverUniversity.com - Contact Us Page

#  Scenario: Valid Contact Us Form Submission
#    Given I navigate to the webdriveruniversity homepage
#    When I click on the contact us button
#    And I type a first name
#    And I type a last name
#    And I enter an email address
#    And I type a comment
#    And I click on the submit button
#    Then I should be presented with a successful contact us submission message
#
#  Scenario: Invalid Contact Us Form Submission
#    Given I navigate to the webdriveruniversity homepage
#    When I click on the contact us button
#    And I type a last name
#    And I enter an email address
#    And I type a comment
#    And I click on the submit button
#    Then I should be presented with a unsuccessful contact us submission message
#
#  Scenario: Invalid Contact Us Form Submission - Using Specific Data
#    Given I navigate to the webdriveruniversity homepage
#    When I click on the contact us button
#    And I type a specific first name "Sarah"
#    And I type a specific last name "Woods"
#    And I enter a specific email address "sarah_woods@example.com"
#    And I type specific text "Hello" and number 123 within the comment input field
#    And I click on the submit button
#    Then I should be presented with a successful contact us submission message

  Scenario: Valid Contact Us Form Submission - Using Random Data
    Given I navigate to the webdriveruniversity homepage
    When I click on the contact us button
    And I type a random first name
    And I type a random last name
    And I enter a random email address
    And I type a comment
    And I click on the submit button
    Then I should be presented with a successful contact us submission message