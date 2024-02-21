package com.comsystem.homework;

import com.comsystem.homework.model.RobotAction;
import com.comsystem.homework.model.RobotPlan;
import com.comsystem.homework.robot.RobotOperations;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RobotOperationsTest {
    @Test
    void excavateStonesForDays() {
        RobotOperations robotOperations = new RobotOperations();

        //Test with zero days or with negative days (incorrect days).
        assertThrows(IllegalArgumentException.class,
                () -> robotOperations.excavateStonesForDays(0));

        assertThrows(IllegalArgumentException.class,
                () -> robotOperations.excavateStonesForDays(-5));

        //Test with 1 day and 2 days.
        RobotPlan robotPlanOne = robotOperations.excavateStonesForDays(1);
        assertEquals(1, robotPlanOne.numberOfDays());
        assertEquals(List.of(RobotAction.DIG), robotPlanOne.robotActions());

        RobotPlan robotPlanTwo = robotOperations.excavateStonesForDays(2);
        assertEquals(2, robotPlanTwo.numberOfDays());
        assertEquals(List.of(RobotAction.DIG, RobotAction.DIG), robotPlanTwo.robotActions());

        //Test with positive days (correct days).
        int days = 5;
        RobotPlan robotPlan = robotOperations.excavateStonesForDays(days);
        List<RobotAction> actions = robotPlan.robotActions();
        //The given numberOfDays must be the same as the number of days in the robot's plan.
        assertEquals(days, robotPlan.numberOfDays());
        //Start with CLONE action.
        assertEquals(RobotAction.CLONE, actions.get(0));
        //End with DIG action.
        assertEquals(RobotAction.DIG, actions.get(actions.size() - 1));
    }

    @Test
    void daysRequiredToCollectStones() {
        RobotOperations robotOperations = new RobotOperations();

        //Test with zero and negative number of stones.
        assertThrows(IllegalArgumentException.class,
                () -> robotOperations.daysRequiredToCollectStones(0));
        assertThrows(IllegalArgumentException.class,
                () -> robotOperations.daysRequiredToCollectStones(-1));

        //Test with positive number of stones.
        int numberOfStones = 25;
        RobotPlan robotPlan = robotOperations.daysRequiredToCollectStones(numberOfStones);

        //Test if the number of stones is more than 0.
        assertTrue(robotPlan.numberOfStones() > 0);
        //Test if the stones in the robotPlan match the input.
        assertEquals(numberOfStones, robotPlan.numberOfStones());
        //Test when number of required days < 0.
        assertThrows(IllegalArgumentException.class,
                () -> robotOperations.daysRequiredToCollectStones(-1));
        //Test when number of required days == 0.
        assertThrows(IllegalArgumentException.class,
                () -> robotOperations.daysRequiredToCollectStones(-1));
    }
}
