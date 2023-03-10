package org.firstinspires.ftc.teamcode.RoadRunnterTest;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous (name = "a_test")
public class test extends LinearOpMode {

    private DcMotor leftArmDrive;
    private DcMotor rightArmDrive;
    private DcMotor turretDrive;
    private DcMotor grabberDrive;
    private Servo grab;

    //private int armPos;
    //endocer stuff

    @Override
    public void runOpMode() {

        leftArmDrive = hardwareMap.get(DcMotor.class, "leftArmMotor");
        rightArmDrive = hardwareMap.get(DcMotor.class, "rightArmMotor");
        turretDrive = hardwareMap.get(DcMotor.class, "turretMotor");
        grabberDrive = hardwareMap.get(DcMotor.class, "grabberMotor");
        grab = hardwareMap.get(Servo.class, "grabServo");
        leftArmDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArmDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabberDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArmDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        /*
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        armPos = 0;
        //encoder stuff

         */



        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(-61.25, -33, 0);

        drive.setPoseEstimate(startPose);

        //random line
        waitForStart();

        if (isStopRequested()) return;

        while (!isStopRequested()) {
            TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                    .addTemporalMarker(1, () -> {
                        grabberDrive.setTargetPosition(-500);
                        grabberDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        grabberDrive.setPower(.5);
                    })
                    /*
                    .addTemporalMarker(5, () -> {
                        turretDrive.setTargetPosition(-700);

                        turretDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                        turretDrive.setPower(0.5);

                    })

                     */
                    .waitSeconds(30)
                    .build();
            drive.followTrajectorySequence(trajSeq);
        }
    }


}
