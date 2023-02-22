package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class RaiseClaw extends CommandBase {

  private Elevator elevator;

  public RaiseClaw(Elevator elevator) {
    this.elevator = elevator;

    addRequirements(elevator);
  }

  @Override
  public void execute() {
    this.elevator.raiseClaw();
  }
}
