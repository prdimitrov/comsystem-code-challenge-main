package com.comsystem.homework.rest;

import com.comsystem.homework.model.RobotPlan;
import com.comsystem.homework.robot.RobotOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/robot/operation")
public final class RobotRestController {
    //Calling the RobotOperations class.
    public RobotRestController(RobotOperations robotOperations) {
        this.robotOperations = robotOperations;
    }
    RobotOperations robotOperations;
    /**
     * This method exposes the functionality of {@link RobotOperations#excavateStonesForDays(int)} via HTTP
     */
    @PostMapping("/excavation")
    public ResponseEntity<RobotPlan> excavateStones(@RequestParam Integer numberOfDays) {
        RobotPlan robotPlan = robotOperations.excavateStonesForDays(numberOfDays);
        return ResponseEntity.ok(robotPlan);
    }
    /**
     * This method exposes the functionality of {@link RobotOperations#daysRequiredToCollectStones(int)} via HTTP
     */
    @PostMapping("/approximation")
    public ResponseEntity<RobotPlan> approximateDays(@RequestParam Integer numberOfStones) {
        RobotPlan robotPlan = robotOperations.daysRequiredToCollectStones(numberOfStones);
        return ResponseEntity.ok(robotPlan);
    }
}
