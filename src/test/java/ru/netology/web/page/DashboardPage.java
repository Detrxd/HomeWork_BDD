package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Selenide.$;


public class DashboardPage {

    private SelenideElement header = $("[data-test-id=dashboard]");
    private SelenideElement firstCardTransfer = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").$("[data-test-id='action-deposit']");
    private SelenideElement secondCardTransfer = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").$("[data-test-id='action-deposit']");
    private SelenideElement reloadButton = $("[data-test-id='action-reload']");
    private SelenideElement firstCardBalance = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement secondCardBalance = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");


    public DashboardPage() {
        header.shouldBe(Condition.visible);
    }

    public DashboardPage reloadMoneyStatus() {
        reloadButton.click();
        return new DashboardPage();
    }

    public int getFirstCardBalance() {
        String[] innerText = firstCardBalance.innerText().split(" ");
        int balance = Integer.parseInt(innerText[5]);
        return balance;
    }

    public int getSecondCardBalance() {
        String[] innerText = secondCardBalance.innerText().split(" ");
        int secondBalance = Integer.parseInt(innerText[5]);
        return secondBalance;
    }

    public TransferMoneyPage choseFirstCard() {
        firstCardTransfer.click();
        return new TransferMoneyPage();
    }

    public TransferMoneyPage choseSecondCard() {
        secondCardTransfer.click();
        return new TransferMoneyPage();
    }

}
