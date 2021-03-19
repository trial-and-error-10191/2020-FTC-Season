package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class DrivetrainHardware {
    // Hardware Variables
    DcMotor[] motors = new DcMotor[4];

    public BNO055IMU imu;
    public Orientation angles = new Orientation();
    public Acceleration gravity = new Acceleration();
    public int correctHeading = 1;

    static final double     TURN_SPEED              = 0.5;     // Nominal half speed for better accuracy.
    static final double     HEADING_THRESHOLD       = 0.4 ;      // As tight as we can make it with an integer gyro
    static final double     P_TURN_COEFF            = 0.025;

    // Sensors
    public TouchSensor notGrabTouch;
    public DistanceSensor ringSensor;

    //Sevos
    public Servo dumpServo;
    public CRServo wobbleHolder;
    public Servo distanceSensorServo;
    public Servo distanceSensorServoRot;
    //public Servo loadingServo;

    double minSpeed = 0.125;
    double maxSpeedPos = 0.4;
    double maxSpeedMin = -0.4;
    double maxAcceleration = 0.025;
    double decceleration = 0.012;
    double j = 0.0006;
    boolean hasPecked = false;
    int cpr = 28;
    double gearratio = 40 * 80/100;
    double diameter = 4.125;
    double cpi = (cpr * gearratio) / (Math.PI * diameter);
    double bias = 0.5;
    double conversion = cpi * bias;
    boolean x = false;
    public double storePos = 0;
    public double fourRingPos = 0.2;
    public double oneRingPos = 1;


    public DcMotor graberMotor;

 //   public DcMotor pewPewMotor0;
 //   public DcMotor pewPewMotor1;

    public ElapsedTime runtime = new ElapsedTime();

    // Hardware Map Variables
    HardwareMap hwmap = null;

    public DrivetrainHardware() {
    }//Constructor

    public void init(HardwareMap ahwmap){
        hwmap = ahwmap;

        for (int i = 0; i < motors.length; i++){
            motors[i] = hwmap.get(DcMotor.class, "motor" + i);
            if (i % 2 == 0) { // even
                motors[i].setDirection(DcMotor.Direction.REVERSE);
            } else {
                motors[i].setDirection(DcMotor.Direction.FORWARD);
            }
            motors[i].setPower(0);
            motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        ringSensor = hwmap.get(DistanceSensor.class, "sensorRings");

        notGrabTouch = hwmap.get(TouchSensor.class, "grabTouch");

        distanceSensorServo  = hwmap.get(Servo.class, "SensorServo");

   //     loadingServo = hwmap.get(Servo.class, "loadingServo");

          wobbleHolder = hwmap.get(CRServo.class, "wobbleHolder");

          graberMotor = hwmap.get(DcMotor.class, "graberMotor");
          graberMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         graberMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         graberMotor.setPower(0);
        distanceSensorServo.setPosition(0);
/*
        pewPewMotor0 = hwmap.get(DcMotor.class, "pewPewMotor");
        pewPewMotor1= hwmap.get(DcMotor.class, "pewPewMotor1");
        pewPewMotor0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pewPewMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pewPewMotor0.setPower(0);
        pewPewMotor1.setPower(0);
*/

        dumpServo = hwmap.get(Servo.class, "dumpServo");

        dumpServo.setPosition(0);

      //  wobbleHolder.setPower(0);

        imuInit();

    }

  public void dump() {
        if (dumpServo.getPosition() == 0) {
          dumpServo.setPosition(1);
      }
      else {
           dumpServo.setPosition(0);
        }
    }
    /*
    public void load(Telemetry telemetry) {
        if (loadingServo.getPosition() == 1.0) {
            loadingServo.setPosition(0);
        }
        else if(loadingServo.getPosition() == 0){
            loadingServo.setPosition(1);
        }

    }

    public void fire() {
        if (pewPewMotor1.getPower() > 0.5) {
            pewPewMotor0.setPower(0);
            pewPewMotor1.setPower(0);
        }
        else if (pewPewMotor1.getPower() < 0.5) {
            pewPewMotor0.setPower(1);
            pewPewMotor1.setPower(1);
        }

    }
    */

    public void liftWobble(double wobblePower) {
        if(Math.abs(wobblePower) > 0.20) {
            graberMotor.setPower(wobblePower);
        }
        else {
            graberMotor.setPower(0);
        }
    }

    public void unGrabGoal() {
          wobbleHolder.setPower(0.3);
    }
   public void grabGoal(boolean pressed) {
        while (pressed) {
            wobbleHolder.setPower(-0.3);
        }
        wobbleHolder.setPower(0);
    }

    public void getData(Telemetry telemetry) {
        telemetry.addData("deviceName",ringSensor.getDeviceName() );
        telemetry.addData("range", String.format("%.01f mm", ringSensor.getDistance(DistanceUnit.MM)));
        telemetry.addData("range", String.format("%.01f cm", ringSensor.getDistance(DistanceUnit.CM)));
        telemetry.addData("range", String.format("%.01f m", ringSensor.getDistance(DistanceUnit.METER)));
        telemetry.addData("range", String.format("%.01f in", ringSensor.getDistance(DistanceUnit.INCH)));
    }

    public void FindRingsDown() {
        if(distanceSensorServo.getPosition() == storePos) {
            distanceSensorServo.setPosition(0.5);
        }
        else {
            distanceSensorServo.setPosition(1);
        }
    }
    public void FindRingsUp() {
        if(distanceSensorServo.getPosition() == storePos) {
            distanceSensorServo.setPosition(0);
        }
        else if(distanceSensorServo.getPosition() == fourRingPos){
            distanceSensorServo.setPosition(0);
        }
        else if (distanceSensorServo.getPosition() == oneRingPos) {
            distanceSensorServo.setPosition(0.5);
        }
    }

    public boolean isDrivetrainBusy() {
        for (int i = 0; i < motors.length; i++) {
            if (!motors[i].isBusy()) {
                return false;
            }
        }
        return true;
    }
    public void HoldText() {

    }
    public void chill() {
        while (!x) {

        }

    }
    public void travel(double translationX, double translationY, double rotation) {
        // Calculate velocities
        double v0 = translationY + translationX + rotation;
        double v1 = translationY - translationX - rotation;
        double v2 = translationY - translationX + rotation;
        double v3 = translationY + translationX - rotation;
        // See what the max is
        double maximum = Math.max(Math.max(Math.max(Math.abs(v0),Math.abs(v1)),Math.abs(v2)),Math.abs(v3));
        // If maximum is greater than available power, scale everything by maximum
        if(maximum > 1) {
            v0 = v0/maximum;
            v1 = v1/maximum;
            v2 = v2/maximum;
            v3 = v3/maximum;
        }
        // Set power to their velocities
        motors[0].setPower(v0);
        motors[1].setPower(v1);
        motors[2].setPower(v2);
        motors[3].setPower(v3);
    }
    public void travelTank(double leftSide, double rightSide) {

        leftSide=maxSpeedPos*leftSide;
        rightSide=maxSpeedPos*rightSide;

        motors[0].setPower(leftSide);
        motors[1].setPower(rightSide);
        motors[2].setPower(leftSide);
        motors[3].setPower(rightSide);

    }
    public void move(double inches, double desiredSpeed, Telemetry telemetry) {
        int move1 = (int)  (Math.round(inches * conversion));

        for(int i = 0; i < motors.length; i++) {
            motors[i].setTargetPosition(motors[i].getCurrentPosition()+move1);
            motors[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        double initialPosition = motors[0].getCurrentPosition();
        double currentPosition = 0;
        double realSpeed = 0;

        while (isDrivetrainBusy()) {
            currentPosition = motors[0].getCurrentPosition();
            if (currentPosition < initialPosition + move1 * 0.5) {
                if (realSpeed < desiredSpeed) {
                    realSpeed = realSpeed + maxAcceleration;
                } else {
                    realSpeed = desiredSpeed;
                }
            } else {
                if (realSpeed > minSpeed) {
                     decceleration = decceleration + j;
                    realSpeed = realSpeed - decceleration;
                } else {
                    realSpeed = minSpeed;
                }
            }
            setDrivetrainSpeed(realSpeed);
            telemetry.addData("speed", realSpeed);
            telemetry.addData("decceleration", decceleration);
            telemetry.update();
            telemetry.setAutoClear(false);
        }
        setDrivetrainSpeed(0);
    }
    public void imuInit() {
        BNO055IMU.Parameters imuParameters = new BNO055IMU.Parameters();

        this.imu = hwmap.get(BNO055IMU.class, "imu");
        imuParameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        imuParameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imuParameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        imuParameters.loggingEnabled      = true;
        imuParameters.loggingTag          = "IMU";
        imuParameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        this.imu.initialize(imuParameters);
    }

    public float getHeading() {
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        if (correctHeading == 1) {
            return angles.firstAngle;
        } else if (correctHeading == 2) {
            return angles.secondAngle;
        } else { // Using third angle by default
            return angles.thirdAngle;
        }
    }

    public void setDrivetrainSpeed(double p) {
        for(int i = 0; i < motors.length; i++) {
            motors[i].setPower(p);
        }
    }


    /*
    This function's purpose is simply to drive forward or backward.
    To drive backward, simply make the inches input negative.
     */

    public void rotate(double angle, double speed, Telemetry telemetry) {

        // keep looping while we are still active, and not on heading.
        while (!onHeading(speed, angle, P_TURN_COEFF, telemetry)) {
            // Update telemetry & Allow time for other processes to run.
            telemetry.update();
        }
    }

    /**
     * Perform one cycle of closed loop heading control.
     *
     * @param speed     Desired speed of turn.
     * @param angle     Absolute Angle (in Degrees) relative to last gyro resetg.
     *                  0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                  If a relative angle is required, add/subtract from current heading.
     * @param PCoeff    Proportional Gain coefficient
     * @return
     */
    boolean onHeading(double speed, double angle, double PCoeff, Telemetry telemetry) {
        for (int i = 0; i < motors.length; i++){
            motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            //leftSpeed   = -rightSpeed;

            if (Math.abs(rightSpeed) < 0.25) {
                rightSpeed = Math.signum(rightSpeed)*0.25;
            }
            leftSpeed   = -rightSpeed;

        }

        // Send desired speeds to motors.
        for(int i = 0; i < motors.length; i++) {
            if(i%2==0) {
                motors[i].setPower(leftSpeed);
            } else {
                motors[i].setPower(rightSpeed);
            }

        }

        // Display it for the driver.
        telemetry.addData("onTarget", onTarget);
        telemetry.addData("PCoeff", PCoeff);
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);
        telemetry.addData("heading", getHeading());
        telemetry.update();

        return onTarget;
    }

    /**
     * getError determines the error between the target angle and the robot's current heading
     * @param   targetAngle  Desired angle (relative to global reference established at last Gyro Reset).
     * @return  error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
     *          +ve error means the robot should turn LEFT (CCW) to reduce error.
     */
    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        //robotError = targetAngle - gyro.getIntegratedZValue();
        robotError = targetAngle - getHeading();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    /**
     * returns desired steering force.  +/- 1 range.  +ve = steer left
     * @param error   Error angle in robot relative degrees
     * @param PCoeff  Proportional Gain Coefficient
     * @return
     */
    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }
}