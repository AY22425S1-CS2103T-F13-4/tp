package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OTHER_PARTY;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Transaction;
import seedu.address.model.person.TransactionDateComparator;

/**
 * Adds a transaction to the selected person in address book.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "addt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to selected person. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_OTHER_PARTY + "OTHER PARTY "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "buy new equipment "
            + PREFIX_AMOUNT + "-1000.55 "
            + PREFIX_OTHER_PARTY + "Company XYZ "
            + PREFIX_DATE + "2024-10-20";

    public static final String MESSAGE_ADD_TRANSACTION_SUCCESS = "Added new transaction %1$s\nto %2$s";

    private final Index index;
    private final Transaction toAdd;
    private final Logger logger = LogsCenter.getLogger(AddTransactionCommand.class);

    /**
     * @param index index of selected person in person list to add transaction to
     * @param transaction transaction to add
     */
    public AddTransactionCommand(Index index, Transaction transaction) {
        requireAllNonNull(index, transaction);

        this.index = index;
        toAdd = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (model.getIsViewTransactions()) {
            logger.fine("CommandException caused by attempt to use addt command in transaction view.");
            throw new CommandException(String.format(Messages.MESSAGE_MUST_BE_PERSON_LIST, COMMAND_WORD));
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_PERSON_LIST, COMMAND_WORD));
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.fine("CommandException caused by invalid index.");
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        assert personToEdit != null : "Person should not be null";
        List<Transaction> transactions = new ArrayList<>(personToEdit.getTransactions());
        transactions.add(toAdd);
        transactions.sort(new TransactionDateComparator());

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getCompany(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), transactions);

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_ADD_TRANSACTION_SUCCESS, Messages.format(toAdd),
                Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTransactionCommand)) {
            return false;
        }

        AddTransactionCommand otherCommand = (AddTransactionCommand) other;
        return index.equals(otherCommand.index) && toAdd.equals(otherCommand.toAdd);
    }

}
