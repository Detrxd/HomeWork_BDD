package ru.netology.web.test;

import lombok.val;
//import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.TransferMoneyPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    DashboardPage dashboardPage;
    int amount;
    int balanceFirstCard;
    int balanceSecondCard;

    @BeforeEach
    void InitPageAuthenticator() {
        open("http://localhost:9999");
        val loginPage = new LoginPageV1();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        balanceFirstCard = dashboardPage.getFirstCardBalance();
        balanceSecondCard = dashboardPage.getSecondCardBalance();
    }

    @Test
    void ShouldTransferMoneyFromSecondToFirstCard() {
        amount = 1000;
        var transferPage = dashboardPage.choseFirstCard();
        var cardNumber = DataHelper.cardsNumber.getSecondCardsNumber().getCardsNumber();
        var returnSuccessPage = transferPage.transferSuccess(Integer.toString(amount), cardNumber);
        int totalBalanceFirstCard = dashboardPage.getFirstCardBalance();
        int totalBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCard + amount, totalBalanceFirstCard);
        assertEquals(balanceSecondCard - amount, totalBalanceSecondCard);
    }

    @Test
    void ShouldTransferFromSecondToFirstCard() {
        amount = 0;
        var transferPage = dashboardPage.choseFirstCard();
        var cardNumber = DataHelper.cardsNumber.getSecondCardsNumber().getCardsNumber();
        var returnSuccessPage = transferPage.transferSuccess(Integer.toString(amount), cardNumber);
        int totalBalanceFirstCard = dashboardPage.getFirstCardBalance();
        int totalBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCard, totalBalanceFirstCard);
        assertEquals(balanceSecondCard, totalBalanceSecondCard);
    }


    @Test
    void ShouldTransferZeroValue() {
        amount = 0;
        var transferPage = dashboardPage.choseFirstCard();
        var cardNumber = DataHelper.cardsNumber.getSecondCardsNumber().getCardsNumber();
        var returnSuccessPage = transferPage.transferSuccess(Integer.toString(amount), cardNumber);
        int totalBalanceFirstCard = dashboardPage.getFirstCardBalance();
        int totalBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCard + amount, totalBalanceFirstCard);
        assertEquals(balanceSecondCard - amount, totalBalanceSecondCard);
    }

    @Test
    void receiveAllMoneyFromFirstCardToSecondCard() {
        amount = balanceFirstCard;
        var transferPage = dashboardPage.choseSecondCard();
        var cardNumber = DataHelper.cardsNumber.getFirstCardsNumber().getCardsNumber();
        var returnSuccessPage = transferPage.transferSuccess(Integer.toString(amount), cardNumber);
        int totalBalanceFirstCard = dashboardPage.getFirstCardBalance();
        int totalBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(balanceSecondCard + amount, totalBalanceSecondCard);
        assertEquals(balanceFirstCard - amount, totalBalanceFirstCard);
    }

    @Test
    void receiveAllMoneyFromSecondCardToFirstCard() {
        amount = balanceSecondCard;
        var transferPage = dashboardPage.choseFirstCard();
        var cardNumber = DataHelper.cardsNumber.getSecondCardsNumber().getCardsNumber();
        var returnSuccessPage = transferPage.transferSuccess(Integer.toString(amount), cardNumber);
        int totalBalanceFirstCard = dashboardPage.getFirstCardBalance();
        int totalBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCard + amount, totalBalanceFirstCard);
        assertEquals(balanceSecondCard - amount, totalBalanceSecondCard);
    }


    @Test
    void ShouldTransferZeroFromSecondToFirstCard() {
        amount = 0;
        var transferPage = dashboardPage.choseSecondCard();
        var cardNumber = DataHelper.cardsNumber.getFirstCardsNumber().getCardsNumber();
        var returnSuccessPage = transferPage.transferSuccess(Integer.toString(amount), cardNumber);
        int totalBalanceFirstCard = dashboardPage.getFirstCardBalance();
        int totalBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCard + amount, totalBalanceFirstCard);
        assertEquals(balanceSecondCard - amount, totalBalanceSecondCard);
    }

    @Test
    void ShouldDeclineTransfer() {
        var transferPage = dashboardPage.choseFirstCard();
        TransferMoneyPage transferMoneyPage = new TransferMoneyPage();
        var cancelTransfer = transferMoneyPage.cancelTransferMoney();
        int totalBalanceFirstCard = dashboardPage.getFirstCardBalance();
        int totalBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCard, totalBalanceFirstCard);
        assertEquals(balanceSecondCard, totalBalanceSecondCard);
    }

    @Test
    void receiveMoneyFromSecondToFirstCardByZero() {
        amount = balanceSecondCard;
        var transferPage = dashboardPage.choseFirstCard();
        var cardNumber = DataHelper.cardsNumber.getSecondCardsNumber().getCardsNumber();
        var returnSuccessPage = transferPage.transferSuccess(Integer.toString(amount), cardNumber);
        int totalBalanceFirstCard = dashboardPage.getFirstCardBalance();
        int totalBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(0, totalBalanceSecondCard);
        assertEquals(balanceFirstCard + amount, totalBalanceFirstCard);
    }

    @Test
    void errorTransferOperation() {
        amount = balanceSecondCard + 1000;
        var transferPage = dashboardPage.choseFirstCard();
        var cardNumber = DataHelper.cardsNumber.getSecondCardsNumber().getCardsNumber();
        transferPage.transferFault(Integer.toString(amount), cardNumber); // Error`a НЕТ?? => Issue
    }

}
