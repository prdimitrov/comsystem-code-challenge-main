package com.comsystem.homework.robot;


import com.comsystem.homework.model.RobotAction;
import com.comsystem.homework.model.RobotPlan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RobotOperations {
    /**
     * An algorithm that converts a number of days into an action plan.
     *
     * @param days the number of days that the robot can work
     * @return The action plan <em>must maximize</em> the number of stones that the robot will dig. In other words, this
     * algorithm must try to achieve the highest value of {@link RobotPlan#numberOfStones} possible for the
     * number of provided days. The value of {@link RobotPlan#numberOfDays} is equal to the input
     * days parameter
     * @see RobotPlan
     */
    public RobotPlan excavateStonesForDays(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("The number of days must be a positive number.");
        }
        int numberOfStones = 0;
        int numberOfClonedRobots = 0;
        List<RobotAction> robotActionsList = new ArrayList<>();

        switch (days) {
            case 1:
                robotActionsList.add(RobotAction.DIG);
                break;
            case 2:
                robotActionsList.add(RobotAction.DIG);
                robotActionsList.add(RobotAction.DIG);
                break;
            default:
                //Every first day, the FIRST ROBOT will clone.
                //Every next day, the FIRST ROBOT will clone itself, the OTHER ROBOTS will DIG.
            /*
            The for loop will begin from the second day, and will
            continue until the day before the last day.
            */
                for (int day = 0; day < days - 1; day++) {
                    //Every day the FIRST ROBOT will CLONE itself.
                    robotActionsList.add(RobotAction.CLONE);
                    if (numberOfClonedRobots > 0) {
                        for (int j = 0; j < numberOfClonedRobots; j++) {
                            //Add DIG action for each robot, without the FIRST ROBOT.
                            robotActionsList.add(RobotAction.DIG);
                        }
                    }
                    //Increasing the number of cloned robots at the end of the working day.
                    numberOfClonedRobots++;
                }
                //On the last day ALL ROBOTS will only DIG!.
                for (int i = 0; i <= numberOfClonedRobots; i++) {
                    robotActionsList.add(RobotAction.DIG);
                }
                //Here is the first robot also making the DIG action.
                robotActionsList.add(RobotAction.DIG);
                break;
        }
        //This for loop increases the number of stones.
        for (RobotAction action : robotActionsList) {
            if (action == RobotAction.DIG) {
                numberOfStones++;
            }
        }
        return new RobotPlan(days, numberOfStones, robotActionsList);
    }

    /**
     * An algorithm that converts a number of stones into an action plan. Essentially this algorithm is the inverse of
     * {@link #excavateStonesForDays(int)}.
     *
     * @param numberOfStones the number of stones the robot has to collect
     * @return The action plan <em>must minimize</em> the number of days necessary for the robot to dig the
     * provided number of stones. In other words, this algorithm must try to achieve the lowest value of
     * {@link RobotPlan#numberOfDays} possible for the number of provided stones. The value of
     * {@link RobotPlan#numberOfStones} is equal to the numberOfStones parameter
     * @see RobotPlan
     */
    public RobotPlan daysRequiredToCollectStones(int numberOfStones) {
        if (numberOfStones <= 0) {
            throw new IllegalArgumentException("The number of stones must be a positive number.");
        }

        List<RobotAction> robotActionList = new ArrayList<>();
        int totalDays = 0;
        int remainingStones = numberOfStones;

        while (remainingStones > 0) {
            totalDays++;
            // If remaining stones can be collected in the current day.
            if (remainingStones <= totalDays) {
                for (int i = 0; i < remainingStones; i++) {
                    robotActionList.add(RobotAction.DIG);
                }
                // All stones collected.
                remainingStones = 0;
            } else {
                //Decrease stones collected on this day.
                remainingStones -= totalDays;
                // Clone the FIRST ROBOT.
                robotActionList.add(RobotAction.CLONE);
                for (int i = 0; i < totalDays; i++) {
                    //Add a DIG action.
                    robotActionList.add(RobotAction.DIG);
                }
            }
        }
        return new RobotPlan(totalDays, numberOfStones, robotActionList);
    }
}
