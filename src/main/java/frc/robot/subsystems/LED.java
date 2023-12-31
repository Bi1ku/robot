package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.util.LEDColor;

public class LED extends SubsystemBase {

  private AddressableLED LEDStrip;
  private AddressableLEDBuffer LEDBuffer;

  private int waveIndex;
  private int currentColor; // index of colors array

  private static int start = 0;

  public LED(int LEDLength) {
    this.waveIndex = 0;
    this.currentColor = 0;

    this.LEDStrip = new AddressableLED(0);
    this.LEDBuffer = new AddressableLEDBuffer(LEDLength);

    this.LEDStrip.setLength(this.LEDBuffer.getLength());
    this.LEDStrip.setData(this.LEDBuffer);
    this.LEDStrip.start();
  }

  public void setColor(LEDColor color) {
    for (byte i = 0; i < this.LEDBuffer.getLength(); i++) {
      this.LEDBuffer.setRGB(
          i,
          color.getRed(),
          color.getGreen(),
          color.getBlue()
        );
    }

    this.LEDStrip.setData(this.LEDBuffer);
  }

  public void waveLoop(int waveLength, LEDColor[] colors) {
    int red = colors[this.currentColor].getRed();
    int green = colors[this.currentColor].getGreen();
    int blue = colors[this.currentColor].getBlue();

    if (waveIndex == 0) {
      for (byte i = 0; i <= waveLength; i++) {
        this.LEDBuffer.setRGB(i, red, green, blue);
      }
    } else {
      if (this.waveIndex > 0) this.LEDBuffer.setRGB(
          this.waveIndex - 1,
          0,
          0,
          0
        );

      if (this.waveIndex + waveLength < this.LEDBuffer.getLength() - 1) {
        this.LEDBuffer.setRGB(this.waveIndex + waveLength, red, green, blue);
      } else {
        LEDColor nextColor =
          colors[this.currentColor + 1 > colors.length - 1
              ? 0
              : this.currentColor + 1];
        this.LEDBuffer.setRGB(
            this.waveIndex + waveLength - this.LEDBuffer.getLength() + 1,
            nextColor.getRed(),
            nextColor.getGreen(),
            nextColor.getBlue()
          );
      }

      if (this.waveIndex > this.LEDBuffer.getLength() - 1) {
        this.waveIndex = -1;
        this.currentColor =
          this.currentColor + 1 > colors.length - 1 ? 0 : this.currentColor + 1;
      }
    }
    waveIndex += 1;

    this.LEDStrip.setData(this.LEDBuffer);
  }

  public void waveIn(LEDColor color) {
    int red = color.getRed();
    int green = color.getGreen();
    int blue = color.getBlue();

    this.LEDBuffer.setRGB(waveIndex, red, green, blue);
    this.waveIndex += this.waveIndex + 1 < this.LEDBuffer.getLength() ? 1 : 0;

    this.LEDStrip.setData(this.LEDBuffer);
  }

  public void rainbow() {
    for (int i = 0; i < this.LEDBuffer.getLength(); i++) {
      final var hue = (start + (i * 180 / this.LEDBuffer.getLength())) % 180;
      this.LEDBuffer.setHSV(i, hue, 255, 128);
    }
    this.LEDStrip.setData(this.LEDBuffer);
    start %= 180;
    start += 5;
  }

  public void endgameLED(double time) {
    if (time > 105 && time < 110) {
      this.rainbow();
    }
    if (time > 110 && time < 110.05) {
      this.setColor(new LEDColor(0, 0, 0));
    }
  }

  public void zeroWaveIndex() {
    this.waveIndex = 0;
  }
}
