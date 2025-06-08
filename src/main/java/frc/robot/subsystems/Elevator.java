// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  
  

  public TalonFX objElevTalon = new TalonFX(0, getName());
  public TalonFXConfiguration objElevConfiguration = new TalonFXConfiguration();
  public StatusCode objStatusCode;

  @SuppressWarnings("rawtypes")
  StatusSignal objStatusSignal;

  /** Creates a new Elevator. */
  public Elevator() {

    // === Current Limits === \\
    objElevConfiguration.CurrentLimits.StatorCurrentLimit = 60.0;
    objElevConfiguration.CurrentLimits.StatorCurrentLimitEnable = true;

    // === Brake Mode === \\
    objElevConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    // === Open Loop Ramp === \\
    objElevConfiguration.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.25;

    // === Config Push Loop === \\
    objStatusCode = StatusCode.StatusCodeNotInitialized;
    for(int i = 1; i < 5; i = i + 1) {
      objStatusCode = objElevTalon.getConfigurator().apply(objElevConfiguration);
      if (objStatusCode.isOK()) {
        break;
      }
    }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Elevator Position", getElevPos());
    SmartDashboard.putNumber("Elevator Acceleration", getElevAccel());
  }

  public double getElevPos(){
    objStatusSignal = objElevTalon.getPosition();
    return objStatusSignal.getValueAsDouble();
  }

  public double getElevAccel(){
    objStatusSignal = objElevTalon.getAcceleration();
    return objStatusSignal.getValueAsDouble();
  }

  public void runElevPositive(){
    objElevTalon.set(0.1);
  }

  public void runElevNegative(){
    objElevTalon.set(-0.1);
  }

  public void runElevSubClass(double dSpeed){
    objElevTalon.set(dSpeed);
  }

  public void stopElev(){
    objElevTalon.stopMotor();
  }

}
