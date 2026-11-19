import com.mycompany.assignmentpoe.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        // This populates all 5 arrays.
        Message.populateTestData();
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        // From brief: Message 1 and 4 are Sent
        System.out.println("Sent Messages: " + Message.sentMessages);
        assertEquals(2, Message.sentMessages.size(), "Sent array should have 2 messages");
        assertTrue(Message.sentMessages.contains("Did you get the cake?"));
        assertTrue(Message.sentMessages.contains("It is dinner time !"));
        System.out.println("PASSED: Sent Messages array correctly populated");
    }

    @Test
    public void testDisplayLongestMessage() {
        String longest = Message.displayLongestMessage();
        System.out.println("Longest: " + longest);
        // Expected from brief.
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
        System.out.println("PASSED: Longest message is correct");
    }

    @Test
    public void testSearchForMessageID() {
        // Search for ID 0838884567 -> should return "It is dinner time !"
        String result = Message.searchByMessageID("0838884567");
        System.out.println(result);
        assertTrue(result.contains("It is dinner time !"), "Should find Message 4");
        System.out.println("PASSED: Search by Message ID");
    }

    @Test
    public void testSearchAllMessagesForRecipient() {
        // Recipient +27838884567 has 2 messages: Message 2 and Message 5
        String result = Message.searchByRecipient("+27838884567");
        System.out.println(result);
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
        System.out.println("PASSED: Search by Recipient returns 2 messages");
    }

    @Test
    public void testDeleteMessageUsingHash() {
        // Delete Message 2 ("Where are you...") using its hash
        String hashToDelete = Message.messageHashes.get(1); // Message 2 hash
        System.out.println("Deleting hash: " + hashToDelete);
        String result = Message.deleteByHash(hashToDelete);
        System.out.println(result);
        assertTrue(result.toLowerCase().contains("successfully deleted"));
        assertFalse(Message.messagesList.contains("Where are you? You are late! I have asked you to be on time."));
        System.out.println("PASSED: Delete by hash");
    }

    @Test
    public void testDisplayReport() {
        String report = Message.displayReport();
        System.out.println(report);
        // Report must contain Hash, Recipient, Message for all messages
        assertTrue(report.contains("Message Hash"));
        assertTrue(report.contains("Recipient"));
        assertTrue(report.contains("Message"));
        assertTrue(report.contains("Did you get the cake?"));
        System.out.println("PASSED: Display Report");
    }
}
