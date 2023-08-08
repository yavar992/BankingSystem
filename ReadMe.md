things to add in the project yet 

Transaction: 
Represents a financial transaction made on the bank account, such as deposits, withdrawals, transfers, etc.
Each transaction would have details like the transaction amount, date, description, and transaction type (debit or credit).

Beneficiary: 
Represents a person or entity that is authorized to receive funds through transfers or other banking services from the bank account. 
This entity would store details like the beneficiary's name, account number, and other relevant information.

Card: Represents a debit or credit card associated with the bank account. It would store card details like the card number,
expiration date, CVV, card type, and the status of the card (active, blocked, etc.).

Loan: Represents a loan taken by the account holder from the bank. 
The entity would store loan details like the loan amount, interest rate, loan term, and repayment schedule.

Statement: Represents a periodic statement issued by the bank to the account holder,
summarizing all transactions and the account's current balance during a specific period.

Alert: Represents customized alerts or notifications set up by the account holder for specific events like low balance,
large transactions, or account activity.

Overdraft Facility: Represents an overdraft facility associated with the bank account
, allowing the account holder to withdraw more money than the available balance up to a predefined limit.

Fixed Deposit: Represents a fixed deposit account associated with the bank account,
where the account holder can deposit a sum of money for a specific period at a fixed interest rate.

Auto-Payment: Represents automatic payment arrangements set up by the account holder for regular bill payments or subscriptions.

Standing Instruction: Represents standing instructions set by the account holder for recurring fund transfers between their accounts or to other beneficiaries.




//TO DO 
add more braches to the bank
delete bankBranches from bank
update the branches 
get all the branches for the bank
get all the details for ths customer including the bank and branches 
add money to the bank 
debit money from the bank
update customer bank branches or customer information
check if a user has an account already or not if yes throw him a error that you already have account in our bank
check if user want to open the diff account type in our bank than allow him to open the account 
check if the customer is verfied or not if he's not then throw him a error that you are not verify 