package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IsSelectedPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Transaction;
import seedu.address.model.person.TransactionContainsKeywordsPredicate;

/**
 * Finds and lists all transactions for any person whose description contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindTransactionCommand extends Command {
    public static final String COMMAND_WORD = "findt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all transactions whose descriptions contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " food transport";

    private final Index personIndex;
    private final TransactionContainsKeywordsPredicate predicate;

    /**
     * Creates a FindTransactionCommand to find the transactions of a given {@code Index} of a person
     * that meet the specified {@code TransactionContainsKeywordsPredicate}
     */
    public FindTransactionCommand(Index presonIndex, TransactionContainsKeywordsPredicate predicate) {
        this.personIndex = presonIndex;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person targetPerson = lastShownList.get(personIndex.getZeroBased());
        List<Transaction> targetTransactions = targetPerson.getTransactions();
        model.updateFilteredPersonList(new IsSelectedPredicate(model, personIndex));
        model.setViewTransactions(true);
        model.updateTransactionList(targetTransactions);
        model.updateTransactionListPredicate(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size(), Messages.format(targetPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTransactionCommand)) {
            return false;
        }

        FindTransactionCommand otherFindTransactionCommand = (FindTransactionCommand) other;
        return predicate.equals(otherFindTransactionCommand.predicate);
    }
}
