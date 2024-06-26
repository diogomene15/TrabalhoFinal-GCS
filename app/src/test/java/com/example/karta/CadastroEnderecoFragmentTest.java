import org.junit.Test;
import static org.junit.Assert.*;

public class CadastroEnderecoFragmentTest {

    @Test
    public void testNegativeLatitudeAndLongitude() {
        CadastroEnderecoFragment fragment = new CadastroEnderecoFragment();

        // Set negative latitude and longitude values
        double latitude = -10.12345;
        double longitude = -20.54321;

        // Call the method that handles the input
        boolean result = fragment.handleInput(latitude, longitude);

        // Assert that the result is true, indicating that the input was accepted
        assertTrue(result);
    }
}