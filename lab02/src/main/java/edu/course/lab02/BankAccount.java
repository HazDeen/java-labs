package edu.course.lab02;

/**
 * Учебный класс банковского счета.
 */
public class BankAccount {

    private int balance;

    /**
     * Создает счет с начальным балансом.
     * Баланс не может быть отрицательным.
     */
    public BankAccount(int initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        this.balance = initialBalance;
    }

    /**
     * Пополняет счет на указанную сумму.
     * Сумма пополнения должна быть строго положительной.
     * Итоговый баланс должен помещаться в int.
     */
    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма пополнения должна быть больше нуля");
        }
        if (amount > Integer.MAX_VALUE - this.balance) {
            throw new IllegalArgumentException("Пополнение превышает максимальный допустимый баланс");
        }
        this.balance += amount;
    }

    /**
     * Снимает со счета указанную сумму.
     * Сумма списания должна быть больше нуля и не превышать текущий баланс.
     */
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма снятия должна быть больше нуля");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }
        this.balance -= amount;
    }

    /**
     * Возвращает текущий остаток на счете.
     */
    public int getBalance() {
        return this.balance;
    }
}
