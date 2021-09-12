Feature: Delegate Journey Planner
  As an interested conference delegate
  I want to view journey details to the venue
  So that I can find the best way of getting to the conference

  @journeyplanner
  Scenario: Check Journey Manchester by car
    Given the application "HealthConference"
    When a conference delegate checks their travel details from "Manchester, UK" by "car"
    Then the correct journey information will be calculated as "352 km" and "4 hours 0 mins"