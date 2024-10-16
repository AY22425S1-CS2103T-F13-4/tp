package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListTransactionCommandTest {
    private Model model;
    private Model expectedModel;

    @Test
    public void execute_listAllTransactions_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(new ListTransactionCommand(INDEX_FIRST_PERSON), model,
                String.format(ListTransactionCommand.MESSAGE_SUCCESS,
                        Messages.format(expectedModel.getFilteredPersonList().get(0))),
                expectedModel);
    }

    @Test
    public void equals() {
        ListTransactionCommand command1 = new ListTransactionCommand(INDEX_FIRST_PERSON);
        ListTransactionCommand command2 = new ListTransactionCommand(INDEX_FIRST_PERSON);
        ListTransactionCommand command3 = new ListTransactionCommand(INDEX_SECOND_PERSON);

        // same object -> return true
        assertTrue(command1.equals(command1));

        // different object but same index -> return true
        assertTrue(command1.equals(command2));

        // different index -> return true
        assertFalse(command1.equals(command3));

        // compare object with null -> return false
        assertFalse(command1.equals(null));

        // compare with other types of command -> return false
        assertFalse(command1.equals(new ListCommand()));
    }
}
