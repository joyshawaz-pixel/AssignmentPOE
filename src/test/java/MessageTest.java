
import com.mycompany.assignmentpoe.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", m.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = "a".repeat(260);
        Message m = new Message(0, "+27718693002", longMsg);
        assertTrue(m.checkMessageLength().contains("Message exceeds 250 characters"));
    }

    @Test
    public void testRecipientSuccess() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message m = new Message(0, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", m.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        // Test case 1 from rubric - must produce 00:0:HITONIGHT
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678");
        // Should be 00:0:HITONIGHT
        assertEquals("00:0:HITONIGHT", m.createMessageHash());
    }

    @Test
    public void testMessageIDCreated() {
        Message m = new Message(0, "+27718693002", "Test");
        assertNotNull(m.getMessageID());
        assertTrue(m.checkMessageID());
    }

    @Test
    public void testSentMessage() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully sent.", m.SentMessage(1));
        assertEquals("Press 0 to delete the message.", m.SentMessage(2));
        assertEquals("Message successfully stored.", m.SentMessage(3));
    }
}