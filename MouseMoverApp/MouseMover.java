import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.Random;

/**
 * MouseMover
 *
 * Moves the mouse cursor by a tiny random amount every 3-7 seconds
 * (random interval each time) to prevent the system from going idle.
 *
 * Compile:  javac MouseMover.java
 * Run:      java MouseMover
 * Stop:     Ctrl+C in the terminal, or close the terminal window.
 */
public class MouseMover {

    // Minimum and maximum wait time between moves, in seconds.
    private static final int MIN_WAIT_SECONDS = 10;
    private static final int MAX_WAIT_SECONDS = 59;

    // How far (in pixels) the cursor nudges each time.
    private static final int MOVE_RANGE_PIXELS = 5;

    public static void main(String[] args) {
        Random random = new Random();

        try {
            Robot robot = new Robot();
            System.out.println("MouseMover started. Press Ctrl+C to stop.");

            while (true) {
                // Pick a random wait time between MIN and MAX seconds.
                int waitSeconds = MIN_WAIT_SECONDS
                        + random.nextInt(MAX_WAIT_SECONDS - MIN_WAIT_SECONDS + 1);

                Thread.sleep(waitSeconds * 1000L);

                // Get current mouse position.
                Point current = MouseInfo.getPointerInfo().getLocation();

                // Compute a small random offset (-MOVE_RANGE_PIXELS to +MOVE_RANGE_PIXELS).
                int dx = random.nextInt(MOVE_RANGE_PIXELS * 2 + 1) - MOVE_RANGE_PIXELS;
                int dy = random.nextInt(MOVE_RANGE_PIXELS * 2 + 1) - MOVE_RANGE_PIXELS;

                int newX = current.x + dx;
                int newY = current.y + dy;

                robot.mouseMove(newX, newY);

                // Move it back to the original spot right after, so the
                // cursor doesn't visibly drift across the screen over time.
                robot.mouseMove(current.x, current.y);

                System.out.println("Moved mouse at " + java.time.LocalTime.now()
                        + " (waited " + waitSeconds + "s)");
            }

        } catch (AWTException e) {
            System.err.println("Could not initialize Robot: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("MouseMover interrupted, exiting.");
            Thread.currentThread().interrupt();
        }
    }
}