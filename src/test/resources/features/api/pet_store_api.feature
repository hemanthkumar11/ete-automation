Feature: Pet Store Rest API

  @petstore
  Scenario Outline: Get Pet Information 1
    Given a rest api "PetStore"
    Given a header
      | Content-Type | application/json |
    And path parameters
      | petID | <petID> |
    When the system requests GET "/v2/pet/{petID}"
    Then the response code is 200
    And the response body contains
      | element | matcher | value     | type |
      | name    | equals  | <petName> | str  |

    Examples:
      | petID | petName  |
      | 167   | Spot     |
      | 267   | Whiskers |
      | 367   | Rover    |