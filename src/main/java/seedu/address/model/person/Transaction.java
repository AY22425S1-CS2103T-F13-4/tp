package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person's transaction in the address book.
 */
public class Transaction {

    private final String description;
    private final int amount;
    private final String otherParty;
    private final String date;

    /**
     * Every field must be present and not null.
     */
    public Transaction(String description, int amount, String otherParty, String date) {
        requireAllNonNull(description, amount, otherParty, date);
        this.description = description;
        this.amount = amount;
        this.otherParty = otherParty;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
    public int getAmount() {
        return amount;
    }

    public String getOtherParty() {
        return otherParty;
    }
    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return description.equals(otherTransaction.getDescription())
                && (amount == otherTransaction.getAmount())
                && otherParty.equals(otherTransaction.getOtherParty())
                && date.equals(otherTransaction.getDate());
    }

    @Override
    public String toString() {
        return String.format("%s | Amount: %d | Other Party: %s | Date: %s", description, amount, otherParty, date);
    }
}
