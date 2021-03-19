package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="AutoOp", group="chad")

public class DriveTrainAutoOp  extends LinearOpMode  {
    DrivetrainHardware robot = new DrivetrainHardware();


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();
        // Moving to the place where we can find rings

        robot.move(20, 0.25, telemetry);

        //Moving the arm down

        robot.distanceSensorServo.setPosition(1);
        sleep(2000);

        //Checking of their are any rings

        if (robot.ringSensor.getDistance(DistanceUnit.MM) > 200) {
            // no rings, moving to spot A after putting up the stick.

            robot.distanceSensorServo.setPosition(robot.storePos);
            robot.move(49, 0.3, telemetry);
            sleep(5000);
            robot.rotate(30, 0.4, telemetry);
            sleep(5000);

        } else if (robot.ringSensor.getDistance(DistanceUnit.MM) < 200) {
            //moving to see if it is one ring or 4 rings
            robot.distanceSensorServo.setPosition(robot.distanceSensorServo.getPosition() - 0.1);
            sleep(2000);
            if (robot.ringSensor.getDistance(DistanceUnit.MM) < 200) {
                // 4 rings moving to place C after putting up stick
                robot.distanceSensorServo.setPosition(robot.storePos);

                robot.move(95, 0.3, telemetry);
                sleep(2000);
                robot.rotate(20, 0.4, telemetry);
                sleep(2000);
                robot.dump();
                sleep(2000);
                robot.move(-40, 0.3, telemetry);

            } else if (robot.ringSensor.getDistance(DistanceUnit.MM) > 200) {
                // 1 ring moving to place b after putting up stick
                robot.distanceSensorServo.setPosition(robot.storePos);

                robot.move(49, 0.3, telemetry);
                sleep(2000);
                robot.rotate(-45, 0.3, telemetry);
                sleep(2000);
                robot.move(20, 0.3, telemetry);
                sleep(2000);
                robot.dump();
                sleep(2000);
                robot.move(-20, 0.3, telemetry);
            }
        }
    }
}
