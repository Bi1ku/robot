package frc.robot.commands.LED;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LED;
import frc.util.LEDColor;

public class Wave extends CommandBase {
    private LED LED;
    private LEDColor color;
    private int waveLength;

    public Wave(LED LED, int waveLength, LEDColor color) {
        this.LED = LED;
        this.waveLength = waveLength;
        this.color = color;

        addRequirements(LED);
    }

    @Override
    public void execute() {
        this.LED.wave(this.waveLength, this.color);
    }
}
