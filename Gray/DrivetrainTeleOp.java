/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
public class DrivetrainTeleOp extends LinearOpMode {
    DrivetrainHardware robot = new DrivetrainHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

      //  double xMove = gamepad1.left_stick_x;
     //   double yMove = gamepad1.left_stick_y;
     //   double rotate = gamepad1.right_stick_x;
        double left = -gamepad1.left_stick_y;
        double right = gamepad1.right_stick_y;
        double up = gamepad2.left_stick_y;
        boolean a = gamepad2.a;
        boolean b = gamepad2.b;
        boolean x = gamepad2.x;
        boolean lt = gamepad2.left_bumper;
        boolean rt = gamepad2.right_bumper;
        boolean rs = gamepad2.right_stick_button;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
        //    xMove = gamepad1.left_stick_x;
            //  yMove = gamepad1.left_stick_y;
        //    rotate = gamepad1.right_stick_x;
           // robot.travel(xMove, yMove, rotate);
            left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;
            up = gamepad2.left_stick_y;
            a = gamepad2.a;
            b = gamepad2.b;
            x = gamepad2.x;
            rt = gamepad2.right_bumper;
            lt = gamepad2.left_bumper;
            rs = gamepad2.right_stick_button;
            robot.travelTank(left, right);
            robot.liftWobble(up);
            robot.getData(telemetry);
            telemetry.addData("speed",robot.motors[0].getPower());
            if(a) {
                robot.distanceSensorServo.setPosition(0);
            }
            else if (b) {
                robot.distanceSensorServo.setPosition(1);
            }
            if(x){
                robot.dump();
            }
            telemetry.update();

            if(lt) {
                robot.unGrabGoal();
            }
            if (rt) {
                robot.wobbleHolder.setPower(-0.3);
            }

            if (!rt && !lt) {
                robot.wobbleHolder.setPower(0);
            }


            telemetry.addData("encoderCounts", robot.graberMotor.getCurrentPosition());
            telemetry.addData("isPressed", robot.notGrabTouch.isPressed());
            telemetry.addData("rt", rt);
            telemetry.addData("rs", rs);
            telemetry.update();


        }

    }

}
