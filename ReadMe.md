
# Bank Management System

Welcome to the Bank Management System, a comprehensive and feature-rich platform built using Spring Boot, Hibernate, JPA, and MySQL, designed to provide a seamless banking experience.

## Project Overview

Our Bank Management System is packed with a wide array of cutting-edge features and functionalities to cater to all your banking needs. Here's a breakdown of what this system offers:

### User Registration and Verification

**Signup**: Users can easily create an account, triggering an event that sends a verification OTP to their email.

**OTP** **Verification**: To ensure security, users must enter the OTP received during registration to activate their accounts. Failed OTP attempts result in restricted access to the system.

### User Authentication and Session Management

**User** **Login**: Once verified, users can log in to the system, obtaining a unique user session for secure authentication and access to the platform.

### Bank and Branch Management

**Bank** **and** **Branch** **Creation**: Admins can dynamically add banks and branches to the system, providing flexibility for scaling the banking network.

**Multi**-**Branch** **Account**: Users have the liberty to open bank accounts in different branches, avoiding the duplication of the same account type within a single branch. However, they can open multiple account types, such as savings, deposit, and credit.

### Account Services

**Transaction** **Capability**: With a bank account, users can perform transactions, both sending and receiving money.

**Digital** **Wallet**: Users can open digital wallets linked to their bank accounts, enabling convenient money management and transactions.

**ATM** **Services**: Apply for a physical ATM card with a unique ATM number, CVV, expiration date, and a PIN for secure cash withdrawals, just like a real ATM.

**Beneficiary** **Management**: Users can add beneficiaries, allowing them to withdraw funds in the account owner's absence. Beneficiaries must provide proper approval from the account owner for withdrawal.

## Getting Started

### Prerequisites

Before diving into the Bank Management System, ensure you have the following:

* Java Development Kit (JDK)
Maven
MySQL
An Integrated Development Environment (IDE) like Eclipse or IntelliJ IDEA

### Setup

1.Clone this repository to your local machine
bash
Copy code
git clone https://github.com/yavar992/BankingSystem.git
2.Configure your MySQL database settings in application.properties.
3.Run the application using your IDE or Maven:

bash
Copy code
mvn spring-boot:run
4.Access the application in your web browser at http://localhost:8484.

## Contribute

We embrace contributions from the open-source community. To contribute:

1.Fork the repository.
2.Create a new branch for your feature or bug fix.
3.Make your changes and commit them with clear and concise messages.
4.Push your changes to your fork and open a pull request to this repository.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Contact

For questions or further assistance, please reach out to [yavarkhan892300@gmail.com]

With our feature-packed Bank Management System, the possibilities are limitless. Enhance your banking experience, streamline your transactions, and enjoy the utmost convenience. We encourage you to explore, fork, and contribute to this project. Welcome to the future of banking!

Documentation : http://localhost:8484/swagger-ui/index.html