package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {

    @Test
    void createsAccountWithValidInitialBalance() {
        BankAccount account = new BankAccount(100);
        assertEquals(100, account.getBalance());
    }

    @Test
    void throwsExceptionWhenInitialBalanceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(-10));
    }

    @Test
    void depositsValidAmountSuccessfully() {
        BankAccount account = new BankAccount(100);
        account.deposit(50);
        assertEquals(150, account.getBalance());
    }

    @Test
    void rejectsOverflowingDepositWithoutChangingBalance() {
        BankAccount account = new BankAccount(Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(1));
        assertEquals(Integer.MAX_VALUE, account.getBalance());
    }

    @Test
    void allowsDepositUpToMaximumBalance() {
        BankAccount account = new BankAccount(Integer.MAX_VALUE - 10);
        account.deposit(10);
        assertEquals(Integer.MAX_VALUE, account.getBalance());
    }

    @Test
    void throwsExceptionWhenDepositAmountIsNotPositive() {
        BankAccount account = new BankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-20));
    }

    @Test
    void withdrawsValidAmountSuccessfully() {
        BankAccount account = new BankAccount(100);
        account.withdraw(40);
        assertEquals(60, account.getBalance());
    }

    @Test
    void throwsExceptionWhenWithdrawAmountIsNotPositive() {
        BankAccount account = new BankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-5));
    }

    @Test
    void throwsExceptionWhenWithdrawExceedsBalance() {
        BankAccount account = new BankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(101));
    }
}
